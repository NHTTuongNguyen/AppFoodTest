package com.example.firebase.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.Model.Team;
import com.example.firebase.R;
import com.example.firebase.ViewHolder.TeamViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MyTeam extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference team;
    TextView txtFullName;
    RecyclerView recyler_menu;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Team, TeamViewHolder> adapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        /////set font///////////
        /////set font///////
        database = FirebaseDatabase.getInstance();
        team = database.getReference("TeamTuongAZ");
        recyler_menu = findViewById(R.id.recycler_team);
        layoutManager = new LinearLayoutManager(this);//them luc sau //
        recyler_menu.setLayoutManager(layoutManager);
        recyler_menu.setHasFixedSize(true);

        toolBar();
        loadTeam();

    }

    private void toolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("M Y T E A M");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void loadTeam() {
        adapter= new FirebaseRecyclerAdapter<Team, TeamViewHolder>
                (Team.class,R.layout.item_my_team, TeamViewHolder.class,team) {
            @Override
            protected void populateViewHolder(TeamViewHolder viewHolder, Team model, int position) {
                viewHolder.tv_name_team.setText(model.getName());
                viewHolder.tv_vitri.setText(model.getLocation());
                Picasso.with(getBaseContext())
                        .load(model.getAvatar())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.error)
                        .into(viewHolder.img_profile);

                final Team clickitem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent i = new Intent(MyTeam.this,UIProfile.class);
                        i.putExtra("TeamId",adapter.getRef(position).getKey());
                        startActivity(i);

                    }
                });

            }
        };
        recyler_menu.setAdapter(adapter);
    }

}
