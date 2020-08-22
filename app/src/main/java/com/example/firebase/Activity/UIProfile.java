package com.example.firebase.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firebase.Model.Team;
import com.example.firebase.R;
import com.example.firebase.ViewHolder.TeamViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UIProfile extends AppCompatActivity {
    ImageView img_anhbia,img_avatar,img_more;
    TextView txt_name,txt_status,txt_student,txt_live,txt_from,txt_jonined,txt_about,txt_phone,txt_email;
    String teamId= "";
    FirebaseDatabase database;
    DatabaseReference team;
    Team currentteam;
    ImageView img_profile_edit;
    FirebaseRecyclerAdapter<Team, TeamViewHolder> adapter;
    Team teams;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiprofile);
        /////set font///////////
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/restaurant_font.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
        /////set font///////
        anhxa();

        database = FirebaseDatabase.getInstance();
        team = database.getReference("TeamTuongAZ");

        if (getIntent()!= null) {
            teamId = getIntent().getStringExtra("TeamId");
        }
        if (!teamId.isEmpty()) {
            getDetailteam(teamId);
        }

        img_profile_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIProfile.this, EditUIProfile.class);
                intent.putExtra("MyTeamId", currentteam);
                startActivity(intent);
//                String name = txt_name.getText().toString();
//                Intent i = new Intent(UIProfile.this, EditUIProfile.class);
//                i.putExtra("MyTeamId",name);
//
//                startActivity(i);
            }
        });
        img_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UIProfile.this,Uiprofile_Detail.class);
                startActivity(intent);
            }
        });




    }




    private void anhxa() {
        img_more = findViewById(R.id.more);
        txt_about = findViewById(R.id.tv_About_UI_Detail);
        txt_phone = findViewById(R.id.tv_Phone_UI_Detail);
        txt_email = findViewById(R.id.tv_Email_UI_Detail);
        img_profile_edit = findViewById(R.id.profile_edit_image);
        img_anhbia = findViewById(R.id.profile_image_anhbia);
        img_avatar = findViewById(R.id.profile_image_avatar);
        txt_name = findViewById(R.id.profile_tv_name);
        txt_status = findViewById(R.id.profile_tv_status);
        txt_student = findViewById(R.id.profile_tv_student);
        txt_live = findViewById(R.id.profile_tv_live);
        txt_from = findViewById(R.id.profile_tv_from);
        txt_jonined = findViewById(R.id.profile_tv_jonined);
    }



    private void getDetailteam(String teamId) {

        team.child(teamId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentteam = dataSnapshot.getValue(Team.class);
                Picasso.with(getBaseContext()).load(currentteam.getImage())
                        .into(img_anhbia);

                Picasso.with(getBaseContext())
                        .load(currentteam.getAvatar())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.imgerror)
                        .into(img_avatar);

                txt_name.setText(currentteam.getName());
                txt_status.setText(currentteam.getStatus());
                txt_student.setText(currentteam.getStudent());
                txt_from.setText(currentteam.getFrom());
                txt_live.setText(currentteam.getLive());
                txt_jonined.setText(currentteam.getJonined());
                txt_about.setText(currentteam.getAbout());
                txt_email.setText(currentteam.getEmail());
                txt_phone.setText(currentteam.getPhone());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UIProfile.this, "wrong !!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
