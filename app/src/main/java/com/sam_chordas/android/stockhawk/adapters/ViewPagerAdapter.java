package com.sam_chordas.android.stockhawk.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sam_chordas.android.stockhawk.ui.fragments.ChartFragment;
import com.sam_chordas.android.stockhawk.ui.fragments.DetailFragment;
import com.sam_chordas.android.stockhawk.ui.fragments.NewsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sureshkumar on 08/11/16.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments = new ArrayList<>();
    private final List<String> mFragmentTitles = new ArrayList<>();
    private Bundle mExtras;
    public ViewPagerAdapter(FragmentManager fm, Bundle extras) {
        super(fm);
        this. mExtras = extras;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.setArguments(mExtras);
                return detailFragment;
            case 1:
                ChartFragment chartFragment = new ChartFragment();
                chartFragment.setArguments(mExtras);
                return chartFragment;
            case 2:
                NewsFragment newsFragment = new NewsFragment();
                newsFragment.setArguments(mExtras);
                return newsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragments.add(fragment);
        mFragmentTitles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitles.get(position);
    }
}
