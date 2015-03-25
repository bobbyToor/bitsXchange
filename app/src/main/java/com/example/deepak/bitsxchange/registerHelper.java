package com.example.deepak.bitsxchange;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;

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
 * Created by Deepak on 12-02-2015.
 */
public class registerHelper extends AsyncTask<String, Void, String> {
    private Context context;
    private TextView warning;

    public registerHelper(Context context, TextView warning) {
        this.warning = warning;
        this.context = context;
    }

    String email;

    @Override
    protected String doInBackground(String... arg0) {
        try {
            email = (String) arg0[0];
            String firstName = (String) arg0[1];
            String lastName = (String) arg0[2];
            String password = (String) arg0[3];
            String phoneNumber = (String) arg0[4];
            String link = "http://bitsxchange.net84.net/register.php";
            String data = URLEncoder.encode("firstname", "UTF-8")
                    + "=" + URLEncoder.encode(firstName, "UTF-8");
            data += "&" + URLEncoder.encode("lastname", "UTF-8")
                    + "=" + URLEncoder.encode(lastName, "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            data += "&" + URLEncoder.encode("phonenumber", "UTF-8")
                    + "=" + URLEncoder.encode(phoneNumber, "UTF-8");
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
        super.onPostExecute(result);
        if (result.startsWith("0")) {
            warning.setText("error");
        } else if (result.startsWith("1")) {
            Intent inte = new Intent(context, AndroidListViewCursorAdaptorActivity.class);
            inte.putExtra("uid", email);
            context.startActivity(inte);
        }


    }
}
