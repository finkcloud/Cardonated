package com.philencripted.cardonated.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.philencripted.cardonated.R;
import com.philencripted.cardonated.model.Result;
import com.philencripted.cardonated.ui.MovieDetailsActivity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.UserViewHolder> {
    private List<Result> movieList;
     Context context;

    public MovieAdapter(Context context, List<Result> movieList){
        this.context = context;
        this.movieList = movieList;
    }



    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // create view for binding data to UI inflated from our list item
        View view = LayoutInflater.from(context).inflate(R.layout.movie_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        // get reference to a single moview
        final Result movieresult = movieList.get(position);

        // bind movie to UI
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/"+movieresult.getPosterPath()).into(holder.imageView);        holder.movieTitleTV.setText(movieresult.getTitle());
        holder.overViewTV.setText(movieresult.getOverview());



        String newOverview;
        if(movieresult.getOverview().length() > 50)
        {
            newOverview =  movieresult.getOverview().substring(0, 49);

        }else {
            newOverview = movieresult.getOverview();
        }

        holder.overViewTV.setText(newOverview);
        holder.releaseDateTv.setText(movieresult.getReleaseDate());


        // handle details navigation with movie info

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // open detail activity on item click
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra("title", movieresult.getTitle());
                intent.putExtra("date", movieresult.getReleaseDate());
                intent.putExtra("backdrop",movieresult.getBackdropPath());
                intent.putExtra("overview",movieresult.getOverview());

                intent.putExtra("poster", movieresult.getPosterPath());
                intent.putExtra("vote_count", movieresult.getVoteCount());
                intent.putExtra("vote_ava", movieresult.getVoteAverage());
                intent.putExtra("popularity", movieresult.getPopularity());


                context.startActivity(intent); // start Intent
            }
        });
    }


    // get the no of items in the list
    @Override
    public int getItemCount() {
        if(movieList.size() > 0){
            return movieList.size();
        }else {
            return 0;
        }
    }


    // most extend view holder from recyclerview  for binding view to the inflated list item in recycle view
    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView movieTitleTV;
        private TextView overViewTV;
        private TextView releaseDateTv;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.cover);
            this.movieTitleTV = itemView.findViewById(R.id.title);
            this.overViewTV = itemView.findViewById(R.id.overview);
            this.releaseDateTv = itemView.findViewById(R.id.votecount);
        }


    }
}
