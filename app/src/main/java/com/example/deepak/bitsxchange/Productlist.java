package com.example.deepak.bitsxchange;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class Productlist extends Activity {

    RecyclerView myrecycler;
    Button sell;
    EditText myFilter;
    ProductAdapter adapter;
    RequestParams params;
    String uid;

    ArrayList<Product> prarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productlist);
        Bundle extra = getIntent().getExtras();
        uid = extra.getString("uid");
        myrecycler = (RecyclerView) findViewById(R.id.recyclerView);

        sell = (Button) findViewById(R.id.sellbutton);
        myFilter = (EditText) findViewById(R.id.myFilter);
        prarr = new ArrayList<Product>();
        params = new RequestParams();
        prarr = new ArrayList<Product>();

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context cont = getApplicationContext();
                Intent startselling = new Intent(cont, upload.class);
                startselling.putExtra("uid",uid);
                startActivity(startselling);
            }
        });

        makeHTTPCall(" ");



        myrecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_productlist, menu);
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


    public void makeHTTPCall(String arg) {

        AsyncHttpClient client = new AsyncHttpClient();
        // Don't forget to change the IP address to your LAN address. Port no as well.
        params.put("filter",arg);
        client.post("http://bitsxchange.net84.net//querydatabase.php",
                params, new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http
                    // response code '200'
                    @Override
                    public void onSuccess(String response) {
                        // Hide Progress Dialog
                        // Context context = getApplicationContext();
                        try {

                            JSONArray result = new JSONArray(response);
                            for(int i=0;i<result.length();i++){
                                String name = result.getJSONObject(i).getString("pname").toString();
                                String price = result.getJSONObject(i).getString("price").toString();
                                String pid = result.getJSONObject(i).getString("pid").toString();
                                String filename = result.getJSONObject(i).getString("filename").toString();
                                String uid = result.getJSONObject(i).getString("uid").toString();
                                String description = result.getJSONObject(i).getString("description").toString();
                                prarr.add(new Product(name,price,pid,uid,description,filename));

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        adapter = new ProductAdapter(getApplicationContext(),prarr);
                        myrecycler.setAdapter(adapter);
                    }

                    // When the response returned by REST has Http
                    // response code other than '200' such as '404',
                    // '500' or '403' etc
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        // When Http response code is '404'
                        if (statusCode == 404) {
                            Toast.makeText(getApplicationContext(),
                                    "Requested resource not found",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code is '500'
                        else if (statusCode == 500) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong at server end",
                                    Toast.LENGTH_LONG).show();
                        }
                        // When Http response code other than 404, 500
                        else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Error Occured \n Most Common Error: \n1. Device not connected to Internet\n2. Web App is not deployed in App server\n3. App server is not running\n HTTP Status code : "
                                            + statusCode, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
        return;
    }
}
