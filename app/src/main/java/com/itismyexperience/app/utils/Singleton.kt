package com.itismyexperience.app.utils

import androidx.lifecycle.MutableLiveData
import com.itismyexperience.app.model.User

object Singleton {

    val currentUser: MutableLiveData<User> by lazy{
        MutableLiveData<User>()

    }
}
