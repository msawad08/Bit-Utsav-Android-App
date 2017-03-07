package com.bit.utsav;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    View view;


    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_schedule1, container, false);
        view.post(new Runnable() {
            @Override
            public void run() {
                addView();
            }
        });
        return view;
    }
    private void addView(){
        String[] events=getResources().getStringArray(R.array.event_name);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        LinearLayout holder = (LinearLayout) view.findViewById(R.id.holder);
        for(int i=12;i<events.length-2;i++)
        {
            View row=inflater.inflate(R.layout.schedule_row,null);
            TextView tv = (TextView) row.findViewById(R.id.time);
            tv.setText(Events.TIME[i]);
            tv = (TextView) row.findViewById(R.id.event);
            tv.setText(events[i]);
            holder.addView(row);
        }

    }

}