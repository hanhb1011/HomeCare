package org.androidtown.homecare.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidtown.homecare.MachineLearning.DataGenerator;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;

public class DataGeneratorActivity extends AppCompatActivity {

    /*

        Supervised Learning 을 위한 데이터셋을 만드는 액티비티
        TODO 일반 유저는 접근할 수 없게 만들기

        features :
        1. star (총 평점)
        2. homecare count (홈케어 진행 횟수)
        3. suspensions (홈케어 중단 횟수)
        4. exceededPayments (비정상적으로 높은 금액 입력)
        5. type ( 0일 경우 일반, 1일 경우 비정상)

    */
    EditText star, careCount, suspensions, exceededPayments, type;
    Button submitBtn;
    DataGenerator dataGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_generator);

        final User user = new User();
        dataGenerator = new DataGenerator();

        star = findViewById(R.id.star_edt);
        careCount = findViewById(R.id.count_edt);
        suspensions = findViewById(R.id.sus_edt);
        exceededPayments = findViewById(R.id.exceed_edt);
        type = findViewById(R.id.type_edt);
        submitBtn = findViewById(R.id.submt_btn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    user.setStar(Double.parseDouble(star.getText().toString()));
                    user.setHomecareCount(Integer.valueOf(careCount.getText().toString()));
                    user.setSuspensions(Integer.valueOf(suspensions.getText().toString()));
                    user.setExceededPayments(Integer.valueOf(exceededPayments.getText().toString()));

                    if(Integer.valueOf(type.getText().toString()) == 0){
                        //normal user [1 0]
                        user.setType0(1);
                        user.setType1(0);
                    } else {
                        //abnormal user [0 1]
                        user.setType0(0);
                        user.setType1(1);
                    }

                    dataGenerator.generateTrainingData(user);
                    Toast.makeText(DataGeneratorActivity.this, "완료", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(DataGeneratorActivity.this, "타입 불일치. 인풋을 확인하세요.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
