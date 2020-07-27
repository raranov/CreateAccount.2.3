package com.itismyexperience.app.utils;

import androidx.annotation.NonNull;

import com.itismyexperience.app.model.User;

public interface UserCallbacks {
    void onSuccess(@NonNull User user);
    void onError(@NonNull Throwable throwable);
}
