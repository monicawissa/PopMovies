package com.example.moka.popmovies.UI.Movie_Details;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moka.popmovies.BuildConfig;
import com.example.moka.popmovies.data.Models.Trailer;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.utilities.CheckInternetConnection;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class TrailerFragment extends Fragment {



    //Trailer recycleview
    private RecyclerView Trailer_recyclerView;
    private List<Trailer> trailerList;




    @SuppressLint("ValidFragment")
    public TrailerFragment(List<Trailer> trailers) {
        trailerList=trailers;
    }

    public static TrailerFragment newInstance(List<Trailer> trailers) {
        return new TrailerFragment(trailers);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_trailer, container, false);


        Trailer_recyclerView = root.findViewById(R.id.trailerRcl);
        Trailer_recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        Trailer_recyclerView.setLayoutManager(mLayoutManager);
        Trailer_recyclerView.setAdapter(new TrailerAdapter(getActivity(), trailerList));
        Trailer_recyclerView.smoothScrollToPosition(0);
        //handling if it's in portrait position or in the rotation position


        return root;
    }

}
