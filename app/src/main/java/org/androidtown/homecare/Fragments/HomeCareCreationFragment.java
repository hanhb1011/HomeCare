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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hanhb on 2017-11-09.
 */

public class HomeCareCreationFragment extends DialogFragment {

    private EditText payEdit, commentEdit, titleEdit;
    private TextView startPeriodText, endPeriodText;
    private Spinner locationSpinner, typeSpinner;
    private Button startDatePickButton, endDatePickButton;

    private Calendar cal;
    private int year, day, month;
    private HomeCare homeCare;

    public HomeCareCreationFragment() { }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_care_creation, container);

        homeCare = new HomeCare();

        initButtons(view);
        initSpinners(view);
        initEditTexts(view);
        initDatePickersAndViews(view);

        return view;
    }



    private void initButtons(View view) {

        //등록
        view.findViewById(R.id.submit_button_in_home_care_addition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //invalid한 input일 경우 다이얼로그 띄우고 리턴
                if(payEdit.getText().toString().length()==0 || payEdit.getText().toString().equals("0")){
                    MessageDialogFragment.showDialog(MessageDialogFragment.PAY_INVALID, HomeCareCreationFragment.this.getActivity());
                    return;
                }
                if(commentEdit.getText().toString().length()==0){
                    MessageDialogFragment.showDialog(MessageDialogFragment.COMMENT_INVALID, HomeCareCreationFragment.this.getActivity());
                    return;
                }
                if(titleEdit.getText().toString().length()==0){
                    MessageDialogFragment.showDialog(MessageDialogFragment.COMMENT_INVALID, HomeCareCreationFragment.this.getActivity());
                    return;
                }

                homeCare.setComment(commentEdit.getText().toString());
                homeCare.setPay(Integer.valueOf(payEdit.getText().toString()));
                homeCare.setUid((MainActivity.uid));
                homeCare.setTimestamp();
                homeCare.setTitle(titleEdit.getText().toString().trim());

                ((MainActivity)getActivity()).firebaseHomeCare.writeHomeCare(MainActivity.uid, homeCare, HomeCareCreationFragment.this);

            }
        });

        //취소
        view.findViewById(R.id.back_button_in_activity_home_care_addition).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialogFragment.setHomeCareCreationFragment(HomeCareCreationFragment.this);
                MessageDialogFragment.showDialog(MessageDialogFragment.CANCEL_ASKING, HomeCareCreationFragment.this.getActivity());

            }
        });

    }


    private void initDatePickersAndViews(View view) {
        startPeriodText = view.findViewById(R.id.start_date_in_creation);
        endPeriodText = view.findViewById(R.id.end_date_in_creation);
        startDatePickButton = view.findViewById(R.id.start_date_pick_button_in_creation);
        endDatePickButton = view.findViewById(R.id.end_date_pick_button_in_creation);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day= cal.get(Calendar.DAY_OF_MONTH);

        cal.set(year, month, day);
        startPeriodText.setText(fmt.format(cal.getTime()));
        endPeriodText.setText(fmt.format(cal.getTime()));

        homeCare.setStartPeriod(cal.getTimeInMillis());
        homeCare.setEndPeriod(cal.getTimeInMillis());

        startDatePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HomeCareCreationFragment.this.getActivity(), startDateSetListener, year, month, day).show();
            }
        });

        endDatePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(HomeCareCreationFragment.this.getActivity(), endDateSetListener, year, month, day).show();
            }
        });

    }

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            startPeriodText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            cal.set(year, monthOfYear, dayOfMonth);
            homeCare.setStartPeriod(cal.getTimeInMillis());
        }
    };
    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            endPeriodText.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
            cal.set(year, monthOfYear, dayOfMonth);
            homeCare.setEndPeriod(cal.getTimeInMillis());
        }
    };


    private void initEditTexts(View view) {
        titleEdit = view.findViewById(R.id.title_edit_text_in_creation);
        payEdit = view.findViewById(R.id.pay_edit_text_in_creation);
        commentEdit = view.findViewById(R.id.comment_edit_text_in_creation);
    }

    private void initSpinners(View view) {
        locationSpinner = view.findViewById(R.id.loaction_spinner_in_creation);
        typeSpinner = view.findViewById(R.id.type_spinner_in_creation);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homeCare.setLocation(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                homeCare.setCareType(parent.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

