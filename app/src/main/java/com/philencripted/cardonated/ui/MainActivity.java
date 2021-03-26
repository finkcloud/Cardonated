package com.philencripted.cardonated.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.philencripted.cardonated.R;
import com.philencripted.cardonated.adapter.MovieAdapter;
import com.philencripted.cardonated.model.Movie;
import com.philencripted.cardonated.model.Result;
import com.philencripted.cardonated.utils.Util;
import com.philencripted.cardonated.viewmodel.MovieViewModel;


import java.util.List;

public class MainActivity extends AppCompatActivity {
    // declare view model
    private MovieViewModel movieViewModel;

     ProgressBar progressBar;
    private RecyclerView userRecyclerview;
    private MovieAdapter movieAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // hold context reference for non activity class use. eg. Utils class
        Util.set_context(MainActivity.this);

        userRecyclerview = findViewById(R.id.movie_recycler_view);
        progressBar = findViewById(R.id.progress_circular);

        //getting instance of the view model to render view
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        // observe view model live data for changes and update ui
        LiveData<Movie> userliveData = movieViewModel.getMovies();
        userliveData.observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movies) {
                // show progress while loading data
                progressBar.setVisibility(View.VISIBLE);

                // setup movie list
                setUpUserList(movies);

                //hide progress after ui is populated
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    // populated list with data from adapter
    private void setUpUserList(Movie userList){
        userRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerview.setHasFixedSize(true);
        userRecyclerview.setItemAnimator(new DefaultItemAnimator());
        movieAdapter = new MovieAdapter(this, userList.getResults());
        userRecyclerview.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }
}