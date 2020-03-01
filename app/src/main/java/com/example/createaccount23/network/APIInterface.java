package com.example.createaccount23.network;

import com.example.createaccount23.model.JsonKeyValuePair;
import com.example.createaccount23.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("allusers")
    Call<List<User>> getUsers();

    @GET("users/{id}")
    Call<User> getUserByID(@Path("id") String id);

    @POST("users")
    Call<User> createUser(@Body User user);

    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") String id, @Body JsonKeyValuePair fieldValuePair);

    @DELETE("users/{id}")
    Call<Void> deleteUser(@Path("id") String id) ;

}
