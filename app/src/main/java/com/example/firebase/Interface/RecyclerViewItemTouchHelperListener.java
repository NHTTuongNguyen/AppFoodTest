package com.example.firebase.Interface;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerViewItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder,int direction, int position);
}
