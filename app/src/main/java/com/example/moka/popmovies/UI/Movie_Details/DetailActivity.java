package com.example.moka.popmovies.UI.Movie_Details;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.data.Models.Cast;
import com.example.moka.popmovies.data.Models.Movie;
import com.example.moka.popmovies.data.Models.Review;
import com.example.moka.popmovies.data.Models.Trailer;
import com.example.moka.popmovies.utilities.ActivityUtils;
import com.example.moka.popmovies.utilities.Injection;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class DetailActivity extends AppCompatActivity implements MovieDetails_contract.View{

    static boolean active = false;
    TextView nameofmovie, plotsynopsis, uesrRating, releaseDate;
    ImageView posrterimg, backimg;

    Movie movi;
    MaterialFavoriteButton materialFavoriteButton;

    private Boolean isFavorite = false;

    TrailerFragment trailerFragment;
    ReviewFragment reviewFragment;
    CastFragment castFragment;
    private MovieDetails_contract.Presenter movieDetailsPresenter;
    View parentLayout ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        parentLayout = findViewById(android.R.id.content);

        Intent intent = getIntent();

        nameofmovie = (TextView) findViewById(R.id.Movie_title);
        plotsynopsis = (TextView) findViewById(R.id.overview);
        uesrRating = (TextView) findViewById(R.id.Movie_rate);
        releaseDate = (TextView) findViewById(R.id.Movie_release);
        posrterimg = (ImageView) findViewById(R.id.poster);
        backimg = (ImageView) findViewById(R.id.backdrop);
        materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite);
        movieDetailsPresenter=new MovieDetailsPresenter(this,
                Injection.provideTasksRepository(getApplicationContext(),"https://api.themoviedb.org/3/")
        );
        reviewFragment=(ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentReview);
        castFragment=(CastFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentcast);
        trailerFragment=(TrailerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTrailer);

        SetupUI(intent);
        materialFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieDetailsPresenter.favoriteButtonClicked();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void SetupUI(Intent intent) {
        if (intent.hasExtra("movie")) {
            movi = getIntent().getParcelableExtra("movie");
            setTitle(movi.getTitle());
            nameofmovie.setText(movi.getOriginalTitle());
            plotsynopsis.setText(movi.getOverview());
            uesrRating.setText(Double.toString(movi.getVoteAverage()));
            releaseDate.setText(movi.getReleaseDate());
            String thumb = movi.getPosterPath(), thumb2 = movi.getBackdropPath();
            Glide.with(this)
                    .load(thumb)
                    .into(posrterimg);
            Glide.with(this)
                    .load(thumb2)
                    .into(backimg);
            movieDetailsPresenter.setMovie(movi);
            movieDetailsPresenter.start();

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        active=true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active=false;
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showReview(List<Review> reviews) {
        if(reviewFragment==null||!reviewFragment.isAdded()){
            reviewFragment= ReviewFragment.newInstance(reviews);
            //getFragmentManager().beginTransaction().add(R.id.frame_movies,fragment).commit();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),reviewFragment,R.id.frame_Review);
        }
    }

    @Override
    public void showTrailer(List<Trailer> trailers) {
        if(trailerFragment==null||!trailerFragment.isAdded()){
            trailerFragment= TrailerFragment.newInstance(trailers);
            //getFragmentManager().beginTransaction().add(R.id.frame_movies,fragment).commit();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),trailerFragment,R.id.frame_trailer);
        }
    }

    @Override
    public void showCast(List<Cast> casts) {
        if(castFragment==null||!castFragment.isAdded()){
            castFragment= CastFragment.newInstance(casts);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),castFragment,R.id.frame_cast);
        }
    }

    @Override
    public void showNotFavorite() {
        materialFavoriteButton.setFavorite(false);
    }

    @Override
    public void showSuccessfullyDeleteFavoritesMessage() {
        Snackbar.make(parentLayout, R.string.deleteFavMessage, Snackbar.LENGTH_LONG).show();

    }

    @Override
    public void showSuccessfullyAddFavoritesMessage() {
        Snackbar.make(parentLayout, getString(R.string.addFavMessage), Snackbar.LENGTH_LONG).show();

    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void showisFavorite() {
        materialFavoriteButton.setFavorite(true);
    }

    @Override
    public void setPresenter(MovieDetails_contract.Presenter presenter) {
        movieDetailsPresenter = checkNotNull(presenter);
    }
}


