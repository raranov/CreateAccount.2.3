package com.example.createaccount23.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.createaccount23.R;
import com.example.createaccount23.databinding.FragmentCreateaccount1Binding;
import com.example.createaccount23.utils.NonSwipeableViewPager;
import com.example.createaccount23.utils.SharedPreferencesUtils;
import com.example.createaccount23.viewmodel.CreateAccountVM;

import java.util.Objects;

public class CreateAccount1Fragment extends Fragment {
    private CreateAccountVM viewmodel;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentCreateaccount1Binding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_createaccount1, container, false);
        View view = binding.getRoot();
        viewmodel  = ((CreateAccount) Objects.requireNonNull(getActivity())).viewmodel;
        viewmodel.init();
        final NonSwipeableViewPager viewPager = ((CreateAccount)getActivity()).findViewById(R.id.vpCreateAccount);
        binding.edtFirstName.requestFocus();

        // Next Button
        binding.edtLastName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT){

                    if(!binding.edtFirstName.getText().toString().isEmpty()
                        && !binding.edtLastName.getText().toString().isEmpty()
                        && (binding.radioGroup.getCheckedRadioButtonId() != -1)){
                        binding.btnContinue.performClick();
                    }
                    else{
                        Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    }

                }
                return false;
            }
        });

        // Continue Button
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewmodel.nameInput(binding.edtFirstName.getText().toString(),
                        binding.edtLastName.getText().toString(),
                        binding.radioGroup.getCheckedRadioButtonId()).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean updated) {
                        if(!updated){
                            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            ((CreateAccount) Objects.requireNonNull(getActivity())).scrollToFragment(viewPager.getCurrentItem() + 1);
                            SharedPreferencesUtils.setSharedPreferencesRefName(binding.edtFirstName.getText().toString(),
                                    sharedPreferences, editor, getContext());
                        }
                    }
                });
            }
        });

        return view;
    }
}

