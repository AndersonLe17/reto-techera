package com.techera.reto.retrofit.Interface;

import com.techera.reto.retrofit.Model.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostsInterface {

    @GET("posts")
    Call<List<Posts>> getPosts();

    @GET("users/{userId}/posts")
    Call<List<Posts>> getPostsByUserId(@Path("userId") Integer userId);

    @Headers("Accept: application/json; charset=UTF-8")
    @POST("posts")
    Call<Posts> savePosts(@Body Posts posts);

    @Headers("Accept: application/json; charset=UTF-8")
    @PUT("posts/{id}")
    Call<Posts> updatePosts(@Path("id") Integer id, @Body Posts posts);

    @DELETE("posts/{id}")
    Call<Void> deletePosts(@Path("id") Integer id);
}
