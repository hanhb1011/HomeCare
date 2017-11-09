package org.androidtown.homecare.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.homecare.R;

/**
 * Created by hanhb on 2017-11-09.
 */

public class HomeCareCreationFragment extends DialogFragment {

    public HomeCareCreationFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_care_creation, container);
        view.findViewById(R.id.back_button_in_activity_home_care_addition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }

}

