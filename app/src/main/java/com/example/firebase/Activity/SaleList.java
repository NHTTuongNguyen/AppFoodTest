package com.example.firebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.Common.Common;
import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.Model.Sale;
import com.example.firebase.R;
import com.example.firebase.ViewHolder.SaleDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SaleList extends AppCompatActivity {
    DatabaseReference databaseReference,databaseReference_Sale;
    FirebaseDatabase firebaseDatabase;
    FirebaseRecyclerAdapter<Sale, SaleDetailViewHolder>adapter;
    String saleId = "";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Sale currentSale;
    TextView txt_food_Sale;
    ImageView img_food_Sale;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_list);

        floatingActionButton = findViewById(R.id.fab_addlist_sale);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleList.this,Cart.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recycler_SaleList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SaleDetailTuongAZ");


        if (getIntent() != null) {
            saleId = getIntent().getStringExtra("SaleId");
        }
        if (!saleId.isEmpty() && saleId!= null){
            if (Common.isConnectedtoInternet(getBaseContext())) {
                loadListSaleDetail(saleId);
            }
            else {
                Toast.makeText(getBaseContext(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        }

    }



    private void loadListSaleDetail(final String saleId) {
        adapter = new FirebaseRecyclerAdapter<Sale, SaleDetailViewHolder>
                (Sale.class,R.layout.item_sale_list, SaleDetailViewHolder.class, databaseReference.orderByChild("menuId").equalTo(saleId)) {
            @Override
            protected void populateViewHolder(SaleDetailViewHolder saleDetailViewHolder, Sale sale, final int i) {
                saleDetailViewHolder.name_sale_list.setText(sale.getName());
                saleDetailViewHolder.star_sale_list.setText(sale.getStar());
                saleDetailViewHolder.price_sale_list.setText(String.format("$ %s",sale.getPrice()));
                Picasso.with(getBaseContext())
                        .load(sale.getImage())
                        .into(saleDetailViewHolder.image_sale_list);


                saleDetailViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent intent = new Intent(SaleList.this,SaleDetail.class);
                        intent.putExtra("SaleDetailId",adapter.getRef(i).getKey());
                        startActivity(intent);
                    }
                });

            }

        };
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
