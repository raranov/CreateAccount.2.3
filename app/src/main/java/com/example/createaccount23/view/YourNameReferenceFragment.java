package com.example.createaccount23.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.createaccount23.databinding.FragmentYournameReferenceBinding;
import com.example.createaccount23.utils.SharedPreferencesUtils;
import com.example.createaccount23.viewmodel.YourName.YourNameReferenceVM;

public class YourNameReferenceFragment extends Fragment {
    private YourNameReferenceVM viewmodel = new YourNameReferenceVM();

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final FragmentYournameReferenceBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_yourname_reference, container, false);
        View view = binding.getRoot();
        viewmodel.init();

        String userName = SharedPreferencesUtils.getSharedPreferences(
                SharedPreferencesUtils.SharedPreferenceKeys.REFERENCE_NAME.toString(), getContext());
        String text = "What name should we refer to you by?";
        binding.txtReferencePrompt.setText(text);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        viewmodel.setUserID(sharedPreferences.getString("USER_ID", ""));

        // Back Button
        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        // Skip Button
        /*binding.btnSkip.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfilePictureIntro.class);
                startActivity(intent);
            }
        });*/


        // Keyboard next button
        binding.edtReference.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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

                /*SharedPreferencesUtils.setSharedPreferencesRefName(
                        binding.edtReference.getText().toString(),
                        sharedPreferences, editor, getContext());*/


                viewmodel.validateReferenceNameInput(binding.edtReference.getText().toString())
                        .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer response) {
                                switch (response){
                                    case 1:
                                        Toast.makeText(getContext(), "please answer or skip", Toast.LENGTH_SHORT).show();
                                        break;
                                    case 0:
                                        /*Intent intent = new Intent(getActivity(), ProfilePictureIntro.class);
                                        startActivity(intent);*/
                                        break;
                                }

                                if(response == 0){
                                    viewmodel.updateReferenceName(binding.edtReference.getText().toString())
                                            .observe(getViewLifecycleOwner(), new Observer<Integer>() {
                                                @Override
                                                public void onChanged(Integer updatedResponse) {
                                                    switch (updatedResponse){
                                                        case 1:
                                                            Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                                                            break;
                                                        case 0:
                                                            Toast.makeText(getActivity(), "nickname updated", Toast.LENGTH_SHORT).show();

                                                            break;
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
