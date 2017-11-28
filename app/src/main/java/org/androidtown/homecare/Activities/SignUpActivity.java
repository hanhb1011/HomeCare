package org.androidtown.homecare.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private User user;

    private EditText NameEdit, GenderEdit, PhoneEdit, emailEdit, passwordEdit;
    private TextView BirthdayText;
    private Spinner locationSpinner, personalitiesSpinner;
    private Button submitButton;
    private Calendar cal;
    private int year, day, month;

    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = new User();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initView();
        initSpinners();
        initEditTexts();
        initDatePickersAndViews();
        initButtons();


        user.setName(NameEdit.getText().toString());
//        user.setName(GenderEdit.getText().toString());
        user.setName(PhoneEdit.getText().toString());
        //user.setName(locationSpinner.getItemAtPosition(position).toString());


        //user.setName(,,,,);
    }

    private void initView() {

    }

    private void initButtons() {
        findViewById(R.id.submit_button_in_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });
    }

    private void initDatePickersAndViews() {
        BirthdayText = findViewById(R.id.birthday_text_view_in_activity_sign_up);
        submitButton = findViewById(R.id.date_pick_button_in_sign_up);


        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        cal.set(year, month, day);
        BirthdayText.setText(fmt.format(cal.getTime()));



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUpActivity.this, android.app.AlertDialog.THEME_HOLO_LIGHT,startDateSetListener, year, month, day).show();
            }
        });

    }

    private void initEditTexts() {
        NameEdit = findViewById(R.id.name_text_view_in_activity_sign_up);
//        GenderEdit = findViewById(R.id.gender_text_view_in_activity_sign_up);
        PhoneEdit = findViewById(R.id.phone_text_view_in_activity_sign_up);
    }

    private void initSpinners() {
        locationSpinner = findViewById(R.id.location_spinner_in_sign_up);
        personalitiesSpinner = findViewById(R.id.personalities_spinner_in_sign_up);

        locationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.setLocation(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        personalitiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.setPersonality(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            BirthdayText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            cal.set(year, monthOfYear, dayOfMonth);
            user.setBirthday(cal.getTimeInMillis());
        }


    };
}