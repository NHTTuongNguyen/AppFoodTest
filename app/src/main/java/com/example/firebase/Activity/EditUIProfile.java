package com.example.firebase.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.firebase.Model.Team;
import com.example.firebase.R;
import com.squareup.picasso.Picasso;

public class EditUIProfile extends AppCompatActivity {
    ImageView img_avatar,img_anhbia;
    TextView tv_nameeditprofile,
             tv_statuseditprofile,
             tv_studenteditprofile,
             tv_liveeditprofile,
             tv_fromeditprofile,
             tv_joninededitprofile;
    String editprofileId= "";

    String imgAvatar = "";
    String imgAnhbia="";
    String txtName="";
    String txtStatus="";
    String txtStudent="";
    String txtLive="";
    String txtForm="";
    String txtJoin="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_uiprofile);
        toolBar();
        img_avatar = findViewById(R.id.img_avatar_editprofile);
        img_anhbia = findViewById(R.id.img_anhbia_editprofile);
        tv_nameeditprofile = findViewById(R.id.tv_name_editprofile);
        tv_statuseditprofile = findViewById(R.id.tv_status_editprofile);
        tv_studenteditprofile = findViewById(R.id.tv_student_editprofile);
        tv_liveeditprofile = findViewById(R.id.tv_live_editprofile);
        tv_fromeditprofile = findViewById(R.id.tv_from_editprofile);
        tv_joninededitprofile = findViewById(R.id.tv_jonined_editprofile);
        getDataInfo();



    }

    private void getDataInfo() {
        Team team = new Team();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("MyTeamId")) {
                team = (Team) intent.getSerializableExtra("MyTeamId");
            }
        }
        imgAvatar = team.getAvatar();
        imgAnhbia = team.getImage();
        txtName = team.getName();
        txtStatus = team.getStatus();
        txtStudent = team.getStudent();
        txtLive = team.getLive();
        txtForm = team.getFrom();
        txtJoin = team.getJonined();
        Picasso.with(this).load(imgAvatar).into(img_avatar);
        Picasso.with(this).load(imgAnhbia).into(img_anhbia);
        tv_nameeditprofile.setText(txtName);
        tv_statuseditprofile.setText(txtStatus);
        tv_studenteditprofile.setText(txtStudent);
        tv_liveeditprofile.setText(txtLive);
        tv_fromeditprofile.setText(txtForm);
        tv_joninededitprofile.setText(txtJoin);


    }


    private void toolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEditUiProfile);
        toolbar.setTitle("Edit Profile");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
