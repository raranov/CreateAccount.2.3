package com.itismyexperience.app.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.itismyexperience.app.R;
import com.itismyexperience.app.databinding.FragmentYournameNicknameBinding;
import com.itismyexperience.app.utils.NonSwipeableViewPager;
import com.itismyexperience.app.utils.SharedPreferencesUtils;
import com.itismyexperience.app.viewmodel.YourName.YourNameNicknameVM;

public class YourNameNicknameFragment extends Fragment {
    private YourNameNicknameVM viewmodel = new YourNameNicknameVM();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentYournameNicknameBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_yourname_nickname, container, false);
        View view = binding.getRoot();
        final NonSwipeableViewPager viewPager = ((YourName)getActivity()).findViewById(R.id.vpYourName);
        viewmodel.init();

        String userName = SharedPreferencesUtils.getSharedPreferences(
                SharedPreferencesUtils.SharedPreferenceKeys.REFERENCE_NAME.toString(), getContext());
        String text = "Do you have a nickname?";
        binding.txtNicknamePrompt.setText(text);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        viewmodel.setUserID(sharedPreferences.getString("USER_ID", ""));

        // Yes Button
        binding.btnYes.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                // User feedback
                binding.btnYes.setBackgroundResource(R.drawable.button_rectangle);
                //binding.btnYes.setTextColor(getResources().getColor(R.color.color_onPrimary));
                binding.btnYes.setTextSize(38);

                binding.btnNo.setBackgroundResource(R.drawable.button_rectangle); //todo can wrap in if statement to see if is highlighted to begin with
                binding.btnNo.setTextColor(getResources().getColor(R.color.color_accent));

                // If the user answers yes, make EDT visible and command focus
                binding.edtNickname.setVisibility(View.VISIBLE);
                binding.edtNickname.requestFocus();
                InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                Objects.requireNonNull(imm).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,0);
            }
        });

        binding.btnYes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //binding.btnYes.setTextColor(getResources().getColor(R.color.color_onPrimary));
                binding.btnNo.setTextColor(getResources().getColor(R.color.color_accent));
                binding.btnNo.setBackground(getResources().getDrawable(R.drawable.button_rectangle));

                return false;
            }
        });

        // No Button
        binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // User feedback
                binding.btnNo.setBackgroundResource(R.drawable.button_rectangle);
                //binding.btnNo.setTextColor(getResources().getColor(R.color.color_onPrimary));

                binding.btnYes.setBackgroundResource(R.drawable.button_rectangle); //todo can wrap in if statement to see if is highlighted to begin with
                binding.btnYes.setTextColor(getResources().getColor(R.color.color_accent));
                binding.edtNickname.setVisibility(View.INVISIBLE);

                // Continue to next screen and record value as *NONE
                ((YourName) requireActivity()).scrollToFragment(viewPager.getCurrentItem() + 1);
                updateNickname("*NONE");
            }
        });

        binding.btnNo.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               // binding.btnNo.setTextColor(getResources().getColor(R.color.color_onPrimary));
                binding.btnYes.setTextColor(getResources().getColor(R.color.color_accent));
                binding.btnYes.setBackground(getResources().getDrawable(R.drawable.button_rectangle));

                return false;
            }
        });

        // Back Button
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        // Skip Button
        binding.btnSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((YourName) requireActivity()).scrollToFragment(viewPager.getCurrentItem() + 1);
            }
        });


        // Keyboard next button
        binding.edtNickname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_NEXT){
                    binding.btnContinue.performClick();
                }
                return false;
            }
        });

        // Continue Button
        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewmodel.validateNicknameInput(binding.edtNickname.getText().toString())
                        .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer response) {
                                switch (response){
                                    case 1:
                                        Toast.makeText(getContext(), "please answer or skip", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 0:
                                        ((YourName) requireActivity()).scrollToFragment(viewPager.getCurrentItem() + 1);
                                        break;
                                }

                                if(response == 0){
                                    updateNickname(binding.edtNickname.getText().toString());
                                }
                            }
                        });
            }
        });


        return view;
    }

    private void updateNickname(String nickname){
        viewmodel.updateNickname(nickname)
                .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer updatedResponse) {
                        switch (updatedResponse){
                            case 1:
                                Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(getActivity(), "middle name updated", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }
}
