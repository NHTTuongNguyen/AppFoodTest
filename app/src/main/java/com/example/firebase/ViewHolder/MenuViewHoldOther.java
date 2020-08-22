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

public class MenuViewHoldOther extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener {

    public TextView tv_name_category_other,tv_star_category_other,tv_price_category_other;
    public ImageView image_category_other;
    private ItemClickListener itemClickListener;

    public MenuViewHoldOther(@NonNull View itemView) {
        super(itemView);

        tv_name_category_other=itemView.findViewById(R.id.tv_name_category_other);
        tv_star_category_other = itemView.findViewById(R.id.tv_star_category_other);
        tv_price_category_other = itemView.findViewById(R.id.tv_price_category_other);
        image_category_other = itemView.findViewById(R.id.image_category_other);
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
