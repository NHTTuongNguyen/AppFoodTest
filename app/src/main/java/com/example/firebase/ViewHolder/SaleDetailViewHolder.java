package com.example.firebase.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.R;

public  class SaleDetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView name_sale_list,star_sale_list,price_sale_list;
    public ImageView image_sale_list;
    private ItemClickListener itemClickListener;

    public SaleDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        image_sale_list = itemView.findViewById(R.id.image_sale_list);
        name_sale_list = itemView.findViewById(R.id.name_sale_list);
        star_sale_list = itemView.findViewById(R.id.star_sale_list);
        price_sale_list = itemView.findViewById(R.id.price_sale_list);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;

    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
