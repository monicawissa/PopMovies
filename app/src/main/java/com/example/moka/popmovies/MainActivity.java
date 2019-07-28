package com.example.moka.popmovies;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moka.popmovies.api.client;
import com.example.moka.popmovies.api.service;
import com.example.moka.popmovies.jsonmovie.MovieResults;
import com.example.moka.popmovies.jsonmovie.movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    // choosing by default menu1
    // and change it when the user  deside to sort by Top rate
    public boolean which_menu=true;

    // needed data in api query
    // you have to put Your api-key in  " TheMovieDBapiToke "
    public final String TheMovieDBapiToke="############################";
    public final String language="en-US";
    //choosing the sort order "popular or TopRated"
    public boolean movie_popular=true;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerView_dAdapter;
    public List<movie> listItems = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.m_RecyclerView);
        recyclerView.setHasFixedSize(true);

        //handling if it's in portrait position or in the rotation position
        if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
                gridLayoutManager = new GridLayoutManager(this, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
        }else {
            gridLayoutManager = new GridLayoutManager(this, 4);
            recyclerView.setLayoutManager(gridLayoutManager);
        }

        /*recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == listItems.size() - 1) {
                    Get_All_movies(listItems.get(listItems.size()-1).getId());
                }

            }
        });*/

        Get_All_movies(0);
    }
    public void Get_All_movies(int limit) {

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                if(TheMovieDBapiToke==""){
                    Toast.makeText(this, "Please get the api key first from themoviedb.org", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Call<MovieResults> call=null;
                    service myapi= new client().getRetrofit().create(service.class);

                    if(movie_popular)
                        call=myapi.getpopularMovies(TheMovieDBapiToke,language,"1");
                    else
                        call=myapi.getTopRatedMovies(TheMovieDBapiToke,language,"1");

                    call.enqueue(new Callback<MovieResults>() {
                        @Override
                        public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {

                            listItems=response.body().getResults();
                            recyclerView.setAdapter(new RecyclerViewAdapter(listItems, getApplicationContext()));
                        }

                        @Override
                        public void onFailure(Call<MovieResults> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Can't Fetch the data ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }catch (Exception e){
                Toast.makeText(this,"Exception Error", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(which_menu){
            getMenuInflater().inflate(R.menu.menu1,menu);}
        else {
            getMenuInflater().inflate(R.menu.menu2,menu);
        }
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.popular_menu&which_menu==false){
            //change to menu1
            which_menu=true;
            invalidateOptionsMenu();
            movie_popular=true;
            Get_All_movies(0);
        }
        if(id==R.id.topRated_menu&&which_menu){
            //change to menu2
            which_menu=false;
            invalidateOptionsMenu();
            movie_popular=false;
            Get_All_movies(0);
        }
        return super.onOptionsItemSelected(item);
    }
}
