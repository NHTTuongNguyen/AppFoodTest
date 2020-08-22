package com.example.firebase.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firebase.R;

public class TestEmpty extends AppCompatActivity {

    EditText et_name,et_email,et_password;
    Button bt_confirm;
    private String email,name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_empty);
        et_name= findViewById(R.id.edittext_user2);
        et_password = findViewById(R.id.edittext_password2);
        et_email = findViewById(R.id.edittext_email2);
        bt_confirm= findViewById(R.id.button_confirm2);

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conFirm();
            }
        });


    }

    private void conFirm() {
        name = et_name.getText().toString().trim();
        email= et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (!validate())
        {
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
        }
        else {
            Success();
        }
    }
    private void Success() {
    }
    private boolean validate(){
        boolean valid  =true;
        if (name.isEmpty()||name.length()>32)
        {
            et_name.setError("Please Enter Your Name");
            valid = false;
        }
        if (email.isEmpty()|| Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            et_email.setError("Please Enter Your Email");
            valid = false;
        }
        if (password.isEmpty())
        {
            et_password.setError("Please Enter Your Password");
            valid = false;
        }
        return valid;
    }
}
