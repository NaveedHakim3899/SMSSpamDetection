package com.example.naveed.ownsmsapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Spam_Messages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Spam_Messages() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Spam_Messages.
     */
    // TODO: Rename and change types and number of parameters
    public static Spam_Messages newInstance(String param1, String param2) {
        Spam_Messages fragment = new Spam_Messages();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    private ListView chatslist;
    MessageouterAdapterSpam messageouterAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_spam__messages, container, false);
        chatslist= (ListView) view.findViewById(R.id.chats_list);
        messageouterAdapter = new MessageouterAdapterSpam(this.getActivity());
        chatslist.setAdapter(messageouterAdapter);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                // Getting the Container Layout of the ListView
                LinearLayout linearLayoutParent = (LinearLayout) container;

                // Getting the inner Linear Layout
                LinearLayout linearLayoutChild = (LinearLayout ) linearLayoutParent.getChildAt(1);

                // Getting the Country TextView
                TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);

               // Toast.makeText(getContext(), tvCountry.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent i= new Intent(getActivity(),New_Message.class);
                Bundle extras= new Bundle();
                extras.putString("name",tvCountry.getText().toString());
                extras.putString("number",tvCountry.getText().toString());
                i.putExtras(extras);
                startActivity(i);


            }
        };
        chatslist.setOnItemClickListener(itemClickListener);

        return view;
    }
    ArrayList<String> arl = new ArrayList<String>();

    @Override

    public void onStart() {
        super.onStart( );
        sqlitehelper helper = new sqlitehelper(getContext( ));
        SQLiteDatabase database = helper.getReadableDatabase( );
        Cursor cursor;
       // number = "923096671963";
        int count=0;
        cursor = database.rawQuery(String.format("Select NUMBER,RESULT from MYMESSAGES Where RESULT=1 "), new String[]{});
        if (cursor != null && cursor.moveToFirst( )) {
           // Toast.makeText(this.getActivity(), "crsor khali nai aea", Toast.LENGTH_SHORT).show( );
            cursor.moveToFirst( );
            do {

                String number = cursor.getString(0);
                count=0;
                for(int i=0;i<arl.size();i++){
                    if(arl.get(i).equals(number)) {
                        // Toast.makeText(this.getActivity(), "count"+count+"i"+i, Toast.LENGTH_SHORT).show( );
                        count=1;

                    }



                }

                StringBuilder builder = new StringBuilder( );

                //   String message = cursor.getString(1);
                if(count==1)
                {
                    // Toast.makeText(this.getActivity(), "", Toast.LENGTH_SHORT).show( );

                }
                else
                {
                    arl.add(number);

                    messageouterAdapter.add(new User(number));
                    messageouterAdapter.notifyDataSetChanged( );
                }

                // builder.append(number + message);

            }
            while (cursor.moveToNext( ));


        }
    }
}
