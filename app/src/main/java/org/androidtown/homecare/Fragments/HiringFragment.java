package org.androidtown.homecare.Fragments;


import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HiringFragment extends Fragment {
    RecyclerView recyclerView;
    ImageView profileImageView;
    TextView profileNameText;
    Button addOrCheckHomeCareButton, filterButton;

    public HiringFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_hiring, container, false);
        initViews(view);
        initButtons(view);
        //TODO 리프레쉬 버튼 만들기

        return view;
    }

    private void initViews(View view) {

        recyclerView = view.findViewById(R.id.recycler_view_in_hiring);
        ((MainActivity)getActivity()).firebaseHomeCare.setHomeCareRecyclerView(recyclerView);
        ((MainActivity)getActivity()).firebaseHomeCare.refreshHomeCare();

        profileImageView = view.findViewById(R.id.profile_image_view_in_hiring_fragment);
        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        profileNameText = view.findViewById(R.id.name_text_view_in_hiring_fragment);

    }

    private void initButtons(View view) {

        //TODO 이미 추가되어있으면 체크 버튼, 추가되어있지 않으면 추가 버튼으로 전환하기
        //카드 추가, 또는 체크 버튼
        addOrCheckHomeCareButton = view.findViewById(R.id.home_care_add_or_check_button);
        addOrCheckHomeCareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            HomeCareCreationFragment homeCareCreationFragment = new HomeCareCreationFragment();
            homeCareCreationFragment.setCancelable(false);
            homeCareCreationFragment.show(HiringFragment.this.getActivity().getFragmentManager(), "");
            }
        });

        //필터링 버튼
        filterButton = view.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterDialogFragment filterDialogFragment = new FilterDialogFragment();
                filterDialogFragment.setCancelable(false);
                filterDialogFragment.show(HiringFragment.this.getActivity().getFragmentManager(), "");
            }
        });

    }


}
