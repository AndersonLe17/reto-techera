package com.techera.reto.retrofit.Interface;

import com.techera.reto.retrofit.Model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UsersInterface {

    @GET("users")
    Call<List<Users>> getUsers();

}
