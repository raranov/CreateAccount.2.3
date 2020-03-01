package com.example.createaccount23.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.createaccount23.model.JsonKeyValuePair;
import com.example.createaccount23.model.User;
import com.example.createaccount23.network.APIInterface;
import com.example.createaccount23.utils.UserCallbacks;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

// build access points to access webservice
public class UserRepository {
    private static final long NETWORK_TIMEOUT_SEC = 60;
    private static UserRepository instance;
    private static Retrofit retrofit;


    public static Retrofit getRetrofit(){
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.level(HttpLoggingInterceptor.Level.BODY);//TODO: THIS MUST MUST MUST BE CHANGED BEFORE RELEASE!!

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
                    .readTimeout(NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
                    .writeTimeout(NETWORK_TIMEOUT_SEC, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://itismyexperience.com/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }

    private APIInterface apiInterface = getRetrofit().create(APIInterface.class);

    public static UserRepository getInstance(){
        if  (instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public LiveData<User> getUserById(String id){
        final MutableLiveData<User> newUser = new MutableLiveData<>();
        Call<User> call = apiInterface.getUserByID(id);
        Log.e("*******************", "GETting user with id " + id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    newUser.setValue(null);
                    Log.e(TAG, response.code() + response.message());
                    return;
                }
                newUser.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                newUser.setValue(null);
                Log.e(TAG, "Call failed. " + t.getMessage());
            }
        });
        return newUser;
    }


    public User createNewUser(String userID, String firstName, String lastName,
                              String email, String password, final UserCallbacks callbacks){
        User user = new User(userID, firstName, lastName, email, password);
        final User createdUser = new User();
        Call<User> call = apiInterface.createUser(user);
        final UserCallbacks userCallbacks = callbacks;

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = new User();
                if (!response.isSuccessful()) {
                    return;
                    //todo: handle this case
                }
                user = response.body();

                if (userCallbacks != null)
                    userCallbacks.onSuccess(Objects.requireNonNull(user));

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (callbacks != null)
                    callbacks.onError(t);
            }
        });
        return  createdUser;
    }

    public Boolean updateUser(String id, JsonKeyValuePair jsonKeyValuePair, final UserCallbacks callbacks){
        Call<User> call = apiInterface.updateUser(id, jsonKeyValuePair);
        final Boolean updateOperationOutcome;
        final UserCallbacks userCallbacks = callbacks;

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = new User();
                if (!response.isSuccessful()) {
                    return;
                    //todo: handle this case
                }
                user = response.body();

                if (userCallbacks != null)
                    userCallbacks.onSuccess(user);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (callbacks != null)
                    callbacks.onError(t);
            }
        });
        return  true;//todo
    }




}
