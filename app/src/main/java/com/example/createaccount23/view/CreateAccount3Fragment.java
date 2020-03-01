package com.example.createaccount23.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.createaccount23.R;
import com.example.createaccount23.databinding.FragmentCreateaccount3Binding;
import com.example.createaccount23.model.User;
import com.example.createaccount23.utils.NonSwipeableViewPager;
import com.example.createaccount23.utils.SharedPreferencesUtils;
import com.example.createaccount23.utils.Singleton;
import com.example.createaccount23.viewmodel.CreateAccountVM;

public class CreateAccount3Fragment extends Fragment {
    private CreateAccountVM viewmodel;
    private static final String TAG = "CREATE ACCOUNT";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private MutableLiveData<Boolean> confirmed = new MutableLiveData<>();
    LifecycleOwner lifecycleOwner = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentCreateaccount3Binding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_createaccount3, container, false);
        final View view = binding.getRoot();
        viewmodel = ((CreateAccount) requireActivity()).viewmodel;
        viewmodel.init();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sharedPreferences.edit();
        final NonSwipeableViewPager viewPager = ((CreateAccount) getActivity()).findViewById(R.id.vpCreateAccount);


        // Back Button
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CreateAccount) requireActivity()).scrollToFragment(viewPager.getCurrentItem() - 1);
            }
        });


        // Next Button
        binding.edtConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    binding.btnContinue.performClick();
                }
                return false;
            }
        });

        // Continue Button
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewmodel.passwordInput(binding.edtPassword.getText().toString(),
                        binding.edtConfirmPassword.getText().toString()).observe(getViewLifecycleOwner(),
                        new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer response) {
                                if (response == 0) {
                                    //password input was valid, now create user in DB
                                    viewmodel.createDBUser(viewmodel.getEmail()).observe(lifecycleOwner, new Observer<User>() {
                                        @Override
                                        public void onChanged(User user) {
                                            if (user != null) {
                                                viewmodel.initializeSingleton(viewmodel.getEmail()).observe(lifecycleOwner,
                                                        new Observer<Boolean>() {
                                                            @Override
                                                            public void onChanged(Boolean singletonChanged) {
                                                                if (singletonChanged) {
                                                                    Log.e(TAG, "Singleton Changed, new user is " + Singleton.INSTANCE.getCurrentUser()
                                                                            .getValue().getId());

                                                                    SharedPreferencesUtils.setSharedPreferencesID(viewmodel.getEmail(),
                                                                            sharedPreferences, editor, getContext());


                                                                }
                                                            }
                                                        });

                                            } else {
                                                Log.e(TAG, "User not created, Singleton not changed");
                                            }
                                        }
                                    });
                                }
                            }
                        });
            }
        });


        return view;


    }


}