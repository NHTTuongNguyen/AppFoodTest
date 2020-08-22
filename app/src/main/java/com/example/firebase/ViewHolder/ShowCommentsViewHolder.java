package com.example.firebase.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.R;

public class ShowCommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtPhone,txtName,txtComments;
        public ImageView imgComments;
        public RatingBar ratingBar;
    public ShowCommentsViewHolder(@NonNull View itemView) {
        super(itemView);
        txtPhone = itemView.findViewById(R.id.txtUserPhone);
        txtName = itemView.findViewById(R.id.tv_name_comments);
        txtComments = itemView.findViewById(R.id.tv_comments);
        imgComments = itemView.findViewById(R.id.profileImage_comments);
        ratingBar = itemView.findViewById(R.id.ratting_bar_comments);
    }

    @Override
    public void onClick(View v) {

    }
}
