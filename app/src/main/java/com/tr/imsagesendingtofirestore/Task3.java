package com.tr.imsagesendingtofirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Task3 extends AppCompatActivity {

    // TASK 3
//    LOGIN FORM
    Button LOGIN;
    EditText Name,Contact;

    SharedPreferences sharedPreferences;

    private static final String SharedPrefKEY="myPref";
    private static final String KEY_NAME="name";
    private static final String KEY_CONTACT="contact";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);

        Name = (EditText) findViewById(R.id.edtxtName_task3);
        Contact = (EditText) findViewById(R.id.edtxtContact_task3);

        LOGIN = (Button) findViewById(R.id.btnLogin_task3);


        sharedPreferences = getSharedPreferences(SharedPrefKEY, MODE_PRIVATE);


//   first checking if shared preferences data is avaliable or not

        String name = sharedPreferences.getString(KEY_NAME, null);
        if (name != null) {
//            if shared preferences data is avaliable so directly call on HomeActivity(the activity which you want to call)

            Intent DirectGoAndDontComeHere = new Intent(Task3.this, Task3SideBarMenu.class);
            startActivity(DirectGoAndDontComeHere);
        }

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_NAME, Name.getText().toString());
                editor.putString(KEY_CONTACT, Contact.getText().toString());
                editor.apply();

                Intent goToDash = new Intent(Task3.this, Task3SideBarMenu.class);
                startActivity(goToDash);
                Toast.makeText(Task3.this, "Logeed in", Toast.LENGTH_SHORT).show();

            }
        });


    }
}