package com.example.firebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.firebase.Common.Common;
import com.example.firebase.Database.Database;
import com.example.firebase.Model.Order;
import com.example.firebase.Model.Sale;
import com.example.firebase.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SaleDetail extends AppCompatActivity {
    ImageView img_Sale_Detail_img;

    TextView txt_Sale_Detail_Name,txt_Sale_Detail_Price,txt_Sale_Detail_Des;
    FloatingActionButton floatingActionButton;
    Button button_buy_Sale_Detail;
    ElegantNumberButton number_Sale_Detail;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String saleDetailId ="";

    Sale currentSale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);
        controller();

        firebaseDatabase =  FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("SaleDetailTuongAZ");

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SaleDetail.this,Cart.class);
                startActivity(intent);
            }
        });
        if (getIntent()!= null)
            saleDetailId = getIntent().getStringExtra("SaleDetailId");

        if (!saleDetailId.isEmpty() && saleDetailId!= null ){
            if (Common.isConnectedtoInternet(getBaseContext())) {
                loadSaleDetail(saleDetailId);
//                getRatingFood(foodId);
            }
            else {
                Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        }
        button_buy_Sale_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        saleDetailId,
                        currentSale.getName(),
                        number_Sale_Detail.getNumber(),
                        currentSale.getPrice(),
                        currentSale.getDiscount(),
                        currentSale.getImage()

                ));
                Toast.makeText(SaleDetail.this, "Add To Cart", Toast.LENGTH_SHORT).show();
            }
        });

//        TextView tv_feedback_name = findViewById(R.id.tv_feedback_name_sale_detail);
//        TextView tv_feedback_email = findViewById(R.id.tv_feedback_email_sale_detail);
//        TextView tv_feedback_phone = findViewById(R.id.tv_feedback_phone_sale_detail);
//
//        ///////////////////
//
//        tv_feedback_email.setText(Common.currentUser.getEmail());
//        tv_feedback_name.setText(Common.currentUser.getName());
//        tv_feedback_phone.setText(Common.currentUser.getPhone());
}

    private void loadSaleDetail(String saleDetailId) {
        databaseReference.child(saleDetailId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentSale = dataSnapshot.getValue(Sale.class);

                Picasso.with(getBaseContext()).load(currentSale.getImage())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.imgerror)
                        .into(img_Sale_Detail_img);
                txt_Sale_Detail_Name.setText(currentSale.getName());
                txt_Sale_Detail_Price.setText(currentSale.getPrice());
                txt_Sale_Detail_Des.setText(currentSale.getDescription());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void controller() {
        img_Sale_Detail_img = findViewById(R.id.img_Sale_Detail_img);
        floatingActionButton = findViewById(R.id.fab_Cart_SaleDetail);
        txt_Sale_Detail_Name = findViewById(R.id.txt_Sale_Detail_Name);
        txt_Sale_Detail_Price = findViewById(R.id.txt_Sale_Detail_Price);
        number_Sale_Detail = findViewById(R.id.number_Sale_Detail);
        button_buy_Sale_Detail = findViewById(R.id.button_buy_Sale_Detail);
        txt_Sale_Detail_Des = findViewById(R.id.txt_Sale_Detail_Des);
    }
}
