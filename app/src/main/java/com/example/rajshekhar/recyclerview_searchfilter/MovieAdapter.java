package com.example.rajshekhar.recyclerview_searchfilter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.rajshekhar.recyclerview_searchfilter.bean.Movie;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>implements Filterable {

     private List<Movie>movieList;
     private List<Movie>movieListFilterd;
     private Context context;

public void setMovieList(Context context,final List<Movie>movieList){
    this.context=context;
    if(this.movieList==null){
        this.movieList=movieList;
        this.movieListFilterd=movieList;
        notifyItemChanged(0,movieListFilterd.size());
    }else {
        final DiffUtil.DiffResult result=DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return MovieAdapter.this.movieList.size();
            }

            @Override
            public int getNewListSize() {
                return movieList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPostion, int newItemPostion) {
                return MovieAdapter.this.movieList.get(oldItemPostion).getTitle()==movieList.get(newItemPostion).getTitle();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPostion, int newItemPostion) {
                Movie newMovie=MovieAdapter.this.movieList.get(oldItemPostion);

                Movie oldMovie=movieList.get(newItemPostion);
                return newMovie.getTitle()==oldMovie.getTitle();


            }
        });
        this.movieList=movieList;
        this.movieListFilterd=movieList;
        result.dispatchUpdatesTo(this);
    }
}



    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movielist_adapter,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.title.setText(movieListFilterd.get(position).getTitle());
        Glide.with(context).load(movieList.get(position).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(myViewHolder.image);

    }

    @Override
    public int getItemCount() {
    if(movieList!=null){
        return movieListFilterd.size();
    }else {
        return 0;
    }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
               String charString =constraint.toString();
               if(charString.isEmpty()){
                   movieListFilterd=movieList;
               }else {
                   List<Movie>filterList= new ArrayList<>();
                   for (Movie movie:movieList){
                       if(movie.getTitle().toLowerCase().contains(charString.toLowerCase())){
                           filterList.add(movie);
                       }
                   }
                   movieListFilterd=filterList;
               }
               FilterResults filterResults= new FilterResults();
               filterResults.values=movieListFilterd;
               return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                movieListFilterd=(ArrayList<Movie>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            image = (ImageView)view.findViewById(R.id.image);
        }
}}
