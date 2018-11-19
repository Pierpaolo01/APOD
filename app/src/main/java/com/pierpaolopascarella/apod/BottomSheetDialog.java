package com.pierpaolopascarella.apod;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BottomSheetDialog extends BottomSheetDialogFragment implements StartFragment.StartFragmentListener, EndFragment.EndFragmentListener{

    //private BottomSheetListener mListener;
    private View view;
    private FragmentActivity mContext;
    private ViewPager viewPager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        TabLayout tab = view.findViewById(R.id.tablayout123);
        tab.addTab(tab.newTab().setText("Start Date"));
        tab.addTab(tab.newTab().setText("End Date"));
        tab.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = view.findViewById(R.id.viewpager123);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager(), tab.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return view;
    }



    @Override
    public void onAttach(Context context) {
        mContext = (FragmentActivity) context;
        super.onAttach(context);



    }



    @Override
    public void onStartFragmentListener(CharSequence date1) {

        int tabPosition = 0;
        viewPager.setCurrentItem(tabPosition, true);

    }

    @Override
    public void onEndFragmentListener(CharSequence input) {

    }
}
