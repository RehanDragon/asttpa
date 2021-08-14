package com.tr.imsagesendingtofirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Task2 extends AppCompatActivity {

    Button LOGIN;
    EditText Name,Contact;

    SharedPreferences sharedPreferences;

    private static final String SharedPrefKEY="myPref";
    private static final String KEY_NAME="name";
    private static final String KEY_CONTACT="contact";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);


        Name    =   (EditText) findViewById(R.id.edtxtName);
        Contact =   (EditText) findViewById(R.id.edtxtContact);

        LOGIN=     (Button) findViewById(R.id.btnLogin);


        sharedPreferences =getSharedPreferences(SharedPrefKEY,MODE_PRIVATE);


//   first checking if shared preferences data is avaliable or not

        String name = sharedPreferences.getString(KEY_NAME,null);
        if (name!=null){
//            if shared preferences data is avaliable so directly call on HomeActivity(the activity which you want to call)

            Intent DirectGoAndDontComeHere = new Intent(Task2.this,Task2SideBarMenu.class);
            startActivity(DirectGoAndDontComeHere);
        }

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putString(KEY_NAME,Name.getText().toString());
                editor.putString(KEY_CONTACT,Contact.getText().toString());
                editor.apply();

                Intent goToDash= new Intent(Task2.this,Task2SideBarMenu.class);
                startActivity(goToDash);
                Toast.makeText(Task2.this, "Logeed in", Toast.LENGTH_SHORT).show();

            }
        });




    }
}