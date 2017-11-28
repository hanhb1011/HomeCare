package org.androidtown.homecare.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.androidtown.homecare.Firebase.FirebasePicture;
import org.androidtown.homecare.Models.Estimation;
import org.androidtown.homecare.R;

import java.util.List;

/**
 * Created by hanhb on 2017-11-25.
 */

public class EstimationAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<Estimation> list;
    private FirebasePicture firebasePicture;

    public EstimationAdapter(Context context, List<Estimation> list) {
        this.context = context;
        this.list = list;
        firebasePicture = new FirebasePicture(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estimation, parent, false);
        return new EstimationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Estimation estimation = list.get(position);
        ((EstimationViewHolder)holder).bind(estimation);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class EstimationViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImageView;
        TextView nameText, commentText;
        RatingBar kind, well, faith;

        public EstimationViewHolder(View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profile_image_view_in_item_estimation);
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(Color.TRANSPARENT);
            profileImageView.setBackground(shapeDrawable);
            profileImageView.setClipToOutline(true);
            profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            nameText = itemView.findViewById(R.id.name_text_view_in_item_estimation);
            commentText = itemView.findViewById(R.id.comment_edit_text_in_item_estimation);
            kind = itemView.findViewById(R.id.kindness_rating_bar_in_item_estimation);
            well = itemView.findViewById(R.id.wellness_rating_bar_in_item_estimation);
            faith = itemView.findViewById(R.id.faithfulness_rating_bar_in_item_estimation);

        }

        void bind(Estimation estimation){

            firebasePicture.downloadImage(estimation.getUid(), profileImageView);
            nameText.setText(estimation.getName());
            commentText.setText(estimation.getComment());
            kind.setRating(Float.valueOf(estimation.getKindness().toString()));
            well.setRating(Float.valueOf(estimation.getWellness().toString()));
            faith.setRating(Float.valueOf(estimation.getFaithfulness().toString()));

        }
    }

}
