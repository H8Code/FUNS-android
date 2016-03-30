package org.h8code.funs.funs.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import org.h8code.funs.funs.ui.DaysPagerAdapter;
import org.h8code.funs.funs.R;


public class ScheduleFragment extends Fragment {
    private static final String TAG = "SCHEDULE_FRAGMENT";
    private ViewPager daysPager;

    public ScheduleFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.schedule_fragment, null);
        Log.d(TAG, "onCreateView");

        DaysPagerAdapter daysPagerAdapter = new DaysPagerAdapter();
        daysPager = (ViewPager) v.findViewById(R.id.days_pager);
        daysPager.setAdapter(daysPagerAdapter);

        RecyclerTabLayout daysView = (RecyclerTabLayout) v.findViewById(R.id.recycler_tab_layout);
        daysView.setUpWithViewPager(daysPager);
        daysView.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
