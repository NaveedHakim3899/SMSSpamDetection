package com.example.naveed.ownsmsapp;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
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

public class MessageouterAdapter extends BaseAdapter {

    List<User> outermessages = new ArrayList<User>();
    Context context;

    public MessageouterAdapter(Context context) {
        this.context = context;
    }

    public void add(User user) {
        outermessages.add(user);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return outermessages.size();
    }

    @Override
    public Object getItem(int i) {
        return outermessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder1 holder = new MessageViewHolder1();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        User user = outermessages.get(i);



            convertView = messageInflater.inflate(R.layout.message_single, null);
            holder.name = (TextView) convertView.findViewById(R.id.textlayout);
            convertView.setTag(holder);
            holder.name.setText(user.getNumber());


        return convertView;
    }
    }


class MessageViewHolder1 {
    public View avatar;
    public TextView name;

}