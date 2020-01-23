package com.example.moka.popmovies.UI.Movie_Details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moka.popmovies.R;
import com.example.moka.popmovies.Models.Review;

import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Review> ReviewList;

    public ReviewAdapter(Context mContext, List<Review> ReviewList){
        this.mContext = mContext;
        this.ReviewList = ReviewList;

    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_review, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder viewHolder, int i){
        viewHolder.userName.setText(ReviewList.get(i).getAuthor());
        viewHolder.reviewcontent.setText(ReviewList.get(i).getContent());


    }

    @Override
    public int getItemCount(){

        return ReviewList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView userName;
        public TextView reviewcontent;

        public MyViewHolder(View view){
            super(view);
            userName = (TextView) view.findViewById(R.id.username);
            reviewcontent = (TextView) view.findViewById(R.id.review);
        }
    }

}
