package com.example.createaccount23.viewmodel.YourName;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.createaccount23.model.JsonKeyValuePair;
import com.example.createaccount23.model.User;
import com.example.createaccount23.repository.UserRepository;
import com.example.createaccount23.utils.Singleton;
import com.example.createaccount23.utils.UserCallbacks;


public class YourNameMiddleVM extends ViewModel {
    private UserRepository userRepository;
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void init(){
        // return if already retrieved data
        if(userRepository != null){
            return;
        }
        userRepository = UserRepository.getInstance();
    }

    public LiveData<Integer> validateMiddleNameInput(String middlename) {
        final MutableLiveData<Integer> response = new MutableLiveData<>();
        response.setValue(100);

        if(middlename.isEmpty()){
            response.setValue(1);
            return response;
        }
        else{
            response.setValue(0);
            return response;
        }
    }

    public LiveData<Integer> updateMiddleName(String middlename){
        if (Singleton.INSTANCE.getCurrentUser().getValue() != null) {
            Singleton.INSTANCE.getCurrentUser().getValue().setMiddleName(middlename);
        }

        final MutableLiveData<Integer> response = new MutableLiveData<>();
        JsonKeyValuePair jsonKeyValuePair = new JsonKeyValuePair();

        jsonKeyValuePair.setField(JsonKeyValuePair.Fields.name_middle);
        jsonKeyValuePair.setValue(middlename);

        userRepository.updateUser(userID, jsonKeyValuePair, new UserCallbacks() {
            @Override
            public void onSuccess(@NonNull User user) {
                response.setValue(0);
            }

            @Override
            public void onError(@NonNull Throwable throwable) {
                response.setValue(1);
            }
        });

        return response;
    }
}
