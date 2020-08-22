package com.example.firebase.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.Model.Food;
import com.example.firebase.R;

import java.util.ArrayList;

public class FoodViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener {

    public TextView food_name,food_price,food_share_facebook,food_favorite;
    public ImageView food_image,share_img,img_favorite,img_buy_cart_food;
    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        img_buy_cart_food = itemView.findViewById(R.id.img_buy_cart_food);
        img_favorite = itemView.findViewById(R.id.img_favorite);
        food_share_facebook = itemView.findViewById(R.id.tv_share_facebook);
        food_name=itemView.findViewById(R.id.food_list_name);
        food_image = itemView.findViewById(R.id.food_list_image);
        share_img = itemView.findViewById(R.id.btnShare);
        food_price = itemView.findViewById(R.id.food_list_price);
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

    }
    public void setFilter(ArrayList<Food>newList){
//        storyArrayList = new ArrayList<>();
//        storyArrayList.addAll(newList);
//        notifyDataSetChanged();
    }
}
