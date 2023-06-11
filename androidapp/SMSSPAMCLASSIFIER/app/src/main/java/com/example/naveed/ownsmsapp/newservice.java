package com.example.naveed.ownsmsapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class newservice extends Service {
    public newservice() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate( );
        Toast.makeText(this, "testing", Toast.LENGTH_SHORT).show( );
      //   GetJSON getJSON = new GetJSON(this );
        // getJSON.execute( );
//
//        try {
//            String connstr = "http://172.18.0.53:80/mytry1.php";
//
//            //creating a URL
//            URL url = new URL(connstr);
//
//            //Opening the URL using HttpURLConnection
//            HttpURLConnection con = (HttpURLConnection) url.openConnection( );
//
//            //StringBuilder object to read the string from the service
//            StringBuilder sb = new StringBuilder( );
//
//            //We will use a buffered reader to read the string from service
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream( )));
//
//            //A simple string to read values from each line
//            String json;
//
//            //reading until we don't find null
//            while (( json=bufferedReader.readLine( )) != null) {
//
//                //appending it to string builder
//                json=bufferedReader.readLine();
//
//            }
//
//
//            //finally returning the read string
//            Toast.makeText(this, "db vale for spam"+ json, Toast.LENGTH_SHORT).show( );
//        } catch (Exception e) {
//            Toast.makeText(this, "exception oiccured", Toast.LENGTH_SHORT).show( );
//        }
       backgroundforread bk= new backgroundforread(this);
       bk.execute("number","text");

    }
}
