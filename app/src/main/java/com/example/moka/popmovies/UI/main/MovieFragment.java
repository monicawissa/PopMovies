package com.example.moka.popmovies.UI.main;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.moka.popmovies.R;
import com.example.moka.popmovies.data.Models.Movie;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieList_contract.View {

    public MovieFragment() {
        // Required empty public constructor
    }
    public static MovieFragment getInstance(){
        return new MovieFragment();
    }
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerView_dAdapter;

    private Parcelable savedRecyclerLayoutState = null;
    //save state
    private static String LIST_STATE = "list_state";
    private static final String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";
    private ArrayList<Movie> moviesInstance = new ArrayList<>();
    private MovieList_contract.Presenter mPresenter;
    private ProgressBar pgsBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movie, container, false);

        pgsBar = (ProgressBar)root.findViewById(R.id.progress_bar);
        recyclerView = root.findViewById(R.id.m_RecyclerView);
        recyclerView.setHasFixedSize(true);
        //handling if it's in portrait position or in the rotation position
        GridLayoutManager gridLayoutManager;
        if (this.getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
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
            mPresenter.execute(false);
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mPresenter.start();
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

    public void showData() {
        recyclerView_dAdapter=new RecyclerViewAdapter(moviesInstance, getActivity());
        recyclerView.setAdapter(recyclerView_dAdapter);
        recyclerView_dAdapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(0);
    }
    @Override
    public void setPresenter(MovieList_contract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if(active){
            pgsBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else {
            pgsBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showErrorMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        //Toast.makeText(this.getContext(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showMovies(List<Movie> Movies) {
        moviesInstance.clear();
        moviesInstance.addAll(Movies);
        showData();
    }

    @Override
    public void showSuccessfullyDeleteFavoritesMessage() {
        Snackbar.make(getView(), "success delete Movies", Snackbar.LENGTH_LONG).show();

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
