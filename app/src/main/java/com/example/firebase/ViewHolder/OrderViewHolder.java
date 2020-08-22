package com.example.firebase.ViewHolder;

import android.content.Context;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.Common.Common;
import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener
 {
     Context context;
    public TextView txtOrderId,txtOrderStatus,
            txtOrderPhone,txtOrderAddress,
            txtOrderName,
            txtOrderComments,
            txtOrderTotal,txtOrderDate,
            txtOrderEmail;
    private ItemClickListener itemClickListener;

    public ImageView imageViewDelete;

     public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderComments = itemView.findViewById(R.id.order_comments);
        txtOrderTotal = itemView.findViewById(R.id.order_total_food);
        txtOrderId= itemView.findViewById(R.id.order_id);
        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderAddress = itemView.findViewById(R.id.order_address);
        txtOrderName = itemView.findViewById(R.id.order_name);
        txtOrderEmail = itemView.findViewById(R.id.order_email);
        txtOrderDate = itemView.findViewById(R.id.order_time_food);
         imageViewDelete = itemView.findViewById(R.id.imageViewDelete);

         itemView.setOnCreateContextMenuListener(this);
        itemView.setOnClickListener(this);




    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select  the action");
//        menu.add(0,1,getAdapterPosition(), Common.UPDATE);
        menu.add(0,0,getAdapterPosition(),Common.DELETE);
    }
}
