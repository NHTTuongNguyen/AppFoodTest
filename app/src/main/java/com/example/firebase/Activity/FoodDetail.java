package com.example.firebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.andremion.counterfab.CounterFab;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.firebase.Common.Common;
import com.example.firebase.Database.Database;
import com.example.firebase.Model.CategoryOther;
import com.example.firebase.Model.Food;
import com.example.firebase.Model.Order;
import com.example.firebase.Model.Rating;
import com.example.firebase.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class FoodDetail extends AppCompatActivity implements RatingDialogListener {

    TextView tv_name,tv_price,tv_description;
    ImageView food_image,img_feedback;
    CollapsingToolbarLayout collapsingToolbarLayout;
    CounterFab counterFab_Cart;
    ElegantNumberButton numberButton;
    String foodId = null;
    String categoryId = null;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference_CategoryId;
    Food   currentFood;
    CategoryOther current_CategoryOther;
    Button button_buy;
    DatabaseReference ratingTb1;
    RatingBar ratingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        tv_name = findViewById(R.id.txt_Food_Detail_Name);
        tv_price = findViewById(R.id.txt_Food_Detail_Price);
        tv_description = findViewById(R.id.txt_Food_Detail_Des);
        food_image = findViewById(R.id.img_Food_Detail_img);
        img_feedback = findViewById(R.id.image_feelback_other);
        button_buy = findViewById(R.id.button_buy_Food_Detail);
        numberButton = findViewById(R.id.number_Food_Detail);
        counterFab_Cart = findViewById(R.id.fab_Cart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("FoodTuongAZ");
        databaseReference_CategoryId = firebaseDatabase.getReference("CategoryOtherTuongAZ");
        ratingTb1 = firebaseDatabase.getReference("RatingTuongAZ");
        img_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });
        ratingBar = findViewById(R.id.ratingFood);
        counterFab_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FoodDetail.this,Cart.class);
                startActivity(i);
            }
        });
        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount(),
                        currentFood.getImage()
                ));
                finish();
                Toast.makeText(FoodDetail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
//
        counterFab_Cart.setCount(new Database(this).getCountCart());
        TextView tv_feedback_name = findViewById(R.id.tv_feedback_name_food_detail);
        TextView tv_feedback_phone = findViewById(R.id.tv_feedback_phone_food_detail);
        TextView tv_feedback_email = findViewById(R.id.tv_feedback_email_food_detail);
        tv_feedback_name.setText(Common.currentUser.getName());
        tv_feedback_phone.setText(Common.currentUser.getPhone());
        tv_feedback_email.setText(Common.currentUser.getEmail());


        ///get car id from intent
        if (getIntent()!= null)
            foodId = getIntent().getStringExtra("FoodId");

        if (foodId!= null && !foodId.isEmpty()){
            if (Common.isConnectedtoInternet(getBaseContext())) {
                loadFoodDetail(foodId);
                getRatingFood(foodId);
            }
            else {
                Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        }
        if (getIntent()!= null)
            categoryId = getIntent().getStringExtra("CategoryOtherId");

        if (categoryId!= null && !categoryId.isEmpty()){
            if (Common.isConnectedtoInternet(getBaseContext())) {
                loadFoodCategoryId(categoryId);
//                getRatingFood(categoryId);
            }
            else {
                Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        }


    }



    private void loadFoodDetail(String foodId) {
        databaseReference.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentFood.getImage())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.imgerror)
                        .into(food_image);
                tv_price.setText(currentFood.getPrice());
                tv_name.setText(currentFood.getName());
                tv_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadFoodCategoryId(String categoryId) {
        databaseReference_CategoryId.child(categoryId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                current_CategoryOther = dataSnapshot.getValue(CategoryOther.class);
                Picasso.with(getBaseContext()).load(current_CategoryOther.getImage())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.error)
                        .into(food_image);
                tv_price.setText(current_CategoryOther.getPrice());
                tv_name.setText(current_CategoryOther.getName());
                tv_description.setText(current_CategoryOther.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getRatingFood(String foodId) {
       Query foodRating = ratingTb1.orderByChild("foodId").equalTo(foodId);
        foodRating.addValueEventListener(new ValueEventListener() {
            int count = 0,sum = 0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Rating item = postSnapshot.getValue(Rating.class);
                    sum += Integer.parseInt(item.getRateValue());
                    count++;
                }
                if (count != 0){
                    float average = sum/count;
                    ratingBar.setRating(average);
                }


//                for(DataSnapshot ds :dataSnapshot.getChildren()){
//                    Rating item = ds.getValue(Rating.class);
//                    sum = sum + Integer.parseInt(item.getRateValue());
//                    count++;
//
//                }
//                if(count != 0){
//
//                    ratingBar.setRating(sum/count);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    //// Feed back ////
    private void showRatingDialog() {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel")
                .setNoteDescriptions(Arrays.asList("Very Bad", "Not Good", "Quite Ok", "Very Good", "Excelent"))
                .setTitle("Rate this product")
                .setDefaultRating(1)
                .setDescription("Give your feeback and rating star !")
                .setTitleTextColor(R.color.colorPrimary)
                .setHint("Please write your comment here...")
                .setHintTextColor(R.color.white)
                .setDescriptionTextColor(R.color.colorPrimary)
                .setCommentTextColor(R.color.white)
                .setCommentBackgroundColor(R.color.colorPrimary)
                .setWindowAnimation(R.style.RatingDialogFadeAnim)
                .create(FoodDetail.this)
                .show();
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String comments) {
        /// get rating and upload to firebase
        final Rating rating = new Rating(Common.currentUser.getPhone(),
                foodId,
                String.valueOf(value),
                comments);
        ratingTb1.child(Common.currentUser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Common.currentUser.getPhone()).child(foodId).exists())
                {
                    //Remove old value
                    ratingTb1.child(Common.currentUser.getPhone()).child(foodId).removeValue();
                    ///Update new value
                    ratingTb1.child(Common.currentUser.getPhone()).child(foodId).setValue(rating);
                    finish();
                }
                else {
                    /// Update new value
                    ratingTb1.child(Common.currentUser.getPhone()).child(foodId).setValue(rating);
                    Toast.makeText(FoodDetail.this, "Thank you for submit rating", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
