package com.example.firebase.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.Common.Common;
import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.Model.Request;
import com.example.firebase.R;
import com.example.firebase.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class OrderStatus extends AppCompatActivity {
    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, OrderViewHolder>adapter;
    FirebaseDatabase database;

    DatabaseReference requests;
    ///Spiner
    TextView textviewkhongcodulieu;
    MaterialSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        toolBarOfOrder();
        ///Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("RequestsTuongAZ");
        textviewkhongcodulieu = findViewById(R.id.textviewkhongcodulieu);
        recyclerView = findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


            loadOrders(Common.currentUser.getPhone());

    }

    private void toolBarOfOrder() {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_order);
            toolbar.setTitle("My Order");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
//            Intent intent = new Intent(OrderStatus.this, Home.class);
//            startActivity(intent);
//            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("phone")
                        .equalTo(phone)) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, final Request model, final int position) {

                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(Common.convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderName.setText(model.getName());
                viewHolder.txtOrderEmail.setText(model.getEmail());
                viewHolder.txtOrderTotal.setText(model.getTotal());
                viewHolder.txtOrderComments.setText(model.getComments());
                viewHolder.txtOrderDate.setText(Common.getDate(Long.parseLong(adapter.getRef(position).getKey())));
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
//
                        Intent intent = new Intent(OrderStatus.this,OrderDetail.class);
                        Common.currentRequest = model;
                        intent.putExtra("OrderId",adapter.getRef(position).getKey());
                        startActivity(intent);


                    }
                });


                viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderStatus.this);
                        builder.setTitle("Delete Your Order");
                        builder.setMessage("Do you want to delete it ?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int itemposition = position;
                                String key= adapter.getRef(itemposition).getKey();
                                requests.child(key).removeValue();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.show();

                    }
                });

            }
        };
//        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

    }

    private void dialogDeleteItem() {


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Common.UPDATE))
        {
            showUpdateDiaLog(adapter.getRef(item.getOrder()).getKey(), adapter.getItem(item.getOrder()));
        }
        else if (item.getTitle().equals(Common.DELETE))
        {
            showDeleteDiaLog(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);
    }

    public void showDeleteDiaLog(String key) {
        requests.child(key).removeValue();
    }


    private void showUpdateDiaLog(String key, final Request item) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(OrderStatus.this);
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please choose status");

        final LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_order_layout,null);
        spinner = (MaterialSpinner) findViewById(R.id.statusspiner);
        spinner.setItems("Placed","On my way","Shipped");
        alertDialog.setView(view);

        final  String localKey =key;

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                item.setStatus(String.valueOf(spinner.getSelectedIndex()));

                requests.child(localKey).setValue(item);
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();

    }


}
