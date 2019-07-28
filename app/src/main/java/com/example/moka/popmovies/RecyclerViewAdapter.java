package com.example.moka.popmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.moka.popmovies.jsonmovie.movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.moka.popmovies.jsonmovie.movie.*;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int previousPosition = 0;

    private List<movie> List_Item;
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

            Glide.with(context)
                    .load(List_Item.get(position).getPosterPath())
                    .into(menuItemHolder.imageView);


//                if (position > previousPosition) { //scrolling DOWN
//                    AnimationUtil.animate(menuItemHolder, true);
//
//                } else { // scrolling UP
//
//                    AnimationUtil.animate(menuItemHolder, false);
//                }
//                previousPosition = position;


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
                        intent.putExtra("original_title",movieclicked.getOriginalTitle());
                        intent.putExtra("poster_path",movieclicked.getPosterPath());
                        intent.putExtra("overview",movieclicked.getOverview());
                        intent.putExtra("vote_average",movieclicked.getVoteAverage());
                        intent.putExtra("release_date",movieclicked.getReleaseDate());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
