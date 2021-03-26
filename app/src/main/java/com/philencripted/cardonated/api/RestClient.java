package com.philencripted.cardonated.api;

import android.content.Context;


import com.philencripted.cardonated.utils.Util;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    //end point
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie/";

    // Context
     static Context context;

    public static Retrofit retrofit;

    // Online interceptor to detect online state
    static Interceptor onlineInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());
            int maxAge = 60; // read from cache for 60 seconds even if there is internet connection
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    //offline interceptor for cache
    static Interceptor offlineInterceptor= new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!Util.isNetworkAvailable()) {
                int maxStale = 60 * 60 * 24 * 30; // Offline cache available for 30 days
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return chain.proceed(request);
        }
    };




    // cache object
   static int cacheSize = 10 * 1024 * 1024; // 10 MB
    static File cacheDir = new File("cachedirectory");
   static Cache cache = new Cache(cacheDir, cacheSize);


    // okhttpclient
   static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            // .addInterceptor(provideHttpLoggingInterceptor()) // For HTTP request & Response data logging
            .addInterceptor(offlineInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(cache)
            .build();


   /*
    This public static method will return Retrofit client
    anywhere in the appplication
    */

    public static Retrofit getRetrofitClient(){

        //singleton instance
        //If condition to ensure we don't create multiple retrofit instances in a single application
        if (retrofit == null) {

            //Defining the Retrofit using Builder
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)//This is the only mandatory call on Builder object.
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)// Converter library used to convert response into POJO
                    .build();
        }

        return retrofit;
    }
}
