package com.example.createaccount23.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.createaccount23.R;
import com.example.createaccount23.model.JsonKeyValuePair;
import com.example.createaccount23.model.User;
import com.example.createaccount23.repository.UserRepository;
import com.example.createaccount23.utils.Singleton;
import com.example.createaccount23.utils.UserCallbacks;

public class CreateAccountVM extends ViewModel {
    private static final String TAG = "CREATE_ACCOUNT";
    private UserRepository userRepository;
    private User newUser = new User();
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void init(){
        // return if already retrieved data
        if(userRepository != null){
            return;
        }
        userRepository = UserRepository.getInstance();
    }


    public LiveData<Boolean> nameInput(String first, String last, int gender){
        MutableLiveData<Boolean> updated = new MutableLiveData<>();

        if(first.isEmpty() || last.isEmpty() || gender == -1){
            updated.setValue(false);
            return updated;
        }
        else{
            newUser.setFirstName(first);
            newUser.setLastName(last);

            switch (gender){
                case R.id.rdbMale:
                    newUser.setGender(User.Gender.MALE);
                    break;
                case R.id.rdbFemale:
                    newUser.setGender(User.Gender.FEMALE);
                    break;
            }

            updated.setValue(true);
            return updated;
        }
    }

    public LiveData<Integer> emailInput(String email, String confirmEmail){
        MutableLiveData<Integer> response = new MutableLiveData<>();
        response.setValue(100);

        if(email.isEmpty() || confirmEmail.isEmpty()){
            response.setValue(1);
            return response;
        }
        else if(!email.equals(confirmEmail)){
            response.setValue(2);
            return response;
        }
        else {
            newUser.setEmail(email);
            response.setValue(0);
            this.email = email;
            return response;
        }
    }

    public LiveData<Integer> passwordInput(String password, String confirmPassword){
        final MutableLiveData<Integer> response = new MutableLiveData<>();
        response.setValue(100);

        if(password.isEmpty() || confirmPassword.isEmpty()){
            response.setValue(1);
            return response;
        }
        else if(!password.equals(confirmPassword)){
            response.setValue(2);
            return response;
        }
        else {
            newUser.setPassword(password);

            response.setValue(0); //password set
            this.password = password;
            return response;
        }
    }


    public LiveData<User> createDBUser(String userID){
        final MutableLiveData<User> userCreated = new MutableLiveData<>();

        userRepository.createNewUser(userID, newUser.getFirstName(), newUser.getLastName(),
                newUser.getEmail(),newUser.getPassword(), new UserCallbacks() {
                    @Override
                    public void onSuccess(@NonNull User user) {
                        JsonKeyValuePair jsonKeyValuePair = new JsonKeyValuePair(JsonKeyValuePair.Fields.gender,
                                newUser.getGender().toString());

                        userRepository.updateUser(user.getId(), jsonKeyValuePair, new UserCallbacks() {
                            @Override
                            public void onSuccess(@NonNull User user) {
                                Log.i(TAG, "User created in DB, gender recorded: " + user.getId());
                                userCreated.setValue(user);
                            }

                            @Override
                            public void onError(@NonNull Throwable throwable) {
                                Log.i(TAG, "Gender not recorded: " + throwable.getMessage());
                                userCreated.setValue(null);
                            }
                        });
                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {
                        Log.i(TAG, "User not created in DB: " + throwable.getMessage());
                        throwable.printStackTrace();
                        userCreated.setValue(null);
                    }
                });



        return userCreated;
    }

    public LiveData<Boolean> initializeSingleton(String userID){
        final MutableLiveData<Boolean> singletonChanged = new MutableLiveData<>();

        userRepository.getUserById(userID).observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {

                if (user != null) {
                    Singleton.INSTANCE.getCurrentUser().setValue(user);
                    singletonChanged.setValue(true);
                    Log.e("***************", "Setting user to " + Singleton.INSTANCE.getCurrentUser().getValue().getId());
                } else {
                    singletonChanged.setValue(false);
                    Log.e("***************", "User is null, Singleton not set");
                }
            }
        });


        return singletonChanged;
    }


}