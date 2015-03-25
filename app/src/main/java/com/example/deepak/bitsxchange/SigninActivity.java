package com.example.deepak.bitsxchange;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

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
 * Created by Deepak on 10-02-2015.
 */
public class SigninActivity extends AsyncTask<String, Void, String> {
    private Context context;
    EditText emaill,pass;

    public SigninActivity(Context context, EditText email,EditText pass) {
        this.context = context;
        this.emaill = email;
        this.pass = pass;
    }

    String email;

    @Override
    protected String doInBackground(String... arg0) {
        try {
            email = (String) arg0[0];
            String password = (String) arg0[1];
            String link = "http://bitsxchange.net84.net/login.php";
            String data = URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
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
        if (result.startsWith("1")) {
            Intent inte = new Intent(context, Productlist.class);
            inte.putExtra("uid", email);
            context.startActivity(inte);
        } else if (result.startsWith("0")) {

            Toast.makeText(context, "Invalid email or password.", Toast.LENGTH_LONG).show();

            YoYo.with(Techniques.Shake).duration(700).playOn(emaill);
            YoYo.with(Techniques.Shake).duration(700).playOn(pass);
        }
    }
}
