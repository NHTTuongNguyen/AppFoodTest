package com.example.firebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.Common.Common;
import com.example.firebase.Model.User;
import com.example.firebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {
    EditText et_name,et_password,et_phone,et_email;
    Button bt_register;
    ImageView imageView_Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        et_email = findViewById(R.id.editTextEmail);

        et_name= findViewById(R.id.editTextname);
        et_password= findViewById(R.id.editText2);
        bt_register= findViewById(R.id.button);
        et_phone=findViewById(R.id.editText);
        imageView_Register = findViewById(R.id.img_register);
        imageView_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this,Login.class);
                startActivity(i);
            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("UserTuongAZ");
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Common.isConnectedtoInternet(getBaseContext())) {
                    String name = et_name.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    String phone = et_phone.getText().toString().trim();
                    String email = et_email.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                        return;
                    }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(Register.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                        return ;
                    }

                    if (TextUtils.isEmpty(phone)) {
                        Toast.makeText(getApplicationContext(), "Enter  your phone", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (phone.length()<10){
                        Toast.makeText(Register.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(name)) {
                        Toast.makeText(getApplicationContext(), "Enter  your email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Enter your password!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(et_phone.getText().toString()).exists()) {
                                Toast.makeText(Register.this, "User was already registered", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                User user = new User(et_name.getText().toString(), et_password.getText().toString(), et_email.getText().toString());

                                table_user.child(et_phone.getText().toString()).setValue(user);
                                Toast.makeText(Register.this, "Register successfully !!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(Register.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


}
