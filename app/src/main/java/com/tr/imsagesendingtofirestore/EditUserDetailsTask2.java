package com.tr.imsagesendingtofirestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditUserDetailsTask2 extends AppCompatActivity {


    EditText updateName,updateContact;
    Button UpdateButton;
    SharedPreferences sharedPreferences;

    private static final String SharedPrefKEY="myPref2";
    private static final String KEY_NAME="name2";
    private static final String KEY_CONTACT="contact2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details_task2);
        updateName=findViewById(R.id.upDateNameEdtxt);
        updateContact=findViewById(R.id.upDateContactEdtxt);
        UpdateButton=findViewById(R.id.upDateBtn);
        sharedPreferences =getSharedPreferences(SharedPrefKEY,MODE_PRIVATE);


        String name = sharedPreferences.getString(KEY_NAME,null);
        if (name!=null){
//            if shared preferences data is avaliable so directly call on HomeActivity(the activity which you want to call)

            Intent DirectGoAndDontComeHere = new Intent(EditUserDetailsTask2.this,MainActivity.class);
            startActivity(DirectGoAndDontComeHere);
        }



        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor =sharedPreferences.edit();
                editor.putString(KEY_NAME,updateName.getText().toString());
                editor.putString(KEY_CONTACT,updateContact.getText().toString());
                editor.apply();

                Intent goToDash= new Intent(EditUserDetailsTask2.this,Task2SideBarMenu.class);



//               commented this because of it app was not working properly
                Bundle bundle = new Bundle();

                bundle.putString("KEY",  updateName.getText().toString());
                goToDash.putExtras(bundle);
                startActivity(goToDash);
                Toast.makeText(EditUserDetailsTask2.this, "Profile Updated", Toast.LENGTH_SHORT).show();

            }
        });



    }
}