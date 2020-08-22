package com.example.firebase.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.R;

class OrderDetailViewHolder extends RecyclerView.ViewHolder{
    public TextView name,quantity,price,discount;
    public ImageView img;


    public OrderDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.product_name);
        img = itemView.findViewById(R.id.product_image);
        quantity = itemView.findViewById(R.id.product_quantity);
        discount = itemView.findViewById(R.id.product_discount);
        price = itemView.findViewById(R.id.product_price);


    }



}
