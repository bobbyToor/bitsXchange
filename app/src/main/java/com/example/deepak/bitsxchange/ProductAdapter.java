package com.example.deepak.bitsxchange;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deepak.bitsxchange.Product;
import com.example.deepak.bitsxchange.R;

import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.content.ContextCompat.startActivities;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {
    private Context context;
    private List<Product> productList;


    public ProductAdapter(Context context,List<Product> productList) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public void onBindViewHolder(ProductHolder contactViewHolder, int i) {
        Product ci = productList.get(i);
        final int pos = i;
        contactViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toresult = new Intent(context,result.class);
                toresult.putExtra("title",productList.get(pos).name);
                toresult.putExtra("price",productList.get(pos).price);
                toresult.putExtra("description",productList.get(pos).description);
                toresult.putExtra("filename",productList.get(pos).filename);
                toresult.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(toresult);
            }
        });
        contactViewHolder.title.setText(ci.name);
        contactViewHolder.price.setText(ci.price);
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.my_custom_row, viewGroup, false);


        return new ProductHolder(itemView);
    }


    public static class ProductHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView price;


        public ProductHolder(View v) {
            super(v);

            title =  (TextView) v.findViewById(R.id.title);
            price = (TextView)  v.findViewById(R.id.price);
        }
    }
}