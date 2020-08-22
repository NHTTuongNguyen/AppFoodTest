package com.example.firebase.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andremion.counterfab.CounterFab;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.firebase.Common.Common;
import com.example.firebase.Database.Database;
import com.example.firebase.GoogleMap.MapsActivity;
import com.example.firebase.Interface.ItemClickListener;
import com.example.firebase.Model.Banner;
import com.example.firebase.Model.Category;
import com.example.firebase.Model.Category2;
import com.example.firebase.Model.CategoryOther;
import com.example.firebase.R;
import com.example.firebase.Service.ListenOrder;
import com.example.firebase.ViewHolder.MenuViewHold;
import com.example.firebase.ViewHolder.MenuViewHoldOther;
import com.example.firebase.ViewHolder.MenuViewHolder2;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import info.hoang8f.widget.FButton;
import io.paperdb.Paper;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseDatabase database,database_CategoryOther,database_Sale;
    DatabaseReference category, databaseReference_CategoryOther,databaseReference_Sale;
    TextView txtFullName,txtFullEmail,txtFullPhone;
    RecyclerView recyler_menu,recyler_menu2,recyler_Other;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Category,  MenuViewHold>adapter;
    FirebaseRecyclerAdapter<Category2, MenuViewHolder2>adapter_Sale;
    FirebaseRecyclerAdapter<CategoryOther, MenuViewHoldOther>adapter_Other;
    EditText edtName;
    FButton btnUpload,btnSelect;
    CounterFab counterFab;
    FirebaseStorage storage;
    StorageReference storageReference;
    Category newcategory;
    Uri saveUri;
    private final  int PICK_IMAGE_REQUEST = 71;

    //////Slider
    HashMap<String,String> img_list;
    SliderLayout mSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ///init Firebase
        database = FirebaseDatabase.getInstance();
        category = database.getReference("CategoryTuongAZ");

        database_CategoryOther = FirebaseDatabase.getInstance();
        databaseReference_CategoryOther = database_CategoryOther.getReference("CategoryOtherTuongAZ");
        database_Sale = FirebaseDatabase.getInstance();
        databaseReference_Sale = database_Sale.getReference("SaleTuongAZ");///Sale
        /////////////////
        ///Set Up Slider
        setupSlider();
        Paper.init(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        recyler_Other = findViewById(R.id.recycler_Other);
        recyler_Other.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyler_Other.setLayoutManager(layoutManager);


        recyler_menu2 = findViewById(R.id.recycler_menu2);
        recyler_menu2.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyler_menu2.setLayoutManager(layoutManager);
        ///////
        recyler_menu = findViewById(R.id.recycler_menu);
        recyler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyler_menu.setLayoutManager(layoutManager);



        if (Common.isConnectedtoInternet(getBaseContext())){
            loadMenu();
            loadMenu2();
            loadMenu_Other();
        }else {
            Toast.makeText(this, "Please check your connect", Toast.LENGTH_SHORT).show();
        }
        CounterFab counterFab =  findViewById(R.id.counterFab_Home);
        counterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i = new Intent(Home.this,Cart.class);
             startActivity(i);
            }
        });
        counterFab.setCount(new Database(this).getCountCart());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //set name for user
        View hearderView = navigationView.getHeaderView(0);
        txtFullEmail = hearderView.findViewById(R.id.txtFullEmail);
        txtFullName=hearderView.findViewById(R.id.txtFullName);
//        txtFullPhone = hearderView.findViewById(R.id.txtFullPhone);

        txtFullEmail.setText(Common.currentUser.getEmail());
        txtFullName.setText(Common.currentUser.getName());
//        txtFullPhone.setText(Common.currentUser.getPhone());





        Intent service = new Intent(Home.this, ListenOrder.class);
        startService(service);

        //Register Service



    }

    @Override
    protected void onResume() {
        super.onResume();
//        counterFab.setCount(new Database(this).getCountCart());
        if (adapter != null){
            adapter.startListening();
        }else if (adapter_Sale !=null){
            adapter_Sale.startListening();
        }else if (adapter_Other !=null){
            adapter_Other.startListening();
        }
    }

    private void loadMenu_Other() {
        adapter_Other = new FirebaseRecyclerAdapter<CategoryOther, MenuViewHoldOther>
                (CategoryOther.class,R.layout.item_category_other, MenuViewHoldOther.class, databaseReference_CategoryOther) {
            @Override
            protected void populateViewHolder(MenuViewHoldOther viewHolder, CategoryOther model, int position) {
                viewHolder.tv_name_category_other.setText(model.getName());
                viewHolder.tv_price_category_other.setText(model.getPrice());
                viewHolder.tv_price_category_other.setText(String.format("$ %s",model.getPrice()));
                viewHolder.tv_star_category_other.setText(model.getStar());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.error)
                        .into(viewHolder.image_category_other);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent i = new Intent(Home.this,CategoryOtherActivity.class);
                        i.putExtra("CategoryOtherId",adapter_Other.getRef(position).getKey());
                        startActivity(i);
                    }
                });


            }
        };

        recyler_Other.setAdapter(adapter_Other);

        adapter_Other.notifyDataSetChanged();

        recyler_Other.setNestedScrollingEnabled(false);

    }
    private void loadMenu() {
        adapter= new FirebaseRecyclerAdapter<Category, MenuViewHold>
                (Category.class,R.layout.menu_item, MenuViewHold.class, category) {
            @Override
            protected void populateViewHolder(MenuViewHold viewHolder, Category model, int position) {


//                Random rnd = new Random();
//                int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//                viewHolder.txtMenuName.setBackgroundColor(currentColor);
                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.imgerror)
                        .into(viewHolder.imageView);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                    }
                });
                final Category clickItem = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent i = new Intent(Home.this,FoodList.class);
                        i.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(i);

                    }
                });
            }
        };

        recyler_menu.setAdapter(adapter);
        recyler_menu.setNestedScrollingEnabled(false);
        adapter.notifyDataSetChanged();
//        swipeRefreshLayout.setRefreshing(false);
    }
    private void loadMenu2() {
        adapter_Sale = new FirebaseRecyclerAdapter<Category2, MenuViewHolder2>
                (Category2.class,R.layout.menu_item2,MenuViewHolder2.class,databaseReference_Sale) {
            @Override
            protected void populateViewHolder(MenuViewHolder2 viewHolder, final Category2 model, int position) {
                viewHolder.txtMenuName2.setText(model.getName());
//                if (position==0){
//                    viewHolder.tv_sale.setBackgroundColor(getResources().getColor(R.color.my_statusbar_color));
//                }
                viewHolder.tv_sale.setText(model.getSale());

                Picasso.with(getBaseContext()).load(model.getImage())
                        .placeholder(R.drawable.imgerror)
                        .error(R.drawable.error)
                        .into(viewHolder.imgMenuImage2);

                try {
                    // Initialize a new GradientDrawable
                    GradientDrawable gd = new GradientDrawable();
                    // Specify the shape of drawable
                    gd.setShape(GradientDrawable.RECTANGLE);
                    // Set the fill colors of drawable
                    gd.setColor(Color.parseColor(model.getSale()));
                    // Make the border rounded
                    gd.setCornerRadii(new float[]{0, 0, 40.0f, 40.0f, 40.0f, 40.0f, 0, 0}); // border corner radius
                    viewHolder.tv_sale.setBackground(gd);
                } catch (Exception e) {
                    Log.d("error_show", e.toString());
                }

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        final Category2 category2 = model;
                        Intent i = new Intent(Home.this,SaleList.class);
                        i.putExtra("SaleId",adapter_Sale.getRef(position).getKey());
                        startActivity(i);
                    }
                });

            }
        };
        recyler_menu2.setAdapter(adapter_Sale);
        adapter_Sale.notifyDataSetChanged();
        recyler_menu2.setNestedScrollingEnabled(false);

    }



    private void setupSlider() {
        mSlider = findViewById(R.id.slider);
        img_list = new HashMap<>();
        final DatabaseReference banners = database.getReference("BannerTuongAZ");

        banners.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Banner banner = postSnapshot.getValue(Banner.class);
                    //We will concat string name and id like
                    ///
                    img_list.put(banner.getName()+"_"+banner.getId(),banner.getImage());
                }
                for (String key:img_list.keySet()) {
                    String[] keySpilit  = key.split("_");
                    String nameOfFood = keySpilit[0];
                    final String idOfFood = keySpilit[1];

                    ///create  Slider
                    final TextSliderView textSliderView  = new TextSliderView(getBaseContext());
                    textSliderView
                            .description(nameOfFood)
                            .image(img_list.get(key))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Intent intent  = new Intent(Home.this,FoodDetail.class);
                                    intent.putExtras(textSliderView.getBundle());
                                    startActivity(intent);
                                }
                            });
                    //// add extra Bundel
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("FoodId",idOfFood);
                    mSlider.addSlider(textSliderView);
                    banners.removeEventListener(this);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Home.this,InfoCustomer.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {


        } else if (id == R.id.nav_cart) {
            Intent activityCart = new Intent(Home.this,Cart.class);
            startActivity(activityCart);

        } else if (id == R.id.nav_orthers) {
            Intent activityOrder = new Intent(Home.this,OrderStatus.class);
            startActivity(activityOrder);

        } else if (id == R.id.nav_test) {
            Intent map = new Intent(Home.this, MapsActivity.class);
            startActivity(map);
        }
        else if(id == R.id.nav_change_Passwrod){
//           showDialongChangePassword();
        }
        else if(id == R.id.nav_info_Customer){

            Intent infoCustomer = new Intent(Home.this, InfoCustomer.class);
            startActivity(infoCustomer);
        }
        else if(id == R.id.nav_AddressHome){

            showDiaLogAddressHome();
        }
        else if (id == R.id.nav_team) {
            Intent team = new Intent(Home.this,MyTeam.class);
            startActivity(team);
        } else if (id == R.id.log_out) {

            /// delete Remember me user and password
            Paper.book().destroy();
            ///out
            Intent activityOut = new Intent(Home.this,Login.class);
            activityOut.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(activityOut);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showDiaLogAddressHome() {
        AlertDialog.Builder alerdialog = new AlertDialog.Builder(Home.this);
        alerdialog.setTitle("Add Address For User");
        alerdialog.setMessage("Info");
        LayoutInflater  inflater  = this.getLayoutInflater();
        final View addressHome = inflater.inflate(R.layout.dialog_address_home,null);
        final EditText edtAddressHome = addressHome.findViewById(R.id.edtAddressHome);
        alerdialog.setView(addressHome);
        alerdialog.setIcon(R.drawable.ic_cart_black);
        alerdialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Common.currentUser.setHomeAddress(edtAddressHome.getText().toString());

                FirebaseDatabase.getInstance().getReference("User")
                        .child(Common.currentUser.getPhone())
                        .setValue(Common.currentUser)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Home.this, "Add Address Successful", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        alerdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        alerdialog.show();
    }

//    private void showDialongChangePassword() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
//        alertDialog.setTitle("Change Password");
//        alertDialog.setMessage("Please fill fun imformation");
//
//        LayoutInflater inflater = this.getLayoutInflater();
//        View change_Password = inflater.inflate(R.layout.change_password,null);
//
//        final MaterialEditText edtChangPassword = change_Password.findViewById(R.id.edtChangePassword);
//        final MaterialEditText edtNewPassword = change_Password.findViewById(R.id.edtNewPassword);
//        final MaterialEditText edtRepeatPassword = change_Password.findViewById(R.id.edtRepeatPassword);
//        alertDialog.setPositiveButton("Change", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                final android.app.AlertDialog waitingDialog  = new SpotsDialog(Home.this);
//                waitingDialog.show();
//
//                ///Check old password
//
//                if (edtChangPassword.getText().toString().equals(Common.currentUser.getPassword())){
//
//                    ////check new  password and repeat password
//                    if (edtNewPassword.getText().toString().equals(edtRepeatPassword.getText().toString())){
//                        Map<String,Object> passwordUpdate = new HashMap<>();
//                        passwordUpdate.put("password",edtNewPassword.getText().toString());
//
//                        ///make database
//                        DatabaseReference user = FirebaseDatabase.getInstance().getReference("User");
//                         user.child(Common.currentUser.getPhone())
//                                 .updateChildren(passwordUpdate)
//                                 .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                     @Override
//                                     public void onComplete(@NonNull Task<Void> task) {
//                                         waitingDialog.dismiss();
//                                         Toast.makeText(Home.this, "Password was update", Toast.LENGTH_SHORT).show();
//                                     }
//                                 })
//                                 .addOnFailureListener(new OnFailureListener() {
//                                     @Override
//                                     public void onFailure(@NonNull Exception e) {
//                                         Toast.makeText(Home.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                     }
//                                 });
//                    }
//                    else {
//                        waitingDialog.dismiss();
//                        Toast.makeText(Home.this, "New Password doesn't match", Toast.LENGTH_SHORT).show();
//                    }
//                }else {
//                    Toast.makeText(Home.this, "Wrong old  password ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//
//            }
//        });
//        alertDialog.setView(change_Password);
//        alertDialog.setIcon(R.drawable.ic_change_password);
//        alertDialog.show();
//    }






}
