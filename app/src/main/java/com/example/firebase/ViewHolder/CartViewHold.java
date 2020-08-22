package com.example.firebase.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.firebase.Common.Common;
import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.R;

public class CartViewHold extends RecyclerView.ViewHolder implements
        View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView txt_car_name,txt_price;
    public ImageView img_cart_count,img_delete_cart,img_info;
    public ElegantNumberButton btn_quantity;
    private ItemClickListener itemClickListener;

    public RelativeLayout view_background;
    public LinearLayout view_foreground;


    public void setTxt_car_name(TextView txt_car_name) {
        this.txt_car_name = txt_car_name;
    }

    public CartViewHold(@NonNull View itemView) {
        super(itemView);
        img_info = itemView.findViewById(R.id.img_infocart);
        img_delete_cart = itemView.findViewById(R.id.delete_cart);
        txt_car_name = itemView.findViewById(R.id.cart_item_name);
        txt_price = itemView.findViewById(R.id.cart_item_price);
        img_cart_count =itemView.findViewById(R.id.cart_item_count);

        view_foreground = itemView.findViewById(R.id.view_foreground);
        itemView.setOnCreateContextMenuListener(this);


    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        ///
        menu.setHeaderTitle("Select ation");
        menu.add(0,0,getAdapterPosition(), Common.DELETE);

    }
}