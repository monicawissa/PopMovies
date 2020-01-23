package com.example.moka.popmovies.UI.main;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moka.popmovies.R;
import com.example.moka.popmovies.Room.Favorite;
import com.example.moka.popmovies.Room.FavoriteViewModel;
import com.example.moka.popmovies.api.IonlineResponse;
import com.example.moka.popmovies.api.OnlineComponent;
import com.example.moka.popmovies.Models.movie;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends OnlineComponent implements MovieList_contract.View  {


    public MovieFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerView_dAdapter;

    private Parcelable savedRecyclerLayoutState = null;
    //save state
    private static String LIST_STATE = "list_state";
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private ArrayList<movie> moviesInstance = new ArrayList<>();
    private MovieList_contract.Presenter mPresenter;
    public String optionn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie, container, false);


        recyclerView = root.findViewById(R.id.m_RecyclerView);
        recyclerView.setHasFixedSize(true);

        //handling if it's in portrait position or in the rotation position
        GridLayoutManager gridLayoutManager;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
        } else {
            gridLayoutManager = new GridLayoutManager(getActivity(), 4);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        if (savedInstanceState != null) {
            moviesInstance = savedInstanceState.getParcelableArrayList(LIST_STATE);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            displayDataFromInstanceState();
        } else {
            this.refresh(optionn);
        }

        return root;
    }

    private void displayDataFromInstanceState() {
        recyclerView_dAdapter = new RecyclerViewAdapter(moviesInstance, getActivity());

        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerView_dAdapter);
        // restore Layout Manager Position
        if (savedRecyclerLayoutState != null)
            checkNotNull(recyclerView.getLayoutManager()).onRestoreInstanceState(savedRecyclerLayoutState);
        recyclerView_dAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(LIST_STATE, moviesInstance);
        savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT, checkNotNull(recyclerView.getLayoutManager()).onSaveInstanceState());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null){
        moviesInstance = savedInstanceState.getParcelableArrayList(LIST_STATE);
        savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);}
        super.onActivityCreated(savedInstanceState);
    }

    public void setattribute(String option) {
        this.optionn = option;
    }

    @Override
    public String getPath() {
        return "https://api.themoviedb.org/3/";
    }

    @Override
    public void getAttribute() {
        setOption(optionn);
    }

    @Override
    public void onDataFetched(Object data) {
        if (optionn.equals("top") || optionn.equals("popular")) {
            moviesInstance.addAll((List<movie>) data);
            showData();
        }
    }

    public void showData() {
        recyclerView_dAdapter=new RecyclerViewAdapter(moviesInstance, getActivity());
        recyclerView.setAdapter(recyclerView_dAdapter);
        recyclerView_dAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onDataError() {
        Toast.makeText(getActivity(), "Can't Fetch the data ", Toast.LENGTH_SHORT).show();
    }

    public void view_favorites() {
        //mPresenter.get_favorites_Presenter();
        FavoriteViewModel nodeViewModel = ViewModelProviders.of((FragmentActivity) checkNotNull(getActivity())).get(FavoriteViewModel.class);
        nodeViewModel.getAllFavorites().observe((LifecycleOwner)this,new Observer<List<Favorite>>(){

            @Override
            public void onChanged(@Nullable List<Favorite> notes) {
                //update RecycleViewAdapter

                List<movie> movies = new ArrayList<>();
                if(notes==null){
                    Toast.makeText(getActivity(),R.string.null_data, Toast.LENGTH_SHORT).show();
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
                moviesInstance.addAll(movies);
                showData();
            }
        });
    }

    public void refresh(String optionn) {
        this.optionn = optionn;
        moviesInstance.clear();
        if (optionn.equals("favorite"))
            view_favorites();
        else
        {//this.setonlineResponse(this);
            this.execute();}
    }

    @Override
    public void setPresenter(MovieList_contract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showFavoriteMovies(List<movie> movies) {
        moviesInstance.addAll(movies);
        showData();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
