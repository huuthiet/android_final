package com.example.myfinal.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myfinal.fragments.HomeFragment;
import com.example.myfinal.fragments.NotificationFragment;
import com.example.myfinal.fragments.ProfileFragment;
import com.example.myfinal.fragments.RoomFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2AdapterMain extends FragmentStateAdapter {
    public ViewPager2AdapterMain(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new RoomFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new NotificationFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
