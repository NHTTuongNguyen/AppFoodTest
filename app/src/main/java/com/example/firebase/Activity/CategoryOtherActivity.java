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

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.firebase.Common.Common;
import com.example.firebase.Database.Database;
import com.example.firebase.Model.CategoryOther;
import com.example.firebase.Model.Order;
import com.example.firebase.Model.Rating;
import com.example.firebase.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class CategoryOtherActivity extends AppCompatActivity implements RatingDialogListener {

    TextView categoryOther_Name,categoryOther_Price,categoryOther_Des;
    ImageView categoryOther_Image;
    ImageView img_feedback;
    TextView tv_feedback_name,tv_feedback_email,tv_feedback_phone;
    FirebaseDatabase firebaseDatabase,firebaseDatabase_Rating;
    DatabaseReference databaseReference;
    String categoryOtherId= "";
    CategoryOther categoryOther;
    Button button_buy,btn_ShowComments;
    ElegantNumberButton numberButton;
    DatabaseReference ratingTb1;
    RatingBar ratingBar;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_other);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("CategoryOtherTuongAZ");
        ratingTb1 = firebaseDatabase.getReference("RatingTuongAZ");
        tv_feedback_name = findViewById(R.id.tv_feedback_name);
        tv_feedback_email = findViewById(R.id.tv_feedback_email);
        tv_feedback_phone = findViewById(R.id.tv_feedback_phone);
        tv_feedback_name.setText(Common.currentUser.getName());
        tv_feedback_email.setText(Common.currentUser.getEmail());
        tv_feedback_phone.setText(Common.currentUser.getPhone());
        categoryOther_Name = findViewById(R.id.categoryOther_Name);
        categoryOther_Des = findViewById(R.id.categoryOther_Description);
        categoryOther_Price = findViewById(R.id.categoryOther_Price);
        categoryOther_Image = findViewById(R.id.categoryOther_Image);
        numberButton = findViewById(R.id.number_button_other);
        ratingBar = findViewById(R.id.ratingother);

        floatingActionButton = findViewById(R.id.fab_CategoryOther);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryOtherActivity.this,Cart.class);
                startActivity(intent);
            }
        });

        button_buy = findViewById(R.id.button_buy);
        button_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        categoryOtherId,
                        categoryOther.getName(),
                        numberButton.getNumber(),
                        categoryOther.getPrice(),
                        categoryOther.getDiscount(),
                        categoryOther.getImage()
                ));
                Toast.makeText(CategoryOtherActivity.this, "Add to Cart", Toast.LENGTH_SHORT).show();
            }
        });

        img_feedback = findViewById(R.id.image_feelback_other);
        img_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRatingDialog();
            }
        });
        if (getIntent() != null ) {
            categoryOtherId = getIntent().getStringExtra("CategoryOtherId");
        }

        if (!categoryOtherId.isEmpty() && categoryOtherId!= null ) {
            if (Common.isConnectedtoInternet(getBaseContext())) {
                getDetailCategoryOther(categoryOtherId);
                getRatingCategoryOther(categoryOtherId);
            }
            else {
                Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getRatingCategoryOther(String categoryOtherId) {
        ///// carsId get  star from firebase
        Query query = ratingTb1.orderByChild("foodId").equalTo(categoryOtherId);
        query.addValueEventListener(new ValueEventListener() {
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

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
                .create(CategoryOtherActivity.this)
                .show();
    }


    private void getDetailCategoryOther(String categoryOtherId) {
        databaseReference.child(categoryOtherId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
                categoryOther = dataSnapshot.getValue(CategoryOther.class);
                Picasso.with(getBaseContext()).load(categoryOther.getImage())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.error)
                        .into(categoryOther_Image);
                categoryOther_Des.setText(categoryOther.getDescription());
                categoryOther_Name.setText(categoryOther.getName());
                categoryOther_Price.setText(categoryOther.getPrice());

//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String comments) {

//        /get ratting and upload  to firebase
        final Rating rating  = new Rating(Common.currentUser.getPhone(),
                categoryOtherId,
                String.valueOf(value),
                comments);
//            ratingTb1.push()
//                .setValue(rating)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(CategoryOtherActivity.this, "Thank you for submit rating", Toast.LENGTH_SHORT).show();
//                    }
//                });

        ratingTb1.child(Common.currentUser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Common.currentUser.getPhone()).exists())
                {
                    //Remove old value
                    ratingTb1.child(Common.currentUser.getPhone()).removeValue();
                    ///Update new value
                    ratingTb1.child(Common.currentUser.getPhone()).setValue(rating);
                }
                else {
                    /// Update new value
                    ratingTb1.child(Common.currentUser.getPhone()).setValue(rating);
                }
                Toast.makeText(CategoryOtherActivity.this, "Thank you for submit rating", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}


