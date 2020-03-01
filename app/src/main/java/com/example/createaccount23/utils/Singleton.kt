package com.example.createaccount23.utils

import androidx.lifecycle.MutableLiveData
import com.example.createaccount23.model.User

object Singleton {

    val currentUser: MutableLiveData<User> by lazy{
        MutableLiveData<User>()

    }
}
