package com.example.myapplication.webservice;

import com.example.myapplication.bean.PostBean;
import com.example.myapplication.bean.ProductBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface MyApiEndpointInterface {
    @GET("posts")
    Call<List<PostBean>> getPosts();
    @GET("posts/{id}")
    Call<List<PostBean>> getPostbyId(@Path("id") int id);
    @GET("posts")
    Call<List<PostBean>> createPost(@Body PostBean post);
}
