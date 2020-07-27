package com.itismyexperience.app.viewmodel.YourName;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.itismyexperience.app.model.JsonKeyValuePair;
import com.itismyexperience.app.model.User;
import com.itismyexperience.app.repository.UserRepository;
import com.itismyexperience.app.utils.Singleton;
import com.itismyexperience.app.utils.UserCallbacks;


public class YourNameReferenceVM extends ViewModel {
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

    public LiveData<Integer> validateReferenceNameInput(String referenceName) {
        if (Singleton.INSTANCE.getCurrentUser().getValue() != null) {
            Singleton.INSTANCE.getCurrentUser().getValue().setReferenceName(referenceName);
        }

        final MutableLiveData<Integer> response = new MutableLiveData<>();
        response.setValue(100);

        if(referenceName.isEmpty()){
            response.setValue(1);
            return response;
        }
        else{
            response.setValue(0);
            return response;
        }
    }

    public LiveData<Integer> updateReferenceName(String referenceName){
        final MutableLiveData<Integer> response = new MutableLiveData<>();
        JsonKeyValuePair jsonKeyValuePair = new JsonKeyValuePair();

        jsonKeyValuePair.setField(JsonKeyValuePair.Fields.name_reference);
        jsonKeyValuePair.setValue(referenceName);

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
