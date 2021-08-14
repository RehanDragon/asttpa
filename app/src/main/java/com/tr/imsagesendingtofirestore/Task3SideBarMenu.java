package com.tr.imsagesendingtofirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Task3SideBarMenu extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    ActionBarDrawerToggle actionBarDrawerToggle;

    SharedPreferences sharedPreferences;

    TextView namE, contacT;
    private static final String SharedPrefKEY_D ="myPref";
    private static final String KEY_NAME_D ="name";
    private static final String KEY_CONTACT_D ="contact";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3_side_bar_menu);

        namE=findViewById(R.id.NAME_task3);
        contacT=findViewById(R.id.CONTACT_task3);

        drawerLayout=findViewById(R.id.DrawerLayout_task3);
        navigationView=findViewById(R.id.navigationView_task3);
        toolbar=findViewById(R.id.app_bar_task3);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true)   ;

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        actionBarDrawerToggle.syncState();

        sharedPreferences=getSharedPreferences(SharedPrefKEY_D,MODE_PRIVATE);
        String name=sharedPreferences.getString(KEY_NAME_D,null);
        String contact= sharedPreferences.getString(KEY_CONTACT_D,null);



        if (name !=null && contact !=null){

            namE.setText("NAME : "+name);
            contacT.setText("Contact"+contact);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.itemLogOut_task3){
                    Toast.makeText(Task3SideBarMenu.this, "Logout selected", Toast.LENGTH_SHORT).show();


                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    finish();
                    Toast.makeText(Task3SideBarMenu.this, "Logeed Out", Toast.LENGTH_SHORT).show();
                    Intent GoToLoginForm = new Intent(Task3SideBarMenu.this,Task3.class);
                    startActivity(GoToLoginForm);
                }
                return true;
            }
        });




    }
}