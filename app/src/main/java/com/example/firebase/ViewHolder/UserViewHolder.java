package com.example.firebase.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.R;

public class UserViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtName_customer,txtPaswword_customer,txtPhone_customer,txtEmail_customer;
    private ItemClickListener itemClickListener;
    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName_customer = itemView.findViewById(R.id.txtName_customer);
        txtPaswword_customer = itemView.findViewById(R.id.txtPaswword_customer);
        txtPhone_customer = itemView.findViewById(R.id.txtPhone_customer);
        txtEmail_customer = itemView.findViewById(R.id.txtEmail_customer);
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
