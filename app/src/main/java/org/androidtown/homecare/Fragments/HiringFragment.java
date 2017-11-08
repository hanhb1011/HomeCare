package org.androidtown.homecare.Fragments;


import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.homecare.Activities.ContentAdditionActivity;
import org.androidtown.homecare.Activities.MainActivity;
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
    ImageView profileImageView;
    TextView profileNameText;
    Button addOrCheckContentButton;

    public HiringFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_hiring, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_in_hiring);

        profileImageView = view.findViewById(R.id.profile_image_view_in_hiring_fragment);
        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        profileNameText = view.findViewById(R.id.name_text_view_in_hiring_fragment);
        //이미 추가되어있으면 체크 버튼, 추가되어있지 않으면 추가 버튼으로 전환하기
        addOrCheckContentButton = view.findViewById(R.id.content_add_or_check_button);
        addOrCheckContentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //임시
                Intent intent = new Intent(HiringFragment.this.getContext(), ContentAdditionActivity.class);
                startActivityForResult(intent, MainActivity.CONTENT_ADDITION_REQUEST);
            }
        });

        //임시로 리스트 아이템 추가
        List<Content> list = new ArrayList<>();
        list.add(new Content()); //필수
        list.add(new Content());
        list.add(new Content());
        list.add(new Content());
        list.add(new Content());
        list.add(new Content());
        recyclerView.setLayoutManager(new LinearLayoutManager(HiringFragment.this.getContext()));
        recyclerView.setAdapter(new ContentAdapter(list, getContext()));



        return view;
    }



}
