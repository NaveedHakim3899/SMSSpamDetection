package com.example.naveed.ownsmsapp;


import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


/*
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<String> mMessageList;

    public MessageAdapter(New_Message mMessageList) {

        this.mMessageList = mMessageList;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single ,parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public ImageView profileImage;
        public TextView displayName;
        public ImageView messageImage;

        public MessageViewHolder(View view) {
            super(view);

            messageText = (TextView) view.findViewById(R.id.textlayout);
            profileImage = (ImageView) view.findViewById(R.id.profilelayout);


        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        String c = mMessageList.get(i);
        viewHolder.messageText.setText(c);


    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }






}*/

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
public class MessageAdapter extends BaseAdapter {

    List<Message> messages = new ArrayList<Message>();
    Context context;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void add(Message message) {
        this.messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i);


        if (message.isBelongsToCurrentUser()) {
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getText());
        } else {


            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getText());
        //    convertView = messageInflater.inflate(R.layout.their_message, null);
           // holder.avatar = (View) convertView.findViewById(R.id.avatar);
         //   holder.name = (TextView) convertView.findViewById(R.id.name);
           // holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            //convertView.setTag(holder);

          //  holder.name.setText(message.getMemberData().getName());
          //  holder.messageBody.setText(message.getText());
           // GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
         //   drawable.setColor(Color.parseColor(message.getMemberData().getColor()));
        }

        return convertView;
    }
    }


class MessageViewHolder {
    public View avatar;
    public TextView name;
    public TextView messageBody;
}