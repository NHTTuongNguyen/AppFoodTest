package com.example.firebase.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebase.Common.Common;
import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.R;

public class MenuViewHolder2 extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener {

    public TextView txtMenuName2,tv_sale;
    public ImageView imgMenuImage2;
    private ItemClickListener itemClickListener;



    public MenuViewHolder2(@NonNull View itemView) {
        super(itemView);
        tv_sale = itemView.findViewById(R.id.tv_sale);
        txtMenuName2 = itemView.findViewById(R.id.menu_name2);
        imgMenuImage2 = itemView.findViewById(R.id.menu_image2);

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
//        menu.setHeaderTitle("Select  the action");
//        menu.add(0,1,getAdapterPosition(), Common.UPDATE);
//        menu.add(0,0,getAdapterPosition(),Common.DELETE);
    }
}
