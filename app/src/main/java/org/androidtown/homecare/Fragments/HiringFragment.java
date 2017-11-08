package org.androidtown.homecare.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.homecare.Adapters.ContentAdapter;
import org.androidtown.homecare.Models.Content;
import org.androidtown.homecare.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HiringFragment extends Fragment {
    RecyclerView recyclerView;

    public HiringFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_hiring, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_in_hiring);

        //임시로 리스트 아이템 추가
        List<Content> list = new ArrayList<>();
        list.add(null); //필수
        list.add(new Content("first"));
        list.add(new Content("second"));
        list.add(new Content("third"));
        list.add(new Content("fourth"));
        list.add(new Content("fifth"));
        recyclerView.setLayoutManager(new LinearLayoutManager(HiringFragment.this.getContext()));
        recyclerView.setAdapter(new ContentAdapter(list, getContext()));




        return view;
    }



}
