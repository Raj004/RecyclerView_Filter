package com.example.rajshekhar.recyclerview_searchfilter.http;

import com.example.rajshekhar.recyclerview_searchfilter.bean.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("volley_array.json")
    Call<List<Movie>>getMovie();

}
