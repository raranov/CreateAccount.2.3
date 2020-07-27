package com.itismyexperience.app.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.itismyexperience.app.R;
import com.itismyexperience.app.databinding.ActivityYourNameBinding;
import com.itismyexperience.app.utils.FragmentAdapter;
import com.itismyexperience.app.utils.NonSwipeableViewPager;

import java.util.Stack;

//todo hamburger, back, skip, continue
public class YourName extends AppCompatActivity {
    private NonSwipeableViewPager viewPager;
    Stack<Integer> pageHistory;
    int currentPage;
    boolean saveToHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityYourNameBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_your_name);
        viewPager = binding.vpYourName;
        setupViewPager(binding.vpYourName);

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

    private void setupViewPager(NonSwipeableViewPager viewPager){
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new YourNameMiddleFragment(), "YourNameMiddleFragment");
        adapter.addFragment(new YourNameNicknameFragment(), "YourNameNicknameFragment");
        adapter.addFragment(new YourNameReferenceFragment(), "YourNameReferenceFragment");
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
