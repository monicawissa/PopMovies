package com.example.moka.popmovies.UI.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moka.popmovies.data.source.AppRepository;
import com.example.moka.popmovies.utilities.ActivityUtils;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.utilities.Injection;

public class MainActivity extends AppCompatActivity {
    // choosing by default menu1
    // menu1 = popular...menu2 = top rate...menu3 = favorite

    //choosing the sort order "popular or TopRated or favorite"
    // choosing by default popular
    private MoviesFilterType moviesFilterType=MoviesFilterType.popular;
    private static final String sort_KEY = "SORT_KEY";
    private MovieFragment fragment;
    private MovieListPresenter movieListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            fragment=(MovieFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentMovie);
            try {
                if(fragment==null){
                    fragment= MovieFragment.getInstance();
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),fragment,R.id.frame_movies);
                }
                movieListPresenter=new MovieListPresenter(fragment,
                        Injection.provideTasksRepository(getApplicationContext(),"https://api.themoviedb.org/3/")
                );
                // Load previously saved state, if available.
                if (savedInstanceState != null) {
                    MoviesFilterType currentFiltering =
                            (MoviesFilterType) savedInstanceState.getSerializable(sort_KEY);
                    movieListPresenter.setFiltering(currentFiltering);
                }
            }catch (Exception e){
                Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show();
                Log.d("taggg", "Get_All_movies: ."+e);
            }

        }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(sort_KEY, movieListPresenter.getFiltering());

        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(moviesFilterType==MoviesFilterType.popular){
            getMenuInflater().inflate(R.menu.menu1,menu);}
        else if(moviesFilterType==MoviesFilterType.top) {
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
            movieListPresenter.setFiltering(MoviesFilterType.deleteAllFavorite);
        }
        if(id==R.id.popular_menu && !(moviesFilterType==MoviesFilterType.popular)){
            //change to menu1
            movieListPresenter.setFiltering(MoviesFilterType.popular);
            moviesFilterType=MoviesFilterType.popular;
            invalidateOptionsMenu();
        }
        if(id==R.id.topRated_menu && !(moviesFilterType==MoviesFilterType.top)){
            //change to menu2
            movieListPresenter.setFiltering(MoviesFilterType.top);
            moviesFilterType=MoviesFilterType.top;
            invalidateOptionsMenu();
        }
        if(id==R.id.favorite_menu && !(moviesFilterType==MoviesFilterType.favorite)){
            //change to menu2
            movieListPresenter.setFiltering(MoviesFilterType.favorite);
            moviesFilterType=MoviesFilterType.favorite;
            invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }
}
