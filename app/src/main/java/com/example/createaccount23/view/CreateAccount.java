package com.example.createaccount23.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.createaccount23.R;
import com.example.createaccount23.databinding.ActivityCreateAccountBinding;
import com.example.createaccount23.model.User;
import com.example.createaccount23.utils.FragmentAdapter;
import com.example.createaccount23.utils.NonSwipeableViewPager;
import com.example.createaccount23.viewmodel.CreateAccountVM;

import java.util.Stack;

//todo hamburger menu (change from spinner), use large area hamburger png
public class CreateAccount extends AppCompatActivity {
    private NonSwipeableViewPager viewPager;
    User newUser = new User();
    Stack<Integer> pageHistory;
    int currentPage;
    boolean saveToHistory;
    CreateAccountVM viewmodel = new CreateAccountVM();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityCreateAccountBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_create_account);
        viewPager = binding.vpCreateAccount;
        setupViewPager(binding.vpCreateAccount);

        pageHistory = new Stack<>();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                currentPage = viewPager.getCurrentItem() - 1;

                if(saveToHistory)
                    pageHistory.push(currentPage);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        saveToHistory = true;
    }

    private void setupViewPager(ViewPager viewPager){
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreateAccount1Fragment(), "CreateAccountName");
        adapter.addFragment(new CreateAccount2Fragment(), "CreateAccountEmail");
        adapter.addFragment(new CreateAccount3Fragment(), "CreateAccountPassword");
        viewPager.setAdapter(adapter);
    }

    public void scrollToFragment(int fragmentNumber){
        if ((viewPager.getCurrentItem()) < viewPager.getAdapter().getCount()) {
            viewPager.setCurrentItem(fragmentNumber, true);
        }
        else {
            // TODO: WHAT TO DO THERE??
        }
    }

    @Override
    public void onBackPressed() {
        if(pageHistory.empty())
            super.onBackPressed();
        else {
            saveToHistory = false;
            int back = pageHistory.pop();
            viewPager.setCurrentItem(back);
            saveToHistory = true;
        }

}

}
