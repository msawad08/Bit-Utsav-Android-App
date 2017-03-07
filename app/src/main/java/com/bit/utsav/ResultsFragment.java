package com.bit.utsav;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {
    FrameLayout layout;
    RecyclerView recyclerView;
    List<ParseObject> newslist;
    PullRefreshLayout refresLayout;


    public ResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = (FrameLayout) inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview);
        refresLayout = (PullRefreshLayout) layout.findViewById(R.id.swipeRefreshLayout);
        newslist=new ArrayList<>();
        setAdapter();
// listen refresh event

        refresLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(true);
            }
        });


        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                getLocalData();
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(true);
            }
        },1000);

        return layout;
    }

    public void setAdapter() {
        recyclerView.setHasFixedSize(true);
        EventAdapter adapter = new EventAdapter(getActivity(),newslist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    public void getData(final boolean fl){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("result");
        query.addDescendingOrder("updatedAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    newslist = list;
                    for(ParseObject item:newslist) {
                        item.pinInBackground();
                    }
                    if(!fl)
                        setAdapter();
                    else
                        setAdapter();

                } else {
                    Snackbar.make(recyclerView,"Error Connecting to the netwok",Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getData(fl);
                                }
                            })
                            .show();
                }
                refresLayout.setRefreshing(false);
            }
        });




    }

    private void getLocalData() {
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("result");
        query1.addDescendingOrder("updatedAt");
        query1.fromLocalDatastore();
        query1.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null ) {
                    if (list != null){
                        if(list.size()!=0){
                            newslist = list;
                            setAdapter();
                            getData(true);
                        }}else {

                        getData(true);

                    }
                } else {
                    Log.d("score", "Error: " + e.getMessage());
                    getData(true);
                }
            }
        });
    }

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
        private List<ParseObject> list;
        private Context mContext;

        public EventAdapter(Context context,List<ParseObject> list) {
            this.list = list;
            mContext = context;

        }

        @Override
        public EventViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.result_row, null);

            EventViewHolder  viewHolder = new EventViewHolder (view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(EventAdapter.EventViewHolder holder, final int position) {
            holder.textView.setText(list.get(position).getString("text"));


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
