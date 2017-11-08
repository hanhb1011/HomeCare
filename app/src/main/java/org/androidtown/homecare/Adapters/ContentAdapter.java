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
import android.widget.EditText;
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

    private static final int CONTENT = 1;
    private static final int WRITING = 2;
    private boolean isTimeLine; //타임라인이 아닐 경우 글 게시 뷰를 띄우면 안 되기 때문에 플래그 정의

    private List<Content> list;
    private Context context;

    public ContentAdapter(List<Content> list, Context context,  boolean isTimeLine) {
        this.list = list;
        this.isTimeLine = isTimeLine;
        this.context = context;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == CONTENT){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
            return new ContentViewHolder(view);
        } else if (viewType == WRITING){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_write_content, parent, false);
            return new WritingViewHolder(view);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(isTimeLine && position==0)
            return WRITING;
        else
            return CONTENT;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Content content = list.get(position);



        if(holder.getItemViewType() == WRITING){
            ((WritingViewHolder) holder).bind();
        } else {
            ((ContentViewHolder)holder).bind(content);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ContentActivity.class);
                    Pair<View, String> profileImagePair = Pair.create((View)((ContentViewHolder)holder).nameAndProfileLayout,"profile_image_transition");
                    Pair<View, String> contentPair = Pair.create((View)((ContentViewHolder)holder).contentTextView,"content_text_transition");
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, profileImagePair, contentPair);

                    //인텐트에 key 넣기

                    context.startActivity(intent, options.toBundle());

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ContentViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView profileImageView;
        TextView contentTextView;
        CardView contentCardView;
        View nameAndProfileLayout;



        public ContentViewHolder(View itemView) {
            super(itemView);
            contentCardView = itemView.findViewById(R.id.content_card_view);
            profileImageView = itemView.findViewById(R.id.profile_image_view);
            profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
            profileImageView.setClipToOutline(true);
            profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            contentTextView = itemView.findViewById(R.id.content_text_view);
            nameAndProfileLayout = itemView.findViewById(R.id.name_and_profile_view);
        }

        void bind(Content content){
            contentTextView.setText(content.getStringContent());
        }
    }

    private class WritingViewHolder extends RecyclerView.ViewHolder{
        EditText contentEditText;
        CardView writingCardView;

        public WritingViewHolder(View itemView) {
            super(itemView);
            contentEditText = itemView.findViewById(R.id.content_edit_text);
            writingCardView = itemView.findViewById(R.id.writing_card_view);
        }

        void bind(){


        }
    }

}
