package com.tr.imsagesendingtofirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;

public class Task2SideBarMenu extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{


    String name,nameUPDATE,contact,contactUPDATE;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    ImageView prSonIMGview;
    Bundle bundle;

    Uri imgUri;
    Bitmap bitmap;
    ActionBarDrawerToggle actionBarDrawerToggle;

    SharedPreferences sharedPreferences,sharedPreferences_2;

    TextView namE, contacT;
    private static final String SharedPrefKEY_D ="myPref";
    private static final String KEY_NAME_D ="name";
    private static final String KEY_CONTACT_D ="contact";





    private static final String SharedPrefKEY_2="myPref2";
    private static final String KEY_NAME_2="name2";
    private static final String KEY_CONTACT_2="contact2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2_side_bar_menu);


        prSonIMGview=findViewById(R.id.prSonIMG_4_task2);
        namE=findViewById(R.id.NAME);
        contacT=findViewById(R.id.CONTACT);

        drawerLayout=findViewById(R.id.DrawerLayout);
        navigationView=  findViewById(R.id.navigationView_4_task2);
        toolbar=findViewById(R.id.app_bar);





        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true)   ;
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_drawaer_handle_icon);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        actionBarDrawerToggle.syncState();

        sharedPreferences=getSharedPreferences(SharedPrefKEY_D,MODE_PRIVATE);
        name=sharedPreferences.getString(KEY_NAME_D,null);
        contact= sharedPreferences.getString(KEY_CONTACT_D,null);


//         rechecking

        try {



            if (name !=null && contact !=null ){

                namE.setText("NAME : "+name);
                contacT.setText("Contact"+contact);





            }


            sharedPreferences_2=getSharedPreferences(SharedPrefKEY_2,MODE_PRIVATE);
            nameUPDATE=sharedPreferences_2.getString(KEY_NAME_2,null);
            contactUPDATE= sharedPreferences_2.getString(KEY_CONTACT_2,null);

            if(nameUPDATE !=null && contactUPDATE !=null ){
                namE.setText("NAME :"+nameUPDATE);
                contacT.setText("Coontact : "+contactUPDATE);




//            AFTER DOING THIS THE PROBLEM THAT I AM GETTING THE SHARED PREFERENCES IS NOT BEING SAVED AND AGAIN LOGIN SCREEN IS OPENING


                bundle = getIntent().getExtras();
                View headerView = navigationView.getHeaderView(0);
                TextView navUserName = (TextView) headerView.findViewById(R.id.personNameToBeChanged_4_task2);
                String getData = bundle.getString("KEY");
                navUserName.setText(getData);





            }



//        browse button

            View headerViewBrowse = navigationView.getHeaderView(0);
            TextView BROWSE_ = (TextView) headerViewBrowse.findViewById(R.id.browseIMAGE_4_task2);
            BROWSE_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pickImage();
                }
            });



            //        getting on click listner on Image

            navigationView.setNavigationItemSelectedListener(Task2SideBarMenu.this);
//                To Listen click on Image
//        What is meant by getHeaderView(0)  ,, Is it working on Array
            View headView = navigationView.getHeaderView(0);
            prSonIMGview=headView.findViewById(R.id.prSonIMG_4_task2);





            prSonIMGview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(  ( nameUPDATE !=null && contactUPDATE !=null  )    ||  ( name !=null && contact !=null  )    ){



//                     BY DOING THIS I AM ABLE TO GO TO EDIT USER DETAILS ACIVTY , BECAUSE LISTNER WAS ONLY FIRING ONCE NOT EVERY TIME I WANTED
//                    IS THIS THE RIGHT WAY OR WORNG NOT SURE ABOUT IT , IT WAS JUST MY OWN LOGIC .
//                    SharedPreferences.Editor editor =sharedPreferences.edit();

//                    need to rethink proper logic
                        SharedPreferences.Editor editor2 =sharedPreferences_2.edit();
//
//                    editor.clear();
                        editor2.clear();
//                    editor.commit();
                        editor2.commit();
                        finish();


                        Intent GoToEditProfileIntent=new Intent(Task2SideBarMenu.this,EditUserDetailsTask2.class);
                        startActivity(GoToEditProfileIntent);
                    }

                }
            });

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if (item.getItemId()==R.id.itemLogOut){
                        Toast.makeText(Task2SideBarMenu.this, "Logout selected", Toast.LENGTH_SHORT).show();


                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        SharedPreferences.Editor editor2 =sharedPreferences_2.edit();
                        editor.clear();
                        editor2.clear();
                        editor.commit();
                        editor2.commit();
                        finish();
                        Toast.makeText(Task2SideBarMenu.this, "Logeed Out", Toast.LENGTH_SHORT).show();
                        Intent GoToLoginForm = new Intent(Task2SideBarMenu.this,Task2.class);
                        startActivity(GoToLoginForm);
                    }


//                if (item.getItemId()==R.id.prSonIMG){
//                    Toast.makeText(MainActivity.this, "Profile Pic Selected", Toast.LENGTH_SHORT).show();
//                }





                    return true;
                }
            });


        }catch(Exception e){}






    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

//        if(item.getItemId()==R.id.itemLogOut){drawerLayout.openDrawer(GravityCompat.START); return true;}
//        prSonIMGview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "Image selected", Toast.LENGTH_SHORT).show();
//            }
//        });


        return super.onOptionsItemSelected(item);
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }



//    IMAGE PICKING AND DISPLAYING WORK


    //Dexter Work picking image
    void pickImage(){
        Dexter.withActivity(/*the activity on which we are working on */Task2SideBarMenu.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent,"Select Image File"),1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

//                        when app opens after closing again permission will be granted
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==1 && resultCode==RESULT_OK) {
            imgUri=data.getData();
            try {


                InputStream inputStream =getContentResolver().openInputStream(imgUri);
                bitmap = BitmapFactory.decodeStream(inputStream);
//                getImageUri(this,bitmap);
                prSonIMGview.setImageBitmap(bitmap);



/*
First Activity
Uri uri = data.getData();
Intent intent=new Intent(Firstclass.class,secondclass.class);
intent.putExtra("imageUri", uri.toString());
startActivity(intent);
Second class
Imageview iv_photo=(ImageView)findViewById(R.id.iv_photo);
Bundle extras = getIntent().getExtras();
myUri = Uri.parse(extras.getString("imageUri"));
iv_photo.setImageURI(myUri);
* */

            }catch (Exception e){}
        }

        super.onActivityResult(requestCode, resultCode, data);

    }





}