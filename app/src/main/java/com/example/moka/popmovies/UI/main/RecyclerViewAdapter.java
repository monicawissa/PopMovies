package com.example.moka.popmovies.UI.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moka.popmovies.UI.Movie_Details.DetailActivity;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.jsonmovie.movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<movie> List_Item = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(List<movie> list_Item, Context context) {
        List_Item = list_Item;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View menu11 = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.row_item, viewGroup, false);
        return new MenuItemViewHolder(menu11);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            MenuItemViewHolder menuItemHolder = (MenuItemViewHolder) holder;

            menuItemHolder.Title.setText(List_Item.get(position).getTitle());

            Picasso.with(context)
                    .load(List_Item.get(position).getPosterPath())
                    .placeholder(R.drawable.img_loading_cover)
                    .error(R.drawable.img_loading_error)
                    .into(menuItemHolder.imageView);


    }
    //Viewmodel**
    public movie getmovieAt(int position){
        return List_Item.get(position);
    }

    //Viewmodel
    public void setmovies(List<movie> movies) {
        this.List_Item = movies;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return (null != List_Item ? List_Item.size() : 0);
    }


    protected class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView imageView;
        private TextView Title;

        public MenuItemViewHolder(View view) {
            super(view);
            cardView = (CardView) view.findViewById(R.id.cardView);
            imageView = (ImageView) view.findViewById(R.id.img_card_Movie);
            Title = (TextView) view.findViewById(R.id.tv_card_Movie);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int i=getAdapterPosition();
                    if(i!=RecyclerView.NO_POSITION){
                        movie movieclicked=List_Item.get(i);
                        Intent intent=new Intent(context,DetailActivity.class);
                        intent.putExtra("movies",movieclicked);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }





}
