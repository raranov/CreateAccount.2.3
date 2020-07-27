package com.itismyexperience.app.network;

import com.itismyexperience.app.model.JsonKeyValuePair;
import com.itismyexperience.app.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
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
