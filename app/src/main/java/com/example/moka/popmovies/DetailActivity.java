package com.example.moka.popmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moka.popmovies.jsonmovie.movie;

public class DetailActivity extends AppCompatActivity {

    TextView nameofmovie,plotsynopsis,uesrRating,releaseDate;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        nameofmovie=(TextView)findViewById(R.id.Movie_title);
        plotsynopsis=(TextView)findViewById(R.id.overview);
        uesrRating=(TextView)findViewById(R.id.Movie_rate);
        releaseDate=(TextView)findViewById(R.id.Movie_release);
        img=(ImageView)findViewById(R.id.img_detail_movie);

        SetupUI(intent);

    }
    public void SetupUI(Intent intent){
        movie movi = getIntent().getParcelableExtra("movies");
        if(intent.hasExtra("movies")){
            nameofmovie.setText(movi.getOriginalTitle());
            plotsynopsis.setText(movi.getOverview());
            uesrRating.setText(Double.toString(movi.getVoteAverage()));
            releaseDate.setText(movi.getReleaseDate());
            String thumb=movi.getPosterPath();
            Glide.with(this)
                    .load(thumb)

                    .into(img);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        setTitle(movi.getOriginalTitle());
    }
}

