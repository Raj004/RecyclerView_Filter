package com.example.rajshekhar.recyclerview_searchfilter.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static String BASE_URL ="http://35.200.174.74/apis/";
    private static Retrofit retrofit;
    static ApiInterface apiEndPoints;
    public  static ApiInterface getInstance(){

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();
        //add okHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        if(retrofit!=null){
            apiEndPoints = retrofit.create(ApiInterface.class);

            return apiEndPoints;
        }else{

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    // set the okhttpclient and add default connect and read timepouts
                    .client(okHttpClient.newBuilder().connectTimeout(45,TimeUnit.SECONDS).readTimeout(45,TimeUnit.SECONDS).build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            // Create an instance of our GitHub API interface.
            apiEndPoints = retrofit.create(ApiInterface.class);
        }
        return apiEndPoints;
    }

}






//public class ApiClient {
//    public static String BASE_URL ="http://35.200.174.74/apis/";
//    private static Retrofit retrofit;
//    public static Retrofit getClient(){
//        if(retrofit == null){
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }
//}