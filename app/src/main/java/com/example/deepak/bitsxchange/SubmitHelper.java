package com.example.deepak.bitsxchange;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Deepak on 14-02-2015.
 */
public class SubmitHelper extends AsyncTask<String, Void, String> {

    private Context context;

    public SubmitHelper(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        try {
            String title = (String) arg0[0];
            String price = (String) arg0[1];
            String description = (String) arg0[2];
            String uid = (String) arg0[3];
            String filename = (String) arg0[4];


            String link = "http://bitsxchange.net84.net/upload.php";
            String data = URLEncoder.encode("pname", "UTF-8")
                    + "=" + URLEncoder.encode(title, "UTF-8");
            data += "&" + URLEncoder.encode("price", "UTF-8")
                    + "=" + URLEncoder.encode(price, "UTF-8");
            data += "&" + URLEncoder.encode("description", "UTF-8")
                    + "=" + URLEncoder.encode(description, "UTF-8");

            data += "&" + URLEncoder.encode("uid", "UTF-8")
                    + "=" + URLEncoder.encode(uid, "UTF-8");
            data += "&" + URLEncoder.encode("filename", "UTF-8")
                    + "=" + URLEncoder.encode(filename, "UTF-8");


            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter
                    (conn.getOutputStream());
            wr.write(data);
            wr.flush();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            return sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(String result) {
        if (result.startsWith("0")) {
            Toast.makeText(context, "Invalid email or password.", Toast.LENGTH_LONG).show();
        } else if (result.startsWith("1")) {
            Toast.makeText(context, "Upload successful", Toast.LENGTH_LONG).show();
        }
    }


}
