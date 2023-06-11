package com.example.naveed.ownsmsapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;



public class New_Message extends AppCompatActivity {
    private Toolbar ChatNametoolbar;
    // private RecyclerView messaagesList;
    EditText editText;
    //private final List<String> messageList = new ArrayList<String>();
    //private LinearLayoutManager mlinearLayout;

    public static MessageAdapter messageAdapter;
    public static ListView messagesView;
    String number;
    String name;
    MemberData data;
    String number1;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__message);
        //registerReceiver(broadcastReceiver, new IntentFilter("broadCastName"));
        String a;



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        name = extras.getString("name");
        number = extras.getString("number");
       number1= number;
        ChatNametoolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(ChatNametoolbar);
        getSupportActionBar().setTitle(name);


        editText = (EditText) findViewById(R.id.textmessage);
        messageAdapter = new MessageAdapter(this);
        messagesView = (ListView) findViewById(R.id.messages_view);
        messagesView.setAdapter(messageAdapter);
        data = new MemberData(name, getRandomColor());



        final String[] message = new String[1];

    //    Toast.makeText(this, a, Toast.LENGTH_SHORT).show();



    }
    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 2);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 2: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    //do your specific task after read phone state granted
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    protected void onStart() {
        super.onStart( );
      //  Toast.makeText(this, ""+number1, Toast.LENGTH_SHORT).show( );
        sqlitehelper helper = new sqlitehelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor ;
        Toast.makeText(this, ""+number, Toast.LENGTH_SHORT).show( );
         //cursor = database.rawQuery(String.format("Select MESSAGE from MYMESSAGES Where NUMBER = "+ number1), new String[]{});
        //cursor = database.rawQuery(String.format("Select MESSAGE from MYMESSAGES Where NUMBER= + \"+923086343472\""), new String[]{});
        number1= "\"" + "" + number1 + "\"";
        cursor = database.rawQuery(String.format("Select MESSAGE from MYMESSAGES "), new String[]{});


        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst( );
        }
        StringBuilder builder = new StringBuilder( );

        while(cursor.moveToNext())
        {   //String number = cursor.getString(0);
            String message = cursor.getString(0);
            messageAdapter.add(new Message(message,true));
            messageAdapter.notifyDataSetChanged();
           // Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show( );
            builder.append(number + message);

        }
           // while (cursor.moveToNext());

            //Toast.makeText(this, builder, Toast.LENGTH_SHORT).show();
        //    builder.append(number + message);

        }






    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause( );

    }



    public String getimi(){
        String a = null;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }
            a=   telephonyManager.getDeviceId();
        }
        return a;

    }

    public void sendMessage(View view) {

        String message = editText.getText().toString();
        if (message.length() > 0) {
            SmsManager sms = SmsManager.getDefault();
          //  PendingIntent sentPI;
            String SENT = "SMS_SENT";

            //sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
            sms.sendTextMessage(number1, null, message, null, null);
           // final SmsManager smsManager = SmsManager.getDefault();
           // smsManager.sendTextMessage("03086343472", "03086343472", editText.getText().toString(), null, null);
            messageAdapter.add(new Message(editText.getText().toString(),true));
            messageAdapter.notifyDataSetChanged();

            editText.getText().clear();
            String imi="00";
            if(isPermissionGranted()){
                imi=getimi();
            }
            String result="0";
            sqlitehelper helper= new sqlitehelper(this);
            SQLiteDatabase database = helper.getWritableDatabase();
            String trimmednum=number;
            trimmednum= trimmednum.trim().replaceAll("\\s+", "");
            ContentValues values = new ContentValues();
            values.put("NUMBER",trimmednum);
            values.put("MESSAGE",message);
            values.put("IMI",imi);
            values.put("RESULT",result);
            database.insert("MYMESSAGES", null, values);
            database.close();

         //   Toast.makeText(New_Message.this, message  +number1, Toast.LENGTH_SHORT).show();
          

        }
    }



    private String getRandomColor() {
        Random r = new Random();
        StringBuffer sb = new StringBuffer("#");
        while (sb.length() < 7) {
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, 7);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
      //   unregisterReceiver(broadcastReceiver);


    }


}


class MemberData {
    private String name;
    private String color;

    public MemberData(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public MemberData() {
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "MemberData{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }














}




