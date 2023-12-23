package com.example.back_benchers.Adpters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.back_benchers._Student_Profile_Fragments.Personal_Fragment;
import com.example.back_benchers._Student_Profile_Fragments.Educational_Fragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    // Constructor to initialize the adapter with a FragmentActivity
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    // Create the appropriate Fragment based on the position
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Personal_Fragment(); // Return HomeFragment for position 0
            case 1:
                return new Educational_Fragment(); // Return SettingsFragment for position 1
            default:
                return new Personal_Fragment(); // Return HomeFragment by default
        }
    }

    // Return the total number of Fragments in the ViewPager
    @Override
    public int getItemCount() {
        return 2; // Two Fragments: HomeFragment and SettingsFragment
    }
}
