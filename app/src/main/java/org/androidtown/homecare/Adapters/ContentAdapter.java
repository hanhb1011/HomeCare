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

import org.androidtown.homecare.Activities.ContentActivity;
import org.androidtown.homecare.Models.Content;
import org.androidtown.homecare.R;

import java.util.List;

/**
 * Created by hanhb on 2017-11-07.
 */

/*
    ContentAdapter : 게시물 관리 어댑터

 */

public class ContentAdapter extends RecyclerView.Adapter {

    private List<Content> list;
    private Context context;

    public ContentAdapter(List<Content> list, Context context) {
        this.list = list;
        this.context = context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        return new ContentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Content content = list.get(position);


        ((ContentViewHolder)holder).bind(content);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ContentActivity.class);
                Pair<View, String> profileImagePair = Pair.create((View)((ContentViewHolder)holder).profileImageView,"profile_image_transition");
                Pair<View, String> cardViewPair = Pair.create((View)((ContentViewHolder)holder).contentCardView,"card_view_transition");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, profileImagePair, cardViewPair);

                //인텐트에 key 넣기

                context.startActivity(intent, options.toBundle());

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView profileImageView;
        TextView titleText, dateText, payText, periodText, careTypeText, locationText;
        CardView contentCardView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            contentCardView = itemView.findViewById(R.id.content_card_view);
            profileImageView = itemView.findViewById(R.id.profile_image_view);
            profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
            profileImageView.setClipToOutline(true);
            profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            titleText = itemView.findViewById(R.id.content_title_text_view);
            dateText = itemView.findViewById(R.id.content_upload_dat_text_view);
            periodText = itemView.findViewById(R.id.content_period_text_view);
            payText = itemView.findViewById(R.id.content_pay_text_view);
            careTypeText = itemView.findViewById(R.id.content_care_type_text_view);
            locationText = itemView.findViewById(R.id.content_location_text_view);

        }

        void bind(Content content){

        }
    }


}
