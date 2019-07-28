package com.example.moka.popmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

        if(intent.hasExtra("original_title")){
            nameofmovie.setText(intent.getExtras().getString("original_title"));
            plotsynopsis.setText(intent.getExtras().getString("overview"));
            uesrRating.setText(Double.toString(intent.getExtras().getDouble("vote_average")));
            releaseDate.setText(intent.getExtras().getString("release_date"));
            String thumb=intent.getExtras().getString("poster_path");
            Glide.with(this)
                    .load(thumb)

                    .into(img);
        }
        else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
        setTitle(intent.getExtras().getString("original_title"));

    }
}
