package com.example.firebase.ViewHolder;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.firebase.Model.Order;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailViewHolder> {

    List<Order> myOrders = new ArrayList<>();
    private Context context;


    public OrderDetailAdapter(List<Order> myOrders) {
        this.myOrders = myOrders;
    }

    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_detail_layout,parent,false);
        return  new OrderDetailViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {

        Order order  = myOrders.get(position);

        Picasso.with(context)
                .load(myOrders.get(position).getImage())
                .placeholder(R.drawable.imgerror)
                .error(R.drawable.error)
                .into(holder.img);
        holder.name.setText(String.format("Name : %s",order.getProductName()));
        holder.price.setText(String.format("Price : %s",order.getPrice()));
        holder.discount.setText(String.format("Discount : %s",order.getDiscount()));
        holder.quantity.setText(String.format("Quantity : %s",order.getQuantity()));

//        holder.name.setText(String.format(order.getProductName()));
//        holder.price.setText(String.format(order.getPrice()));
//        holder.discount.setText(String.format(order.getDiscount()));
//        holder.quantity.setText(String.format(order.getQuantity()));


    }

    @Override
    public int getItemCount() {
        return myOrders.size();
    }
}
