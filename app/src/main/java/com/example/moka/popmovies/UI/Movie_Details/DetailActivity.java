package com.example.moka.popmovies.UI.Movie_Details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moka.popmovies.Models.Trailer;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.Room.Favorite;
import com.example.moka.popmovies.Room.FavoriteViewModel;
import com.example.moka.popmovies.Models.movie;
import com.example.moka.popmovies.utilities.ActivityUtils;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

public class DetailActivity extends AppCompatActivity {


    TextView nameofmovie, plotsynopsis, uesrRating, releaseDate;
    ImageView posrterimg, backimg;

    movie movi;
    private int movie_id;
    MaterialFavoriteButton materialFavoriteButton;

    //Viewmodel
    private FavoriteViewModel noteViewModel;

    private Boolean isFavorite = false;

    TrailerFragment trailerFragment;
    ReviewFragment reviewFragment;
    CastFragment castFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        nameofmovie = (TextView) findViewById(R.id.Movie_title);
        plotsynopsis = (TextView) findViewById(R.id.overview);
        uesrRating = (TextView) findViewById(R.id.Movie_rate);
        releaseDate = (TextView) findViewById(R.id.Movie_release);
        posrterimg = (ImageView) findViewById(R.id.poster);
        backimg = (ImageView) findViewById(R.id.backdrop);
        materialFavoriteButton = (MaterialFavoriteButton) findViewById(R.id.favorite);

        SetupUI(intent);
        Log.d("TAGGG", "create movie ");

        materialFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) {

                    saveFavorite();
                    Log.d("TAGGG", "add favorite");
                    Toast.makeText(DetailActivity.this, "Added to Favorite :)", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(buttonView, "Added to Favorite :)", Snackbar.LENGTH_SHORT).show();
                } else {
                    deleteFavorite(movie_id);
                    Toast.makeText(DetailActivity.this, "Removed from Favorite :(", Toast.LENGTH_SHORT).show();
                   //Snackbar.make(buttonView, "Removed from Favorite :(", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        reviewFragment=(ReviewFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentReview);
        castFragment=(CastFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentcast);
        trailerFragment=(TrailerFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTrailer);

        if(castFragment==null||!castFragment.isAdded()){
            castFragment= CastFragment.newInstance();
            //getFragmentManager().beginTransaction().add(R.id.frame_movies,fragment).commit();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),castFragment,R.id.frame_cast);
            castFragment.setattribute(movie_id);
        }
        if(reviewFragment==null||!reviewFragment.isAdded()){
            reviewFragment= ReviewFragment.newInstance();
            reviewFragment.setattribute(movie_id);
            //getFragmentManager().beginTransaction().add(R.id.frame_movies,fragment).commit();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),reviewFragment,R.id.frame_Review);
        }
        if(trailerFragment==null||!trailerFragment.isAdded()){
            trailerFragment= TrailerFragment.newInstance();
            trailerFragment.setattribute(movie_id);
            //getFragmentManager().beginTransaction().add(R.id.frame_movies,fragment).commit();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),trailerFragment,R.id.frame_trailer);
        }

    }

    public void SetupUI(Intent intent) {
        movi = getIntent().getParcelableExtra("movies");
        setTitle(movi.getOriginalTitle());
        if (intent.hasExtra("movies")) {
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
            movie_id = movi.getId();
            is_itFavoriteMovie(movie_id);

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }


    }

    private void saveFavorite() {
        //Viewmodel
        noteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        LiveData<Favorite> fs = null;
        fs = noteViewModel.getFavoriteById(movi.getId());
        movi.setFavorite(true);
        // here the problem .. it doesn't update in the noteviewmodel

        if (fs.getValue() == null) {
            Log.d("TAGGG", "new movie to insert in the favorite");

            Favorite favoriteEntry = new Favorite(movi.getId(), movi.getTitle(), movi.getVoteAverage(), movi.posterPath, movi.getOverview(), movi.getReleaseDate(), movi.backdropPath);

            noteViewModel.insert(favoriteEntry);

        } else {
            Log.d("TAGGG", " movie to update in the favorite");
            Favorite favoriteEntry = new Favorite(movi.getId(), movi.getTitle(), movi.getVoteAverage(), movi.posterPath, movi.getOverview(), movi.getReleaseDate(), movi.backdropPath);
            noteViewModel.update(favoriteEntry);
        }

    }

    private void deleteFavorite(int movie_id) {
        noteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        //Viewmodel
        noteViewModel.delete(movie_id);
        movi.setFavorite(false);
    }

    private void is_itFavoriteMovie(final int movie_id) {

        noteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        final Boolean fav = false;
        LiveData<Favorite> fvm = noteViewModel.getFavoriteById(movie_id);
        fvm.observe(this, new Observer<Favorite>() {
            @Override
            public void onChanged(@Nullable Favorite favorite) {
                if (favorite != null) {
                    materialFavoriteButton.setFavorite(true);
                    isFavorite = true;


                } else {
                    isFavorite = false;
                    materialFavoriteButton.setFavorite(false);

                }

            }
        });

    }

//    private void Trailerload_data() {
//        // Trailer adapter
//        TrailerList = new ArrayList<>();
//        recyclerView = (RecyclerView) findViewById(R.id.trailerRcl);
//        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),
//                LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(mLayoutManager);
//
//
//        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
//        Boolean Ch = cic.isConnectingToInternet();
//        if (!Ch) {
//            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
//        } else {
//            try {
//                if (BuildConfig.The_MovieDBapiToke.isEmpty()) {
//                    Toast.makeText(this, getString(R.string.api_NotFound), Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    new Detailsmovie("Trailer",movie_id).trailerItems.observe(this, new Observer<List<Trailer>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Trailer> trailers) {
//                            TrailerList=trailers;
//                            recyclerView.setAdapter(new TrailerAdapter(getApplicationContext(), trailers));
//                            recyclerView.smoothScrollToPosition(0);
//                        }
//                    });
//
//                }
//            } catch (Exception e) {
//                Toast.makeText(this, "Exception Error", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
//
//    private void Review_load_data() {
//        // Review adapter
//        ReviewList = new ArrayList<>();
//        Review_recyclerView = (RecyclerView) findViewById(R.id.reviewsRcl);
//        RecyclerView.LayoutManager Review_mLayoutManager = new LinearLayoutManager(getApplicationContext(),
//                LinearLayoutManager.HORIZONTAL, false);
//        Review_recyclerView.setLayoutManager(Review_mLayoutManager);
//
//
//        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
//        Boolean Ch = cic.isConnectingToInternet();
//        if (!Ch) {
//            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
//        } else {
//            try {
//                if (BuildConfig.The_MovieDBapiToke.isEmpty()) {
//                    Toast.makeText(this, getString(R.string.api_NotFound), Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    new Detailsmovie("Review",movie_id).reviewItems.observe(this, new Observer<List<Review>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Review> reviews) {
//                            ReviewList=reviews;
//                            Review_recyclerView.setAdapter(new ReviewAdapter(getApplicationContext(), reviews));
//                            Review_recyclerView.smoothScrollToPosition(0);
//                        }
//                    });
//
//                }
//            } catch (Exception e) {
//                Toast.makeText(this, "Exception Error", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }
//
//    private void Cast_load_data() {
//
//        // Cast_ adapter
//        CastList = new ArrayList<>();
//        Cast_recyclerView = (RecyclerView) findViewById(R.id.castRcl);
//        RecyclerView.LayoutManager Cast_mLayoutManager = new LinearLayoutManager(getApplicationContext(),
//                LinearLayoutManager.HORIZONTAL, false);
//        Cast_recyclerView.setLayoutManager(Cast_mLayoutManager);
//
//        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
//        Boolean Ch = cic.isConnectingToInternet();
//        if (!Ch) {
//            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
//        } else {
//            try {
//                if (BuildConfig.The_MovieDBapiToke.isEmpty()) {
//                    Toast.makeText(this, getString(R.string.api_NotFound), Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    new Detailsmovie("Actor",movie_id).castItems.observe(this, new Observer<List<Cast>>() {
//                        @Override
//                        public void onChanged(@Nullable List<Cast> casts) {
//                            CastList=casts;
//                            Cast_recyclerView.setAdapter(new ActorsAdapter(getApplicationContext(), casts));
//                            Cast_recyclerView.smoothScrollToPosition(0);
//                        }
//                    });
//
//                }
//            } catch (Exception e) {
//                Toast.makeText(this, "Exception Error", Toast.LENGTH_SHORT).show();
//            }
//
//        }
//    }

}


