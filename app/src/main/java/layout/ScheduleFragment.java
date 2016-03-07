package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nshmura.recyclertablayout.RecyclerTabLayout;

import org.h8code.funs.funs.DaysPagerAdapter;
import org.h8code.funs.funs.R;


public class ScheduleFragment extends Fragment {
    private static final String TAG = "SCHEDULE_FRAGMENT";
    private OnFragmentInteractionListener mListener;
    private ViewPager daysPager;

    public ScheduleFragment() {}

    public static ScheduleFragment newInstance(String param1, String param2) {
        ScheduleFragment fragment = new ScheduleFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
