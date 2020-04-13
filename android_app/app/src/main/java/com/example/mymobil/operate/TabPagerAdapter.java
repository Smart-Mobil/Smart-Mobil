package com.example.mymobil.operate;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mymobil.operate.moodlight.Tab1Fragment;
import com.example.mymobil.operate.music.Tab2Fragment;
import com.example.mymobil.operate.servomotor.Tab3Fragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Count number of tabs
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {

        // Returning the current tabs
        switch (position) {
            case 0:
                Tab1Fragment Fragment1 = new Tab1Fragment();
                return Fragment1 ;
            case 1:
                Tab2Fragment Fragement2 = new Tab2Fragment();
                return Fragement2 ;
            case 2:
                Tab3Fragment Fragment3 = new Tab3Fragment();
                return Fragment3 ;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}