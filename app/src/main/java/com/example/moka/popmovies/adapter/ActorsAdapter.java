package com.example.moka.popmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moka.popmovies.R;
import com.example.moka.popmovies.jsonmovie.Cast;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cast> ActorsList;

    public ActorsAdapter(Context mContext, List<Cast> ActorsList){
        this.mContext = mContext;
        this.ActorsList = ActorsList;

    }

    @Override
    public ActorsAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_cast, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ActorsAdapter.MyViewHolder viewHolder, int i){
        viewHolder.title.setText(ActorsList.get(i).getName());


        Picasso.with(mContext)
                .load(ActorsList.get(i).getProfilePath())
                .placeholder(R.drawable.img_loading_cover)
                .error(R.drawable.img_loading_error)
                .into(viewHolder.thumbnail);

    }

    @Override
    public int getItemCount(){

        return ActorsList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.name);
            thumbnail = (ImageView) view.findViewById(R.id.image);
        }
    }
}
