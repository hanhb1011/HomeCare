package org.androidtown.homecare.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HiringFragment extends Fragment {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HiringFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_hiring, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_in_hiring);
        ((MainActivity)getActivity()).getFirebaseHomeCare().setHomeCareRecyclerView(recyclerView);
        ((MainActivity)getActivity()).getFirebaseHomeCare().refreshHomeCare(null);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).refresh(false, swipeRefreshLayout);
            }
        });

        return view;
    }

}
