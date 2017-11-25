package org.androidtown.homecare.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.homecare.Models.Estimation;

import java.util.List;

/**
 * Created by hanhb on 2017-11-25.
 */

public class EstimationAdapter extends RecyclerView.Adapter{

    Context context;
    List<Estimation> list;

    public EstimationAdapter(Context context, List<Estimation> list) {
        this.context = context;
        this.list = list;
    }

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

    private class EstimationView extends RecyclerView.ViewHolder{


        public EstimationView(View itemView) {
            super(itemView);
        }
    }

}
