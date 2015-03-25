package com.example.deepak.bitsxchange;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class result extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);

        Bundle extra = getIntent().getExtras();
        final String res = extra.getString("filename");
        String titletext = extra.getString("title");
        String pricetext = extra.getString("price");
        String descriptiontext = extra.getString("description");

        final ImageView img = (ImageView) findViewById(R.id.photo);
        TextView title = (TextView) findViewById(R.id.title);
        TextView price = (TextView) findViewById(R.id.price);
        TextView description = (TextView) findViewById(R.id.description);

        title.setText("Title : " + titletext);
        price.setText("Price : " + pricetext);
        description.setText("Description : " + descriptiontext);

        Picasso.with(getApplicationContext()).load("http://bitsxchange.net84.net/images/" + res).into(img);


        /*Thread t = new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    URL url = new URL("http://bitsxchange.net84.net/images/" + res);
                    //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
                    HttpGet httpRequest = null;

                    httpRequest = new HttpGet(url.toURI());

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = (HttpResponse) httpclient
                            .execute(httpRequest);

                    HttpEntity entity = response.getEntity();
                    BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
                    InputStream input = b_entity.getContent();

                    Bitmap bitmap = BitmapFactory.decodeStream(input);

                    img.setImageBitmap(bitmap);

                } catch (Exception ex) {


                }


            }

            };
        t.start();
        }*/



       /* new AsyncTask<Void, Void, Bitmap>() {
            protected Bitmap doInBackground(Void... p) {
                Bitmap bm = null;
                try {
                    URL aURL = new URL("http://bitsxchange.net84.net/images/" + res);
                    URLConnection conn = aURL.openConnection();
                    conn.setUseCaches(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    BufferedInputStream bis = new BufferedInputStream(is);
                    bm = BitmapFactory.decodeStream(bis);
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bm;
            }

            protected void onPostExecute(Bitmap bm) {

                img.setImageBitmap(bm);

            }


        }.execute();*/

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
