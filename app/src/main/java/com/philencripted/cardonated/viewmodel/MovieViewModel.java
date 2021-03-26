package com.philencripted.cardonated.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.philencripted.cardonated.api.RestApi;
import com.philencripted.cardonated.api.RestClient;
import com.philencripted.cardonated.model.Movie;
import com.philencripted.cardonated.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieViewModel extends ViewModel {
    //this is the data that we will fetch asynchronously
    private MutableLiveData<Movie> movieData;

    //we will call this method to get the data
    // this will help save orientation change issues and retain our data with the help of viewmodel
    public LiveData<Movie> getMovies() {
        //if the object is null
        if (movieData == null) {
            movieData = new MutableLiveData<>();
            //we will load it asynchronously from server in this method
            loadMovies();
        }

        //finally we will return the objects
        return movieData;
    }

    //This method is using Retrofit to get the JSON data from URL
    private void loadMovies() {

        //Obtain an instance of Retrofit by calling the static method.
       Retrofit retrofit =  RestClient.getRetrofitClient();
       /*
       The main purpose of Retrofit is to create HTTP calls from the Java interface based
       on the annotation associated with each method. This is achieved by just passing the interface
       class as parameter to the create method
       */
        RestApi userApi = retrofit.create(RestApi.class);

       /*
       Invoke the method corresponding to the HTTP request which will return a Call object. This Call
       object will used to send the actual network request with the specified parameters
       */

        Call<Movie> call = userApi.getAllMovies("cda399036baa0e49365b3bd0c1969bcc");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                //finally we are setting the list to our MutableLiveData
                movieData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });


    }
}
