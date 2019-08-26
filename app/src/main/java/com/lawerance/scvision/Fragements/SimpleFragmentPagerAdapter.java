package com.lawerance.scvision.Fragements;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    public SimpleFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PageOne();
        } else if (position == 1){
            return new PageTwo();
        } else {
            return new PageTree();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
