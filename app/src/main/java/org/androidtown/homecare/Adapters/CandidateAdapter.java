package org.androidtown.homecare.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.homecare.Activities.CandidateListActivity;
import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Fragments.MessageDialogFragment;
import org.androidtown.homecare.Models.User;
import org.androidtown.homecare.R;

import java.util.List;

/**
 * Created by hanhb on 2017-11-10.
 */


/*
    Candidate Adapter
    홈케어를 신청한 사람들의 프로필을 띄움
 */
public class CandidateAdapter extends RecyclerView.Adapter {

    private List<User> userList; //지원자 리스트
    private Context context;

    public CandidateAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidate, parent, false);
        return new CandidateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        User user = userList.get(position);
        ((CandidateViewHolder)holder).bind(user);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CandidateViewHolder) holder).isVisible){
                    ((CandidateViewHolder) holder).hiddenLayout.setVisibility(View.GONE);
                    ((CandidateViewHolder) holder).isVisible = false;
                } else {
                    ((CandidateViewHolder) holder).hiddenLayout.setVisibility(View.VISIBLE);
                    ((CandidateViewHolder) holder).isVisible = true;
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    private class CandidateViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        ImageView profileImageView;
        TextView nameText, starText;
        LinearLayout hiddenLayout;
        Button acceptButton;
        boolean isVisible = false;

        public CandidateViewHolder(View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profile_image_view_in_candidate_item);
            ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
            shapeDrawable.getPaint().setColor(Color.TRANSPARENT);
            profileImageView.setBackground(shapeDrawable);
            profileImageView.setClipToOutline(true);
            profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            nameText = itemView.findViewById(R.id.name_text_view_in_candidate_item);
            starText = itemView.findViewById(R.id.star_text_view_in_candidate_item);
            hiddenLayout = itemView.findViewById(R.id.hidden_view_in_candidate_item);
            acceptButton = itemView.findViewById(R.id.accept_button_in_candidate_item);
        }

        void bind(final User user){

            nameText.setText(user.getName());
            starText.setText("★ " + String.format("%.2f",user.getStar()) + " (" + user.getHomecareCount() + ")");

            acceptButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*
                        1. 자신의 uid와 상대방 uid 비교 후 같으면 x
                        2. MessageDialogFragment에서 사용자 의견을 묻는다.
                        3. FirevaseHomeCare 내의 pickCandidate() 콜함
                     */

                    if(MainActivity.getUidOfCurrentUser().equals(user.getUid()) || ((CandidateListActivity)context).getKey() == null
                            || ((CandidateListActivity)context).getKey().length() == 0){
                        Toast.makeText(context, "비정상적인 접근입니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    MessageDialogFragment.setContext(context);
                    MessageDialogFragment.setKeyAndUid(((CandidateListActivity)context).getKey(), user.getUid());
                    MessageDialogFragment.showDialog(MessageDialogFragment.CANDIDATE_PICK, context);
                }
            });

            CandidateListActivity.getFirebasePicture().downloadImage(user.getUid(), profileImageView);
        }
    }

}
