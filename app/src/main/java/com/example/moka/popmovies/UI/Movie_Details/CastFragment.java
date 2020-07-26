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
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.data.Models.Cast;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class CastFragment extends Fragment {
    public int movie_id;

    //Cast recycleview
    private RecyclerView cast_recyclerView;
    private List<Cast> castList;
    @SuppressLint("ValidFragment")
    public CastFragment(List<Cast> casts) {
        castList=casts;
    }

    public static CastFragment newInstance(List<Cast>casts) {
        return new CastFragment(casts);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cast, container, false);


        cast_recyclerView = root.findViewById(R.id.castRcl);
        cast_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager Cast_mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        cast_recyclerView.setLayoutManager(Cast_mLayoutManager);
        cast_recyclerView.setAdapter(new ActorsAdapter(getContext(), castList));
        cast_recyclerView.smoothScrollToPosition(0);

        //handling if it's in portrait position or in the rotation position


        return root;
    }

}