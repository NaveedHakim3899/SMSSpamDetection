package com.example.naveed.ownsmsapp;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.SmsMessage;
import android.widget.Toast;
import android.content.Intent;

import java.util.concurrent.ExecutionException;

public class MyReceiver2 extends BroadcastReceiver  {

 //   public  static ArrayList<MainActivity.ContactList> arrayList_contactlist1 = new ArrayList<>();

    private static final String TAG = "MyReceiver";
    public static final String pdu_type = "pdus";
    String number;
    String text;
    MessageAdapter messageAdapter;
    @TargetApi(Build.VERSION_CODES.M)


    @Override
    public void onReceive(Context context, Intent intent) {
        Context context1 = null;
        // Get the SMS message.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.
            boolean isVersionM =
                    (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                // Check Android version and use appropriate createFromPdu.
                if (isVersionM) {
                    // If Android version M or newer:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    // If Android version L or older:
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                // Build the message to show


                number = msgs[i].getOriginatingAddress();
                //    tryi mtry = new tryi();
                //  String checkofcontact=  mtry.sendingtexttomain(number);

                text = msgs[i].getMessageBody();
                text = text.replaceAll("'","");


                text = text.replaceAll("\'","");


            }
            int isinContactlist=0;

            String checkofcontact = "not in list";
               if(checkofcontact.equals("not in list")){
                   strMessage += "SMS from " + number;
                   Toast.makeText(context, ""+strMessage, Toast.LENGTH_SHORT).show( );

                   backgrounf bk = new backgrounf(context);

                   int check=0;
                   ContentResolver cr = context.getContentResolver(); //Activity/Application android.content.Context
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
                                   if(PhoneNumberUtils.compare(contactNumber,number)){
                                       //Toast.makeText(getActivity(), "Name"+pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)), Toast.LENGTH_SHORT).show( );
                                       check=1;
                                       // return pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                                   }

                               }
                               pCur.close();
                           }

                       } while (cursor.moveToNext()) ;
                   }


                   bk.execute(number, text, String.valueOf(check));


               }
               else
               { strMessage += "SMS from" +  checkofcontact;}

               strMessage += " :" + text+ "\n";

                // Log and display the SMS message.


            }
      //  sqlitehelper helper1= new sqlitehelper(context);
      //  SQLiteDatabase database1 = helper1.getWritableDatabase();
       // ContentValues values = new ContentValues();
       // values.put("NUMBER", number);
       // values.put("MESSAGE",text);
       // values.put("RESULT","0");
       // values.put("IMI",getimi());
     //   values.put("BelongToCurrentUser",false);
       // database1.insert("MYMESSAGES", null, values);
       // database1.close();

     //   messageAdapter.add(new Message(number,true));
        //messageAdapter.notifyDataSetChanged();

        //messageAdapter.notifyDataSetChanged();

      //  backgroundforread bk= new backgroundforread(context);
       // bk.execute("number","text");
//
//        sqlitehelper helper1= new sqlitehelper(context);
//        SQLiteDatabase database1 = helper1.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("NUMBER", number);
//        values.put("MESSAGE",text);
//        //   values.put("RESULT",output);
//        // values.put("IMI",getimi());
//        //   values.put("BelongToCurrentUser",false);
//        database1.insert("MYMESSAGES", null, values);
//        database1.close();

        backgroundforread bk= new backgroundforread(context);
        //  bk.execute("number","text");
        String output = "0";
       // String output = null;
        try {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            output = new backgroundforread(context).execute("number","message").get();
            //output="0";
            sqlitehelper helper1= new sqlitehelper(context);
            SQLiteDatabase database1 = helper1.getWritableDatabase();


            ContentValues values = new ContentValues();
            values.put("NUMBER", number);
            values.put("MESSAGE",text);
            values.put("RESULT",output);
            // values.put("IMI",getimi());
            //   values.put("BelongToCurrentUser",false);
            database1.insert("MYMESSAGES", null, values);
            database1.close();
        } catch (ExecutionException e) {
            e.printStackTrace( );
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
        Toast.makeText(context, ""+strMessage, Toast.LENGTH_SHORT).show( );
        if((output.equals("1") || ((number.equals("40404")) || (number.length()<7)))){

           Toast.makeText(context, "I am spam Message", Toast.LENGTH_SHORT).show( );
        }
        else
        {
            Toast.makeText(context, "I am ham Message", Toast.LENGTH_SHORT).show( );
        }

        Toast.makeText(context, " "+output, Toast.LENGTH_SHORT).show( );


    }




    }
