package com.sm.smmap.smmap.lte_nr;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {
    String [] title={"卡槽1","卡槽2"};
    private ArrayList<Fragment> list;


    public MyPagerAdapter(FragmentManager fm,ArrayList<Fragment> a) {
        super(fm);
        this.list=a;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        //每次得到一个新的下标就会创建新的Fragment
        return list.get(position);
    }

    @Override
    public int getCount() {
        //定义的集合大小
        return list.size();
    }
//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        //设置Tablayout的标题
//        return title[position];
//    }
}