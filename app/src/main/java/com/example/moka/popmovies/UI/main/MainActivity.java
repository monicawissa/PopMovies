package com.example.moka.popmovies.UI.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moka.popmovies.BuildConfig;
import com.example.moka.popmovies.utilities.CheckInternetConnection;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.Room.Favorite;
import com.example.moka.popmovies.Room.FavoriteViewModel;
import com.example.moka.popmovies.jsonmovie.movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // choosing by default menu1
    // menu1 = popular...menu2 = top rate...menu3 = favorite

    //choosing the sort order "popular or TopRated or favorite"
    // choosing by default popular
    private int view_sort=1;


    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerView_dAdapter;
    private GridLayoutManager gridLayoutManager;

    //Viewmodel
    private FavoriteViewModel nodeViewModel;
    private clientViewModel clientVM;
    //save state
    private static String LIST_STATE = "list_state";
    private Parcelable savedRecyclerLayoutState;
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private ArrayList<movie> moviesInstance = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clientVM=ViewModelProviders.of(this).get(clientViewModel.class);
        recyclerView =  findViewById(R.id.m_RecyclerView);
        recyclerView.setHasFixedSize(true);

        //handling if it's in portrait position or in the rotation position
        if(this.getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT){
                gridLayoutManager = new GridLayoutManager(this, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
        }else {
            gridLayoutManager = new GridLayoutManager(this, 4);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        if (savedInstanceState != null){
            moviesInstance = savedInstanceState.getParcelableArrayList(LIST_STATE);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            displayDataFromInstanceState();
        }else {
            Get_All_movies();
        }

    }


    private void displayDataFromInstanceState(){
        recyclerView_dAdapter = new RecyclerViewAdapter(moviesInstance, this);

        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerView_dAdapter);
        // restore Layout Manager Position
        if(savedRecyclerLayoutState!=null)
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        recyclerView_dAdapter.notifyDataSetChanged();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(LIST_STATE, moviesInstance);
        savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        moviesInstance = savedInstanceState.getParcelableArrayList(LIST_STATE);
        savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void Get_All_movies() {

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                if(BuildConfig.The_MovieDBapiToke.isEmpty()){
                    Toast.makeText(this, getString(R.string.api_NotFound), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("TAG",""+view_sort);

                if(view_sort==1) {
                    clientVM.getpopularMovies();
                    clientVM.listItems.observe(this, new Observer<List<movie>>() {
                        @Override
                        public void onChanged(@Nullable List<movie> movies) {
                            moviesInstance.addAll(movies);
                            recyclerView.setAdapter(new RecyclerViewAdapter(movies, getApplicationContext()));
                            recyclerView.smoothScrollToPosition(0);
                        }
                    });

                }
                else if(view_sort==2){
                    clientVM.getTopRatedMovies();
                    clientVM.listItems.observe(this, new Observer<List<movie>>() {
                        @Override
                        public void onChanged(@Nullable List<movie> movies) {
                            moviesInstance.addAll(movies);
                            recyclerView.setAdapter(new RecyclerViewAdapter(movies, getApplicationContext()));
                            recyclerView.smoothScrollToPosition(0);
                        }
                    });
                }
                else {
                    view_favorites();
                }


            }catch (Exception e){
                Toast.makeText(this,"Exception Error in GET ALL Movies", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void view_favorites(){
        //Viewmodel
        nodeViewModel=ViewModelProviders.of(this).get(FavoriteViewModel.class);
        nodeViewModel.getAllFavorites().observe(this,new Observer<List<Favorite>>(){

            @Override
            public void onChanged(@Nullable List<Favorite> notes) {
                //update RecycleViewAdapter

                List<movie> movies = new ArrayList<>();
                if(notes==null){
                    Toast.makeText(MainActivity.this,getString(R.string.null_data), Toast.LENGTH_SHORT).show();
                    return;
                }
                //assert notes!=null;
                for (Favorite i :notes ){
                    movie movie = new movie();
                    movie.setId(i.getFavoriteid());
                    movie.setOverview(i.getOverview());
                    movie.setTitle(i.getTitle());
                    movie.setOriginalTitle(i.getTitle());
                    movie.setReleaseDate(i.getReleaseDate());
                    movie.setBackdropPath(i.getBackdropPath());
                    movie.setPosterPath(i.getPosterpath());
                    movie.setVoteAverage(i.getUserrating());
                    movie.setFavorite(true);
                    movies.add(movie);
                }
                Log.d("TAGGG", "favorite movie size "+movies.size());

                recyclerView_dAdapter = new RecyclerViewAdapter(movies , MainActivity.this);
                recyclerView.setAdapter(recyclerView_dAdapter);
                recyclerView_dAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(view_sort==1){
            getMenuInflater().inflate(R.menu.menu1,menu);}
        else if(view_sort==2) {
            getMenuInflater().inflate(R.menu.menu2,menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu3,menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.delete_all_favorite){
            FavoriteViewModel nodeViewModel=ViewModelProviders.of(this).get(FavoriteViewModel.class);
            nodeViewModel.deleteall();
            Toast.makeText(MainActivity.this,"All Favorite movies are deleted", Toast.LENGTH_SHORT).show();

        }
        if(id==R.id.popular_menu && view_sort!=1){
            //change to menu1
            view_sort=1;
            invalidateOptionsMenu();
            Get_All_movies();
        }
        if(id==R.id.topRated_menu && view_sort!=2){
            //change to menu2
            view_sort=2;
            invalidateOptionsMenu();
            Get_All_movies();
        }
        if(id==R.id.favorite_menu && view_sort!=3){
            //change to menu2
            view_sort=3;
            invalidateOptionsMenu();
            Get_All_movies();
        }
        return super.onOptionsItemSelected(item);
    }
}
