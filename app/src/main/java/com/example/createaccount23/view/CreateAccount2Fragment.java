package com.example.createaccount23.view;

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
import com.example.createaccount23.databinding.FragmentCreateaccount2Binding;
import com.example.createaccount23.utils.NonSwipeableViewPager;
import com.example.createaccount23.viewmodel.CreateAccountVM;

import java.util.Objects;

public class CreateAccount2Fragment extends Fragment {

    private CreateAccountVM viewmodel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentCreateaccount2Binding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_createaccount2, container, false);
        View view = binding.getRoot();
        viewmodel  = ((CreateAccount) Objects.requireNonNull(getActivity())).viewmodel;
        final NonSwipeableViewPager viewPager = ((CreateAccount) getActivity()).findViewById(R.id.vpCreateAccount);

        // Back Button
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CreateAccount) Objects.requireNonNull(getActivity())).scrollToFragment(viewPager.getCurrentItem() - 1);
            }
        });

        // Next Button
        binding.edtConfirmEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
                viewmodel.emailInput(binding.edtEmail.getText().toString(),
                        binding.edtConfirmEmail.getText().toString()).observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer response) {
                        switch (response) {
                            case 1:
                                Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(getActivity(), "Fields must match", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                ((CreateAccount) Objects.requireNonNull(getActivity())).scrollToFragment(viewPager.getCurrentItem() + 1);
                                break;
                        }
                    }
                });
            }
        });

        return view;

    }
}
