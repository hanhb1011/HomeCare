package org.androidtown.homecare.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import org.androidtown.homecare.Models.User;

import java.util.List;

/**
 * Created by hanhb on 2017-11-10.
 */


/*
    Candidate Adapter
    홈케어를 신청한 사람들의 프로필을 띄움
 */
public class CandidateAdapter extends RecyclerView.Adapter {

    List<User> userList; //지원자 리스트

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
