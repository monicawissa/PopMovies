package com.example.moka.popmovies.UI.Movie_Details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.moka.popmovies.BuildConfig;
import com.example.moka.popmovies.Models.Trailer;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.api.IonlineResponse;
import com.example.moka.popmovies.api.OnlineComponent;
import com.example.moka.popmovies.utilities.CheckInternetConnection;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TrailerFragment extends OnlineComponent  {
    
    public int movie_id;

    
    //Trailer recycleview
    private RecyclerView Trailer_recyclerView;
    private TrailerAdapter Trailer_adapter;
    private List<Trailer> TrailerList;


   

    public TrailerFragment() {

    }

    public static TrailerFragment newInstance() {
        return new TrailerFragment();
    }
    public void setattribute(int movie_id) {
        this.movie_id=movie_id;
        //this.setonlineResponse(this);
        this.execute();
    }

    @Override
    public String getPath(){
        return "https://api.themoviedb.org/3/";
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
        //handling if it's in portrait position or in the rotation position


        return root;
    }

    @Override
    public void getAttribute() {
        setOption("Trailer");
        setMovie_id(this.movie_id);
    }
    @Override
    public void onDataFetched(Object data) {
        List<Trailer> movieList= ((List<Trailer>) data);
                Trailerload_data(movieList);
               
    }



    @Override
    public void onDataError() {
        //Toast.makeText(getApplicationContext(), "Can't Fetch the data ", Toast.LENGTH_SHORT).show();
    }


    private void Trailerload_data(List<Trailer>trailers) {
        // Trailer adapter
        TrailerList = new ArrayList<>();


        CheckInternetConnection cic = new CheckInternetConnection(getActivity());
        boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            Toast.makeText(getActivity(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        } else {
            try {
                if (BuildConfig.the_movie_db_API_token.isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.api_NotFound), Toast.LENGTH_SHORT).show();

                } else {
                    TrailerList=trailers;
                    Trailer_recyclerView.setAdapter(new TrailerAdapter(getActivity(), trailers));
                    Trailer_recyclerView.smoothScrollToPosition(0);
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Exception Error", Toast.LENGTH_SHORT).show();
            }

        }
    }

}
