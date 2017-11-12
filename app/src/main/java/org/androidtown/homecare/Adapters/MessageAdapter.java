package org.androidtown.homecare.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidtown.homecare.Activities.MainActivity;
import org.androidtown.homecare.Models.Message;
import org.androidtown.homecare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hanhb on 2017-11-12.
 */

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int MY_MESSAGE = 1;
    private static final int OPPONENT_MESSAGE = 2;
    private Context context;
    private List<Message> list;
    private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
    //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String oUid;

    public MessageAdapter(Context context, List<Message> list, String oUid) {
        this.context = context;
        this.list = list;
        this.oUid = oUid;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        Message message = list.get(position);

        if(message.getUid().equals(MainActivity.getUidOfCurrentUser())){ //my message
            return MY_MESSAGE;
        } else {// opponent message
            return OPPONENT_MESSAGE;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == MY_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_to_send, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == OPPONENT_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = list.get(position);

        if(holder.getItemViewType() == MY_MESSAGE){
            ((SentMessageHolder) holder).bind(message);
        } else {
            ((ReceivedMessageHolder)holder).bind(message);
        }

    }



    @Override
    public int getItemCount() {
        return list.size();
    }


    private  class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView mMsgText;
        TextView mTimewText;


        public SentMessageHolder(View itemView) {
            super(itemView);
            mMsgText = itemView.findViewById(R.id.m_msg_content);
            mTimewText = itemView.findViewById(R.id.m_time_text);
        }

        void bind(Message message){
            mMsgText.setText(message.getContent());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis((long)message.getTimestamp());
            mTimewText.setText(fmt.format(cal.getTime()));

        }

    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageView profileImage;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.o_msg_content);
            timeText = itemView.findViewById(R.id.o_time_text);
            profileImage = itemView.findViewById(R.id.o_profile_image);
        }

        void bind(Message message){
            messageText.setText(message.getContent());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis((long)message.getTimestamp());
            timeText.setText(fmt.format(cal.getTime()));

        }
    }

}