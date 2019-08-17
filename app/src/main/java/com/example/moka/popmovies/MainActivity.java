package com.example.moka.popmovies;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.nfc.Tag;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moka.popmovies.Room.Favorite;
import com.example.moka.popmovies.Room.FavoriteViewModel;
import com.example.moka.popmovies.adapter.RecyclerViewAdapter;
import com.example.moka.popmovies.api.client;
import com.example.moka.popmovies.api.service;
import com.example.moka.popmovies.jsonmovie.MovieResults;
import com.example.moka.popmovies.jsonmovie.movie;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    // choosing by default menu1 = popular...menu2 = top rate...menu3 = favorite

    // the needed data in api query

    public final String language="en-US";
    //choosing the sort order "popular or TopRated or favorite"
    // choosing by default popular
    public int view_sort=1;


    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerView_dAdapter;
    public List<movie> listItems = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    //Viewmodel
    private FavoriteViewModel noteViewModel;
    private LiveData<List<Favorite>> ff=null;

    //save state
    private static String LIST_STATE = "list_state";
    private Parcelable savedRecyclerLayoutState;
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private ArrayList<movie> moviesInstance = new ArrayList<>();

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
        if (savedInstanceState != null){
            moviesInstance = savedInstanceState.getParcelableArrayList(LIST_STATE);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            displayDataFromInstanceState();
        }else {
            Get_All_movies(0);
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
    private int checkSortOrder(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrder = preferences.getString(
                this.getString(R.string.pref_sort_order_key),
                this.getString(R.string.pref_most_popular)
        );
        assert sortOrder != null;
        if(sortOrder.equals("1"))return 1;
        else if(sortOrder.equals("2"))return 2;
        else if(sortOrder.equals("3"))return 3;
        else return 0;
    }
    public void Get_All_movies(int limit) {

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

                Call<MovieResults> call=null;
                service myapi= new client().getRetrofit().create(service.class);


                Log.d("TAG",""+view_sort);
                //view_sort=checkSortOrder();
                if(view_sort==1) {
                    call = myapi.getpopularMovies(BuildConfig.The_MovieDBapiToke, language, "1");
                    api_request(call);
                }
                else if(view_sort==2){
                    call=myapi.getTopRatedMovies(BuildConfig.The_MovieDBapiToke,language,"1");
                    api_request(call);
                }
                else {
                    view_favorites();
                }


            }catch (Exception e){
                Toast.makeText(this,"Exception Error in GET ALL Movies", Toast.LENGTH_SHORT).show();
            }

        }
    }

    void api_request(Call<MovieResults> call){
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {
                listItems=response.body().getResults();
                moviesInstance.addAll(listItems);
                recyclerView.setAdapter(new RecyclerViewAdapter(listItems, getApplicationContext()));
                recyclerView.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Can't Fetch the data ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void view_favorites(){
        //Viewmodel
        noteViewModel=ViewModelProviders.of(this).get(FavoriteViewModel.class);
        noteViewModel.getAllFavorites().observe(this,new Observer<List<Favorite>>(){

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
                // adapter crash when there is no data
               // recyclerView_dAdapter.setmovies(movies);
                //recyclerView.setItemAnimator(new DefaultItemAnimator());

            }
        });

        /*
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                movie m=recyclerView_dAdapter.getmovieAt(viewHolder.getAdapterPosition());
                Favorite f=new Favorite(m.getId(),m.getTitle(),m.getVoteAverage(),m.getPosterPath(),m.getOverview(),m.getReleaseDate(),m.getBackdropPath());


                noteViewModel.delete(f);
            }
        }).attachToRecyclerView(recyclerView);
        */
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
            FavoriteViewModel noteViewModel=ViewModelProviders.of(this).get(FavoriteViewModel.class);
            noteViewModel.deleteall();
            Toast.makeText(MainActivity.this,"All Favorite movies are deleted", Toast.LENGTH_SHORT).show();

        }
        if(id==R.id.popular_menu && view_sort!=1){
            //change to menu1
            view_sort=1;
            invalidateOptionsMenu();
            Get_All_movies(0);
        }
        if(id==R.id.topRated_menu && view_sort!=2){
            //change to menu2
            view_sort=2;
            invalidateOptionsMenu();
            Get_All_movies(0);
        }
        if(id==R.id.favorite_menu && view_sort!=3){
            //change to menu2
            view_sort=3;
            invalidateOptionsMenu();
            Get_All_movies(0);
        }
        return super.onOptionsItemSelected(item);
    }
}
