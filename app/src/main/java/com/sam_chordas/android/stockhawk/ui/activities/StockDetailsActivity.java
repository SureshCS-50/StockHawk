package com.sam_chordas.android.stockhawk.ui.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.adapters.ViewPagerAdapter;
import com.sam_chordas.android.stockhawk.ui.fragments.ChartFragment;
import com.sam_chordas.android.stockhawk.ui.fragments.DetailFragment;
import com.sam_chordas.android.stockhawk.ui.fragments.NewsFragment;
import com.sam_chordas.android.stockhawk.utils.Constants;

public class StockDetailsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Bundle extras;

        if (getIntent().getExtras() != null) {
            String symbol = getIntent().getStringExtra(Constants.KEY_SYMBOL);

            extras = new Bundle();
            extras.putString(getString(R.string.key_symbol), symbol);

            getSupportActionBar().setTitle(symbol.toUpperCase());

            mViewPager = (ViewPager) findViewById(R.id.viewpager);
            mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), extras);
            mAdapter.addFragment(new DetailFragment(), getString(R.string.str_detail_fragment));
            mAdapter.addFragment(new ChartFragment(), getString(R.string.str_chart_fragment));
            mAdapter.addFragment(new NewsFragment(), getString(R.string.str_news_fragment));
            mViewPager.setAdapter(mAdapter);

            mTabLayout = (TabLayout) findViewById(R.id.tabs);
            mTabLayout.setupWithViewPager(mViewPager);
        }

    }
}