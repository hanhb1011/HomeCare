package org.androidtown.homecare.Fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    private static ImageView profileImageUploadButton;
    private static ImageView profileImage;
    private static TextView nameText, starText, homecareCountText, locText, emailText
            , birthText, phoneText, persText , susText, exceedText;

    public MyPageFragment() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_my_page, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        profileImageUploadButton = view.findViewById(R.id.profile_image_upload_button);
        profileImageUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });
        profileImage = view.findViewById(R.id.profile_image_view_in_fragment_my_page);
        nameText = view.findViewById(R.id.name_text_in_fragment_my_page);
        starText = view.findViewById(R.id.star_text_view_in_fragment_my_page);
        homecareCountText= view.findViewById(R.id.home_care_count_in_fragment_my_page);
        locText = view.findViewById(R.id.location_text_in_fragment_my_page);
        emailText = view.findViewById(R.id.email_text_in_fragment_my_page);
        birthText = view.findViewById(R.id.birthday_in_fragment_my_page);
        phoneText = view.findViewById(R.id.phone_in_fragment_my_page);
        persText  = view.findViewById(R.id. personality_in_fragment_my_page);
        susText = view.findViewById(R.id.sus_in_fragment_my_page);
        exceedText = view.findViewById(R.id.exceed_in_fragment_my_page);
    }

    public static void setView(){

        //프로필 이미지
        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(Color.TRANSPARENT);
        profileImage.setBackground(shapeDrawable);
        profileImage.setClipToOutline(true);
        profileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        MainActivity.getFirebasePicture().downloadImage(MainActivity.getUidOfCurrentUser(), profileImage);

        //텍스트들
        nameText.setText(MainActivity.getCurrentUser().getName());
        starText.setText(String.format("%.2f", MainActivity.getCurrentUser().getStar()));
        homecareCountText.setText(MainActivity.getCurrentUser().getHomecareCount() + "회");
        locText.setText(MainActivity.getCurrentUser().getLocation());
        emailText.setText(MainActivity.getCurrentUser().getEmail());
        birthText.setText(MainActivity.getCurrentUser().getBirthday());
        phoneText.setText(MainActivity.getCurrentUser().getPhoneNumber());
        persText.setText(MainActivity.getCurrentUser().getPersonality());
        susText.setText(MainActivity.getCurrentUser().getSuspensions() + "회");
        exceedText.setText(MainActivity.getCurrentUser().getExceededPayments() + "회");
    }

    public void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REQUEST_GALLERY && data!=null) {

            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap bitmap = extras.getParcelable("data");
                MainActivity.getProfileImageView().setImageBitmap(bitmap);
                profileImage.setImageBitmap(bitmap);
                MainActivity.getFirebasePicture().uploadImage(MainActivity.getUidOfCurrentUser(), bitmap);
            }
        }

    }


}
