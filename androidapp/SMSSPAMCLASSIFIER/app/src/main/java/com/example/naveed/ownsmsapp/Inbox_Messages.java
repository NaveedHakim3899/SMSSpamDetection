package com.example.naveed.ownsmsapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Inbox_Messages extends Fragment  implements View.OnClickListener {

    private Toolbar ChatNametoolbar;
    public static MessageouterAdapter outermessageAdapter;
    public static ListView outermessagesView;
    String number;
    String name;
    ArrayList<String> arl = new ArrayList<String>();

    public Inbox_Messages() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View view;
    MessageouterAdapter messageouterAdapter;

    private ListView chatslist;
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_inbox__messages, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener((View.OnClickListener) this);
        chatslist= (ListView) view.findViewById(R.id.chats_list);
        messageouterAdapter = new MessageouterAdapter(this.getActivity());
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
                number= checkforNumberAgainstname(tvCountry.getText().toString());
                Toast.makeText(getActivity(), ""+number, Toast.LENGTH_SHORT).show( );
                extras.putString("number",number);
               // extras.putString("number",;
                i.putExtras(extras);
                startActivity(i);


            }
        };
        chatslist.setOnItemClickListener(itemClickListener);
        return view;
    }
public String checkforNumberAgainstname(String name){
        String number;
    ContentResolver cr = getActivity().getContentResolver(); //Activity/Application android.content.Context
    Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    if(cursor.moveToFirst())
    {
        do
        {
            String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

            if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
            {
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                while (pCur.moveToNext())
                {
                    String contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    if(contactName.equals(name)){
                        //Toast.makeText(getActivity(), "Name"+pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)), Toast.LENGTH_SHORT).show( );
                        return pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }

                }
                pCur.close();
            }

        } while (cursor.moveToNext()) ;
    }

    return name;
}


    public void onClick(View v) {
        //do what you want to do when button is clicked
        switch (v.getId()) {
            case R.id.fab:
                Intent pickContact = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                pickContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(pickContact, 1);
                // Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                //startActivityForResult(intent, 10011);

        }
    }

    @Override

    public void onStart() {
        super.onStart( );
        sqlitehelper helper = new sqlitehelper(getContext( ));
        SQLiteDatabase database = helper.getReadableDatabase( );
        Cursor cursor;
        int count=0;
       cursor = database.rawQuery(String.format("Select NUMBER from MYMESSAGES Where RESULT=0"), new String[]{});
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            do {

                String number = cursor.getString(0);
                name = getnamefromContactList(number);
                //Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show( );
                count=0;
                for(int i=0;i<arl.size();i++){
                    if(arl.get(i).equals(name)) {
                        count=1;
                       // Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show( );

                    }
                }
                if(count==1)
                {

                }
                else
                {   //Toast.makeText(getActivity(), ""+name, Toast.LENGTH_SHORT).show( );

                        arl.add(name);
                        messageouterAdapter.add(new User(name));
                        messageouterAdapter.notifyDataSetChanged( );


                }

            }
            while (cursor.moveToNext( ));


        }
    }
    public String getnamefromContactList(String num){
        ContentResolver cr = getActivity().getContentResolver(); //Activity/Application android.content.Context
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if(cursor.moveToFirst())
        {
            do
            {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",new String[]{ id }, null);
                    while (pCur.moveToNext())
                    {
                        String contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        if(PhoneNumberUtils.compare(contactNumber,num)){
                            //Toast.makeText(getActivity(), "Name"+pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)), Toast.LENGTH_SHORT).show( );
                            return pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        }

                    }
                    pCur.close();
                }

            } while (cursor.moveToNext()) ;
        }
        return num;
    }


    @Override
    public void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            Uri contactData = data.getData( );
            Cursor c = getActivity( ).getContentResolver( ).query(contactData, null, null, null, null);

            if (c.moveToFirst( )) {
                String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                // String num = c.getString(phoneIndex);
                String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                //  Toast.makeText(getActivity( ), "Number=" + number + name, Toast.LENGTH_LONG).show( );

                Intent NewMessageintent = new Intent(getActivity( ), New_Message.class);
                Bundle extras = new Bundle( );
                extras.putString("name", name);
                extras.putString("number", number);
                NewMessageintent.putExtras(extras);
                startActivity(NewMessageintent);
            } else {
                Toast.makeText(getActivity( ), "" + "Phone NO not found", Toast.LENGTH_SHORT).show( );
            }
        }
        else
        {
            Toast.makeText(getActivity(), "No Contact Selected", Toast.LENGTH_SHORT).show( );
        }
    }
public class modelcontactlist{
        public String name;
        public String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


}



