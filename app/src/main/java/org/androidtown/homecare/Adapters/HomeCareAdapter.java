package org.androidtown.homecare.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.homecare.Activities.HomeCareActivity;
import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Models.HomeCare;
import org.androidtown.homecare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hanhb on 2017-11-07.
 */

/*
    HomeCareAdapter : 게시물 관리 어댑터

 */

public class HomeCareAdapter extends RecyclerView.Adapter {

    private List<HomeCare> list;
    private Context context;

    public HomeCareAdapter(List<HomeCare> list, Context context) {
        this.list = list;
        this.context = context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_care, parent, false);
        return new HomeCareViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final HomeCare homeCare = list.get(position);

        ((HomeCareViewHolder)holder).bind(homeCare);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HomeCareActivity.class);
                Pair<View, String> profileImagePair = Pair.create((View)((HomeCareViewHolder)holder).profileImageView,"profile_image_transition");
                Pair<View, String> cardViewPair = Pair.create((View)((HomeCareViewHolder)holder).homeCareCardView,"card_view_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, profileImagePair, cardViewPair);

                intent.putExtra("key", homeCare.getKey());
                intent.putExtra("uid", MainActivity.uid);

                ((Activity) context).startActivityForResult(intent, MainActivity.REQUEST_HOME_CARE_ACTIVITY ,options.toBundle());

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class HomeCareViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView profileImageView;
        TextView titleText, dateText, payText, periodText, careTypeText, locationText;
        CardView homeCareCardView;

        public HomeCareViewHolder(View itemView) {
            super(itemView);
            homeCareCardView = itemView.findViewById(R.id.home_care_card_view);
            profileImageView = itemView.findViewById(R.id.profile_image_view);
            profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
            profileImageView.setClipToOutline(true);
            profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            titleText = itemView.findViewById(R.id.home_care_title_text_view);
            dateText = itemView.findViewById(R.id.home_care_upload_dat_text_view);
            periodText = itemView.findViewById(R.id.home_care_period_text_view);
            payText = itemView.findViewById(R.id.home_care_pay_text_view);
            careTypeText = itemView.findViewById(R.id.home_care_care_type_text_view);
            locationText = itemView.findViewById(R.id.home_care_location_text_view);

        }

        void bind(HomeCare homeCare){
            titleText.setText(homeCare.getTitle());

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

            payText.setText(String.valueOf(homeCare.getPay()));
            careTypeText.setText(homeCare.getCareType());
            locationText.setText(homeCare.getLocation());
        }
    }


}
