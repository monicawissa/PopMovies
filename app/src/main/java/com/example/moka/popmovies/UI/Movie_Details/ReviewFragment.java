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
import com.example.moka.popmovies.data.Models.Review;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.utilities.CheckInternetConnection;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class ReviewFragment extends Fragment {



    //Review recycleview
    private RecyclerView Review_recyclerView;
    private List<Review> reviewList;




    @SuppressLint("ValidFragment")
    public ReviewFragment(List<Review> reviews) {
        reviewList=reviews;
    }

    public static ReviewFragment newInstance(List<Review> reviews)
    {
        return new ReviewFragment(reviews);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_review, container, false);


        Review_recyclerView = root.findViewById(R.id.reviewsRcl);
        Review_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager Review_mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        Review_recyclerView.setLayoutManager(Review_mLayoutManager);
        Review_recyclerView.setAdapter(new ReviewAdapter(getActivity(), reviewList));
        Review_recyclerView.smoothScrollToPosition(0);
        //handling if it's in portrait position or in the rotation position


        return root;
    }

}
