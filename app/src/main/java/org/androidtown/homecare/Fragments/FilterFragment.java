package org.androidtown.homecare.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import org.androidtown.homecare.R;

/**
 * Created by hanhb on 2017-11-09.
 */

public class FilterFragment extends DialogFragment {

    private Switch filterSwitch;


    public FilterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container);
        
        initView(view);


        return view;
    }

    private void initView(View view) {
        view.findViewById(R.id.cancel_button_in_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 필터 스위치 값에 따라 필터링
                if(filterSwitch.isChecked()){




                } else {



                }

                dismiss();
            }
        });

        filterSwitch = view.findViewById(R.id.filter_switch);
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    filterSwitch.setText("온");



                } else {
                    filterSwitch.setText("전체 보기");



                }

            }
        });
    }
}
