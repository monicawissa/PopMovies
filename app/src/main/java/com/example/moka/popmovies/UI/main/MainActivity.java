package com.example.moka.popmovies.UI.main;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.moka.popmovies.BuildConfig;
import com.example.moka.popmovies.utilities.ActivityUtils;
import com.example.moka.popmovies.utilities.CheckInternetConnection;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.Room.FavoriteViewModel;

public class MainActivity extends AppCompatActivity {
    // choosing by default menu1
    // menu1 = popular...menu2 = top rate...menu3 = favorite

    //choosing the sort order "popular or TopRated or favorite"
    // choosing by default popular
    private int view_sort=1;




    private MovieFragment fragment;
    private MovieListPresenter movieListPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //fragment=(MovieFragment)getSupportFragmentManager().findFragmentById(R.id.fragmentMovie);
        Get_All_movies();
    }


       private void Get_All_movies() {

        CheckInternetConnection cic = new CheckInternetConnection(getApplicationContext());
        Boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                //naming convention
                if(BuildConfig.the_movie_db_API_token.isEmpty()){
                    Toast.makeText(this, getString(R.string.api_NotFound), Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("TAG",""+view_sort);
                String option ="popular";
                switch (view_sort){
                    case 2:option="top";break;
                    case 3:option="favorite";break;
                    default:
                }
                if(fragment==null){
                    fragment= new MovieFragment();
                    fragment.setattribute(option);
                    //getFragmentManager().beginTransaction().add(R.id.frame_movies,fragment).commit();
                    ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),fragment,R.id.frame_movies);
                }
                else{
                    fragment.refresh(option);
                }
                //movieListPresenter=new MovieListPresenter((FragmentActivity)this,fragment);

            }catch (Exception e){
                Toast.makeText(this,e.toString(), Toast.LENGTH_LONG).show();
                Log.d("taggg", "Get_All_movies: ."+e);
            }

        }
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
