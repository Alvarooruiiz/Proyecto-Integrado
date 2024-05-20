package com.example.proyectoalfari.Menu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MenuPagerAdapter  extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> fragmentTitleList;

    public MenuPagerAdapter(@NonNull FragmentManager fm, int behavior,
                            List<Fragment> fragmentList, List<String> fragmentTitleList) {
        super(fm, behavior);
        this.fragmentList = fragmentList;
        this.fragmentTitleList = fragmentTitleList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
