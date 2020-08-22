package com.example.firebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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

import io.paperdb.Paper;

public class Login extends AppCompatActivity {
    EditText et_name,et_email,et_password,et_cpassword,edtPhone;
    Button bt_login;
    CheckBox checkBox;
    ImageView img_register;
    private String phone,name,password;
    TextView tv_title;
    ImageButton btRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkBox = findViewById(R.id.checkbox);

        // change color for status b


        //// init checkbox
        Paper.init(this);

        img_register = findViewById(R.id.link_signup);
        img_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });
        TextView textView = findViewById(R.id.tv_register_now);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });
        et_password = findViewById(R.id.editText5);
        bt_login = findViewById(R.id.button3);
        edtPhone=findViewById(R.id.editTextphone);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("UserTuongAZ");
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// Save user & Password
                if (Common.isConnectedtoInternet(getBaseContext())) {
                    if (checkBox.isChecked()){
                        Paper.book().write(Common.USER_KEY,edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY,et_password.getText().toString());
                    }
                    String email = edtPhone.getText().toString();
                    final String password = et_password.getText().toString();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Your email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(password)) {
                        Toast.makeText(getApplicationContext(), "Please Enter Your Password!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //check if user  not exist  in database


                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                user.setPhone(edtPhone.getText().toString());
                                if (user.getPassword().equals(et_password.getText().toString())) {
                                    Intent intent = new Intent(Login.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(intent);
                                    finish();

//                                    table_user.removeEventListener(this);
                                    Toast.makeText(Login.this, "Log in successfull !!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Login.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Login.this, "User is not register", Toast.LENGTH_SHORT).show();
                            }


                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    Toast.makeText(Login.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                }
            }

        });



    }








}
