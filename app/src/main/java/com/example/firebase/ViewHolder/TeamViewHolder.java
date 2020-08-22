package com.example.firebase.ViewHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.R;

public class TeamViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tv_name_team;
    public TextView tv_vitri;
    public ImageView img_profile;

    private ItemClickListener itemClickListener;
    public TeamViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_name_team =itemView.findViewById(R.id.tv_name_team);
        tv_vitri = itemView.findViewById(R.id.tv_vitri);
        img_profile = itemView.findViewById(R.id.profileImage);
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
