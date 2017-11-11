package org.androidtown.homecare.Fragments;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Firebase.FirebaseHomeCare;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;
import org.androidtown.homecare.Utils.SharedPreferenceHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hanhb on 2017-11-09.
 */

public class FilterFragment extends DialogFragment {

    private Switch filterSwitch;
    private LinearLayout hiddenLayout;
    private Spinner locationSpinner, typeSpinner;
    private EditText minPayEdit, maxPayEdit;
    private Button startDatePickButton, endDatePickButton;
    private TextView startPeriodText, endPeriodText;
    private Calendar cal;
    private int year, day, month;

    //Filter Args
    private long startPeriod, endPeriod;
    private int minPay, maxPay;
    private String careType, location;


    public FilterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_dialog, container);

        initView(view);
        initApplyButton(view);
        initDatePickersAndViews(view);
        initSpinners(view);
        initFilter();



        return view;
    }

    private void initSpinners(View view) {

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                careType = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initDatePickersAndViews(View view) {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        cal = Calendar.getInstance();

        if(SharedPreferenceHelper.getBoolean(getActivity(), "filter")) {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);

            startPeriod =  SharedPreferenceHelper.getLong(getActivity(), "startPeriod");
            endPeriod = SharedPreferenceHelper.getLong(getActivity(), "endPeriod");
            cal.setTimeInMillis(startPeriod);
            startPeriodText.setText(fmt.format(cal.getTime()));
            cal.setTimeInMillis(endPeriod);
            endPeriodText.setText(fmt.format(cal.getTime()));

        } else {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);

            cal.set(year, month, day);
            startPeriod = cal.getTimeInMillis();
            startPeriodText.setText(fmt.format(cal.getTime()));
            cal.setTimeInMillis(cal.getTimeInMillis() + 86400000L*30);
            endPeriodText.setText(fmt.format(cal.getTime()));
            endPeriod = cal.getTimeInMillis();

        }


        startDatePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FilterFragment.this.getActivity(), startDateSetListener, year, month, day).show();
            }
        });

        endDatePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(FilterFragment.this.getActivity(), endDateSetListener, year, month, day).show();
            }
        });

    }

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startPeriodText.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            cal.set(year, monthOfYear, dayOfMonth);
            startPeriod = cal.getTimeInMillis();
        }
    };
    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endPeriodText.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            cal.set(year, monthOfYear, dayOfMonth);
            endPeriod = cal.getTimeInMillis();
        }
    };

    private void initApplyButton(View view) {
        view.findViewById(R.id.cancel_button_in_filter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(filterSwitch.isChecked()){


                    //원본 홈케어 리스트를 불러온다.
                    List<HomeCare> homeCareList = FirebaseHomeCare.getHomeCareList();
                    List<User> userList = FirebaseHomeCare.getUserList();
                    List<HomeCare> filteredHomeCareList = FirebaseHomeCare.getFilteredHomeCareList();
                    List<User> filteredUserList = FirebaseHomeCare.getFilteredUserList();
                    if(homeCareList == null) {
                        Toast.makeText(getActivity(), "다시 시도해 주십시오", Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                    minPay = Integer.valueOf(minPayEdit.getText().toString());
                    maxPay = Integer.valueOf(maxPayEdit.getText().toString());

                    //init
                    filteredHomeCareList.clear();
                    filteredUserList.clear();

                    //필터링
                    Iterator<HomeCare> it = homeCareList.iterator();
                    while(it.hasNext()){
                        HomeCare homeCare = it.next();
                        boolean filtering = true;

                        if( homeCare.getPay() < minPay || maxPay < homeCare.getPay()) {
                            filtering = false;
                        }
                        if(!location.equals(homeCare.getLocation())){
                            filtering = false;
                        }
                        if(!careType.equals(homeCare.getCareType())){
                            filtering = false;
                        }
                        if((endPeriod + 43200000l) < homeCare.getStartPeriod() || homeCare.getEndPeriod() < (startPeriod-43200000)) {
                            filtering = false;
                        }

                        if(filtering){
                            filteredHomeCareList.add(homeCare);

                            Iterator<User> userIt = userList.iterator();
                            while (userIt.hasNext()){
                                User user = userIt.next();

                                if(homeCare.getUid().equals(user.getUid())){
                                    filteredUserList.add(user);
                                }
                            }
                        }
                    }

                    //결과값으로 필터링
                    ((MainActivity)getActivity()).getFirebaseHomeCare().filter();
                    saveFilterArgs(); //필터 값 저장
                } else {
                    ((MainActivity)getActivity()).getFirebaseHomeCare().refreshHomeCare();

                }

                dismiss();
            }
        });
    }

    private void saveFilterArgs() {
        /*
            long startPeriod, endPeriod;
            int minPay, maxPay;
            String careType, location;
        */

        SharedPreferenceHelper.putLong(getActivity(), "startPeriod", startPeriod);
        SharedPreferenceHelper.putLong(getActivity(), "endPeriod", endPeriod);
        SharedPreferenceHelper.putInt(getActivity(), "minPay", minPay);
        SharedPreferenceHelper.putInt(getActivity(), "maxPay", maxPay);


    }

    private void initFilter() {
        filterSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    hiddenLayout.setVisibility(View.VISIBLE);
                    SharedPreferenceHelper.putBoolean(getActivity(), "filter", true);

                } else {
                    hiddenLayout.setVisibility(View.GONE);
                    SharedPreferenceHelper.putBoolean(getActivity(), "filter", false);
                }

            }
        });
    }

    private void initView(View view) {
        hiddenLayout = view.findViewById(R.id.filter_layout);
        locationSpinner = view.findViewById(R.id.loaction_spinner_in_filter);

        typeSpinner = view.findViewById(R.id.type_spinner_in_filter);
        minPayEdit = view.findViewById(R.id.min_pay_edit_in_filter);
        maxPayEdit = view.findViewById(R.id.max_pay_edit_in_filter);
        filterSwitch = view.findViewById(R.id.filter_switch);
        startPeriodText = view.findViewById(R.id.start_date_in_filter);
        endPeriodText = view.findViewById(R.id.end_date_in_filter);
        startDatePickButton = view.findViewById(R.id.start_date_pick_button_in_filter);
        endDatePickButton = view.findViewById(R.id.end_date_pick_button_in_filter);

        if(SharedPreferenceHelper.getBoolean(getActivity(), "filter")){
            filterSwitch.setChecked(true);
            hiddenLayout.setVisibility(View.VISIBLE);
            minPayEdit.setText(String.valueOf(SharedPreferenceHelper.getInt(getActivity(), "minPay")));
            maxPayEdit.setText(String.valueOf(SharedPreferenceHelper.getInt(getActivity(), "maxPay")));

        } else {
            //기본값 지정
            hiddenLayout.setVisibility(View.GONE);
            minPayEdit.setText(String.valueOf(0));
            maxPayEdit.setText(String.valueOf(0));
        }

    }
}
