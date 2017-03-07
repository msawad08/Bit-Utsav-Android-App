package com.bit.utsav;


import android.content.Context;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment {
    FrameLayout layout;
    List<String> elist;


    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout= (FrameLayout) inflater.inflate(R.layout.fragment_event, container, false);
        elist = Arrays.asList(getResources().getStringArray(R.array.event_name));
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
        //recyclerView.setHasFixedSize(true);
        EventAdapter adapter = new EventAdapter(getActivity(),elist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return layout;
    }

    public  void showPopup(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View layout=inflater.inflate(R.layout.popup, null);
        TextView tv = (TextView) layout.findViewById(R.id.title);
        tv.setText(elist.get(position));
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        tv = (TextView) layout.findViewById(R.id.date);
        if(position==0) tv.setText("28/03/16 "+Events.TIME[position]);
        else if(position<12 || position>26) tv.setText("29/03/16 "+Events.TIME[position]);
        else tv.setText("30/03/16 "+Events.TIME[position]);
        tv = (TextView) layout.findViewById(R.id.venue);
        tv.setText(Events.VENUE[position]);
        tv = (TextView) layout.findViewById(R.id.dept);
        tv.setText(Events.DEPT[position]);
        tv = (TextView) layout.findViewById(R.id.fac);
        tv.setText(Events.FAC[position]);
        tv = (TextView) layout.findViewById(R.id.stud);
        tv.setText(Events.stud[position]);
        tv = (TextView) layout.findViewById(R.id.rules);
        tv.setText(getRules(position-1));
        builder.setView(layout);

        builder.create();
        builder.show();

    }

    private String getRules(int position) {
        switch(position)
        {
            case -1:return Rules.cook;
            case 0:return Rules.selfie;
            case 2:return Rules.mono;
            case 3:return Rules.quiz;
            case 6:return Rules.face;
            case 7:return Rules.madads;
            case 8:return Rules.pencil;
            case 9:return Rules.minimilitia;
            case 10:return Rules.dance;
            case 11:return Rules.mehendi;
            case 12:return Rules.movie;
            case 13:return Rules.mime;
            case 14:return Rules.cs;
            case 15:return Rules.collage;
            case 16:return Rules.debate;
            case 17:return Rules.painting;
            case 18:return Rules.dumbchardes;
            case 19:return Rules.treasur;
            case 21:return Rules.clay;
            case 22:return Rules.skit;
            case 23:return Rules.picknspeak;
            case 24:return Rules.singing;
            case 25:return Rules.photo;
            case 27:return Rules.selfieVideo;
            default:return "";
        }
    }

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
        private List<String> list;
        private Context mContext;

        public EventAdapter(Context context,List<String> list) {
            this.list = list;
            mContext = context;

        }

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.event_row, null);

            EventViewHolder  viewHolder = new EventViewHolder (view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(EventAdapter.EventViewHolder holder, final int position) {
            holder.textView.setText(list.get(position));
            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    showPopup(position);



                }
            });

        }




        @Override
        public int getItemCount() {
            return list.size();
        }

        public class EventViewHolder extends RecyclerView.ViewHolder {
            protected TextView textView;
            public EventViewHolder(View view) {
                super(view);
                this.textView = (TextView) view.findViewById(R.id.title);
            }

        }


    }


}
