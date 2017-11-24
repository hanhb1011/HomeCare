package org.androidtown.homecare.MachineLearning;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.androidtown.homecare.Models.User;

/**
 * Created by hanhb on 2017-11-24.
 */

/*
    dataset을 만들기 위한 클래스
    generateTrainingData(User user)


 */
public class DataGenerator {

    public void generateTrainingData(User user){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("train_data").push();
        user.setUid(ref.getKey());
        ref.setValue(user);
    }

    //TODO 실제 유저 데이터로부터 데이터셋을 생성해야 한다.
    //홈케어 카운트가 1회 이상인 유저 정보만 데이터셋에 포함시킨다.
}
