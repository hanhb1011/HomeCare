package org.androidtown.homecare.Fragments;


import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Activities.MessageActivity;
import org.androidtown.homecare.Activities.RatingActivity;
import org.androidtown.homecare.Firebase.FirebaseProfile;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;
import org.androidtown.homecare.Utils.ProgressDialogHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeCareFragment extends Fragment {

    private static LinearLayout hiddenLayout, noneCareLayout; //진행중인 홈케어의 존재 여부에 따라 레이아웃을 띄운다.
    private static ImageView profileImageView;
    private static TextView titleText, dateText, payText, periodText, careTypeText, locationText, commentText
            , nameText, starText;
    private static Button messageButton, estimationButton, cancelButton;
    private static SwipeRefreshLayout swipeRefreshLayout;

    private static boolean mutex = false; //액티비티가 중첩돼서 실행되지 않게 해줌

    public HomeCareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_care, container, false);
        initView(view);

        return view;
    }

    public static void setViews() {
        HomeCare homeCare = MainActivity.getHomeCareOfCurrentUser(); //메인에서 서버로부터 받은 홈케어 리스트에서 해당 key에 맞는 홈케어를 탐색.
        User user = MainActivity.getOpponentUser(); //작성자 정보를 불러온다.

        //사진을 띄움
        MainActivity.getFirebasePicture().downloadImage(user.getUid(), profileImageView);

        //시간 관련 텍스트뷰 (Period, Date)
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("MM/dd");
        SimpleDateFormat fmt3 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(homeCare.getStartPeriod());
        periodText.setText(fmt.format(cal.getTime()));
        cal.setTimeInMillis(homeCare.getEndPeriod());
        periodText.append(" - "+ fmt2.format(cal.getTime()));
        cal.setTimeInMillis((long)homeCare.getTimestamp());
        dateText.setText(fmt3.format(cal.getTime()));

        //나머지
        payText.setText(String.valueOf(homeCare.getPay()));
        careTypeText.setText(homeCare.getCareType());
        locationText.setText(homeCare.getLocation());
        commentText.setText(homeCare.getComment());
        titleText.setText(homeCare.getTitle());

        //유저 정보를 띄움
        starText.setText("★ " + String.format("%.2f",user.getStar()) + " (" + user.getHomecareCount() + ")");
        nameText.setText(user.getName());



    }

    private void initView(View view) {

        hiddenLayout = view.findViewById(R.id.hidden_view_in_message_fragment);
        noneCareLayout = view.findViewById(R.id.none_care_view_in_message_fragment);
        profileImageView = view.findViewById(R.id.profile_image_view_in_fragment_home_care);
        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        titleText = view.findViewById(R.id.title_text_view_in_fragment_home_care);
        dateText = view.findViewById(R.id.upload_date_text_view_in_fragment_home_care);
        payText = view.findViewById(R.id.home_care_pay_text_view_in_fragment_home_care);
        periodText = view.findViewById(R.id.home_care_period_text_view_in_fragment_home_care);
        careTypeText = view.findViewById(R.id.home_care_care_type_text_view_in_fragment_home_care);
        locationText = view.findViewById(R.id.home_care_location_text_view_in_fragment_home_care);
        commentText = view.findViewById(R.id.comment_text_view_in_fragment_home_care);
        nameText = view.findViewById(R.id.name_text_view_in_fragment_home_care);
        starText = view.findViewById(R.id.star_text_view_in_fragment_home_care);

        cancelButton = view.findViewById(R.id.cancel_button_in_fragment_home_care);
        messageButton = view.findViewById(R.id.message_button_in_fragment_home_care);
        estimationButton = view.findViewById(R.id.estimation_button_in_fragment_home_care);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_in_home_care_fragment);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ((MainActivity)getActivity()).refresh(false, swipeRefreshLayout);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mutex)
                    return;
                mutex = true;

                ProgressDialogHelper.show(HomeCareFragment.this.getActivity());
                FirebaseProfile.getUserRef().child(MainActivity.getUidOfCurrentUser()).child("current_homecare").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mutex = false;
                        ProgressDialogHelper.dismiss();
                        if (dataSnapshot.getValue(String.class) == null) {
                            Toast.makeText(HomeCareFragment.this.getContext(), "삭제된 홈케어입니다.", Toast.LENGTH_SHORT).show();
                            ((MainActivity) HomeCareFragment.this.getActivity()).refresh(true, null);
                        } else {
                            MessageDialogFragment.setContext(HomeCareFragment.this.getActivity());
                            MessageDialogFragment.setKeyAndUid(MainActivity.getHomeCareOfCurrentUser().getKey(), MainActivity.getUidOfCurrentUser());
                            MessageDialogFragment.showDialog(MessageDialogFragment.HOMECARE_CANCELLATION, HomeCareFragment.this.getActivity());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mutex = false;
                    }
                });




            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mutex)
                    return;
                mutex = true;

                ProgressDialogHelper.show(HomeCareFragment.this.getActivity());
                FirebaseProfile.getUserRef().child(MainActivity.getUidOfCurrentUser()).child("current_homecare").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mutex = false;
                        ProgressDialogHelper.dismiss();
                        if (dataSnapshot.getValue(String.class) == null) {
                            Toast.makeText(HomeCareFragment.this.getContext(), "삭제된 홈케어입니다.", Toast.LENGTH_SHORT).show();
                            ((MainActivity) HomeCareFragment.this.getActivity()).refresh(true, null);
                        } else {
                            Intent intent = new Intent(HomeCareFragment.this.getActivity(), MessageActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mutex = false;
                    }
                });

            }
        });

        estimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mutex)
                    return;
                mutex = true;

                ProgressDialogHelper.show(HomeCareFragment.this.getActivity());
                FirebaseProfile.getUserRef().child(MainActivity.getUidOfCurrentUser()).child("current_homecare").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mutex = false;
                        ProgressDialogHelper.dismiss();
                        if(dataSnapshot.getValue(String.class) == null){
                            Toast.makeText(HomeCareFragment.this.getContext(), "삭제된 홈케어입니다.", Toast.LENGTH_SHORT).show();
                            ((MainActivity)HomeCareFragment.this.getActivity()).refresh(true, null);
                        } else {
                            Intent intent = new Intent(HomeCareFragment.this.getActivity(), RatingActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mutex = false;
                    }
                });

            }
        });

    }


    public static LinearLayout getHiddenLayout() {
        return hiddenLayout;
    }

    public static LinearLayout getNoneCareLayout() {
        return noneCareLayout;
    }
}
