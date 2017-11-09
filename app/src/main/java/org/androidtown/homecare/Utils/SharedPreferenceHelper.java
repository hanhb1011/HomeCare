package org.androidtown.homecare.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by hanhb on 2017-11-09.
 */
/*
    로컬 스토리지에 정보 저장
    모든 메서드는 static (get/put Boolean or String values)
    1. 로그인 데이터 저장(자동로그인 구현 전까지)
    2. 필터 값 보존
 */
public class SharedPreferenceHelper {

    private SharedPreferenceHelper(){}

    public static boolean getBoolean(Context context, String key){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
    }

    public static void putBoolean(Context context, String key, boolean value){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, "");
    }

    public static void putString(Context context, String key, String value){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();

    }

}
