package com.example.deepak.bitsxchange;

/**
 * Created by Deepak on 14-02-2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;

public class AndroidListViewCursorAdaptorActivity extends Activity {

    private ProductsDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private Button sell;
    private String uid;
    Product prarr[];
    RequestParams params = new RequestParams();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_list_view_cursor_adaptor);
        sell = (Button) findViewById(R.id.sellbutton);
        Bundle extra = getIntent().getExtras();
        uid = extra.getString("uid");
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context cont = getApplicationContext();
                Intent startselling = new Intent(cont, upload.class);
                startselling.putExtra("uid", uid);
                startActivity(startselling);
            }
        });
        dbHelper = new ProductsDbAdapter(this);
        dbHelper.open();

        //Clean all data
        //dbHelper.deleteAllProducts();
        //Add some data
        makeHTTPCall(" ");

        //Generate ListView from SQLite Database
        //displayListView();

    }

    private void displayListView() {


        Cursor cursor = dbHelper.fetchAllProducts();

        // The desired columns to be bound
        String[] columns = new String[]{
                ProductsDbAdapter.KEY_NAME,
                ProductsDbAdapter.KEY_PRICE,
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.name,
                R.id.price,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.product_info,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                Intent toresult = new Intent(getApplicationContext(),result.class);
                toresult.putExtra("title",prarr[position].name);
                toresult.putExtra("price",prarr[position].price);
                toresult.putExtra("description",prarr[position].description);
                toresult.putExtra("filename",prarr[position].filename);
                startActivity(toresult);
/*
                String productPrice =
                        cursor.getString(cursor.getColumnIndexOrThrow("price"));
                Toast.makeText(getApplicationContext(),
                        productPrice, Toast.LENGTH_SHORT).show();*/

            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchProductsByName(constraint.toString());
            }
        });

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
                            dbHelper.deleteAllProducts();
                            JSONArray result = new JSONArray(response);
                            prarr = new Product[result.length()];
                            for(int i=0;i<result.length();i++){
                                String name = result.getJSONObject(i).getString("pname").toString();
                                String price = result.getJSONObject(i).getString("price").toString();
                                String pid = result.getJSONObject(i).getString("pid").toString();
                                String filename = result.getJSONObject(i).getString("filename").toString();
                                String uid = result.getJSONObject(i).getString("uid").toString();
                                String description = result.getJSONObject(i).getString("description").toString();
                                prarr[i] = new Product(name,price,pid,uid,description,filename);
                                dbHelper.createProduct(name,price,pid);
                            }
                            displayListView();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
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
    }
}