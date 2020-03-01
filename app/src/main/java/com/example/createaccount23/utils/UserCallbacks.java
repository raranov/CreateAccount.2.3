package com.example.createaccount23.utils;

import androidx.annotation.NonNull;

import com.example.createaccount23.model.User;

public interface UserCallbacks {
    void onSuccess(@NonNull User user);
    void onError(@NonNull Throwable throwable);
}
