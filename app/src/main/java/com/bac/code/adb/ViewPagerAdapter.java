package com.bac.code.adb;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bac.code.fragments.AddProductFragment;
import com.bac.code.fragments.HomeFragment;
import com.bac.code.fragments.SoldProductsFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new HomeFragment();
        }
        else if (position == 1)
        {
            fragment = new SoldProductsFragment();
        }
        else if (position == 2)
        {
            fragment = new AddProductFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "All";
        }
        else if (position == 1)
        {
            title = "Sold";
        }
        else if (position == 2)
        {
            title = "New";
        }
        return title;
    }
}
