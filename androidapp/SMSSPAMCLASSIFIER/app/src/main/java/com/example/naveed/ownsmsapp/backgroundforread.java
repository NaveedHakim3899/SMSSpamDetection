package com.example.naveed.ownsmsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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


public class backgroundforread extends AsyncTask <String, Void,String> {

    AlertDialog dialog;
    Context context;
    public Boolean login = false;

    public backgroundforread(Context context) {
        this.context = context;
    }
    public backgroundforread(){

    }
    @Override
    protected void onPreExecute() {

        // dialog = new AlertDialog.Builder(context).create();
        // dialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String s) {
        //  dialog.setMessage(s);
        //  dialog.show();
        //Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show( );
        Log.d("testing",""+s );
    }

    @Override
    protected String doInBackground(String... voids) {
        String result = "";
        String number = voids[0];
        String text = voids[1];
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String connstr = "http://172.18.0.89:80/mytry1.php";

        try {
            URL url = new URL(connstr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoInput(true);
            http.setDoOutput(true);

            OutputStream ops = http.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops, "UTF-8"));
            String data = URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8")
                    + "&&" + URLEncoder.encode("text", "UTF-8") + "=" + URLEncoder.encode(text, "UTF-8");
            writer.write(data);
            writer.flush();
            writer.close();
            ops.close();

            InputStream ips = http.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips, "ISO-8859-1"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
            ips.close();
            http.disconnect();
            return result;

        } catch (MalformedURLException e) {
             e.getMessage();
        } catch (IOException e) {
             e.getMessage();
        }


        return result;
    }
}