package com.philencripted.cardonated.api;

import com.philencripted.cardonated.model.Movie;
import com.philencripted.cardonated.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RestApi {


/*
get list of users from dummy api max 10
*/
//    @Headers("Cache-Control: max-age=640000")
    @GET("popular")
    Call<Movie> getAllMovies(@Query("api_key") String apikey);
}
