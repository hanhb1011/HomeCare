package org.androidtown.homecare.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.androidtown.homecare.Firebase.FirebaseAccount;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private User user;

    private EditText nameEdit, phoneEdit, emailEdit, passwordEdit;
    private TextView birthdayText;
    private Spinner locationSpinner, personalitiesSpinner;
    private Button submitButton, picturePickButton;
    private ImageView profileImage;
    private Calendar cal;
    private Bitmap bitmap;
    private FirebaseAccount firebaseAccount;

    private int year, day, month;

    public SignUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        user = new User();
        firebaseAccount = new FirebaseAccount(this);
        initView();
        initSpinners();
        initDatePickersAndViews();
        initButtons();

    }

    private void initView() {
        emailEdit = findViewById(R.id.email_text_view_in_activity_sign_up);
        passwordEdit = findViewById(R.id.password_text_view_in_activity_sign_up);
        nameEdit = findViewById(R.id.name_text_view_in_activity_sign_up);
        phoneEdit = findViewById(R.id.phone_text_view_in_activity_sign_up);

        profileImage = findViewById(R.id.profile_image_view_in_activity_sign_up);
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(Color.TRANSPARENT);
        profileImage.setBackground(shapeDrawable);
        profileImage.setClipToOutline(true);
        profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

    }

    private void initButtons() {
        picturePickButton = findViewById(R.id.picture_pick_button);
        picturePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });

        findViewById(R.id.submit_button_in_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEdit.getText().toString().trim();
                String password = passwordEdit.getText().toString().trim();
                String name = nameEdit.getText().toString().trim();
                String phone = phoneEdit.getText().toString().trim();

                StringBuilder sb = new StringBuilder();
                boolean valid = true;

                if(!FirebaseAccount.checkEmailValid(email)){
                    sb.append("이메일을 정확히 입력해 주십시오.\n");
                    valid = false;
                }
                if(!FirebaseAccount.checkPasswordValid(password)){
                    sb.append("패스워드를 정확히 입력해 주십시오.\n패스워드는 6자 이상입니다.\n");
                    valid = false;
                }
                if(name.length()==0){
                    sb.append("이름을 입력해 주십시오.\n");
                    valid = false;
                }
                if(phone.length()==0){
                    sb.append("연락처를 입력해 주십시오.\n");
                    valid = false;
                }

                if(!valid){
                    MessageDialogFragment.setContentOfDialog(sb.toString());
                    MessageDialogFragment.showDialog(MessageDialogFragment.SIGN_UP_INVALID, SignUpActivity.this);
                } else {
                    user.setName(name);
                    user.setPhoneNumber(phone);
                    user.setEmail(email);
                    firebaseAccount.attemptSignup(email, password, user, bitmap);
                }
            }
        });

        findViewById(R.id.back_buttonw_in_activity_sign_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
    }

    private void initDatePickersAndViews() {
        birthdayText = findViewById(R.id.birthday_text_view_in_activity_sign_up);
        submitButton = findViewById(R.id.date_pick_button_in_sign_up);


        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        cal.set(year, month, day);
        user.setBirthday(year+"-"+(month+1)+"-"+day);
        birthdayText.setText(fmt.format(cal.getTime()));


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUpActivity.this, android.app.AlertDialog.THEME_HOLO_LIGHT,startDateSetListener, year, month, day).show();
            }
        });

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
            birthdayText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            user.setBirthday(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }


    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_GALLERY && data!=null) {

            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                bitmap = extras.getParcelable("data");
                profileImage.setImageBitmap(bitmap);
            }
        }

    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("outputX", 256);
        intent.putExtra("outputY", 256);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, MainActivity.REQUEST_GALLERY);
    }

}