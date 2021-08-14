package com.tr.imsagesendingtofirestore;
//Signup Activity
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth;

    TextView BrowseImage;
    Button Send;
    ImageView ProfilePic;
    Uri imgUri;
    FirebaseStorage storage;
    StorageReference storageReference;
    EditText EdTextname,EdTextemail,EdTextpass,EdTextcontact;

    String GettingEdtextName;
    String GettingEdtextEmail;
    String GettingEdtextPass;
    String GettingEdtextContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        auth = FirebaseAuth.getInstance();

        BrowseImage = (TextView) findViewById(R.id.browse);
        Send =(Button) findViewById(R.id.send);
        ProfilePic= (ImageView) findViewById(R.id.profilePic);
        storage = FirebaseStorage.getInstance();


        EdTextname=(EditText) findViewById(R.id.NAME);
        EdTextemail=(EditText) findViewById(R.id.EMAIL);
        EdTextpass=(EditText) findViewById(R.id.PASS);
        EdTextcontact=(EditText) findViewById(R.id.CONTACT);






//


        BrowseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });




        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DONT DO ANYTHING UNTILL VALIDATED
//                if (Validations(GettingEdtextName, GettingEdtextEmail , GettingEdtextPass ,GettingEdtextContact)){



//                    UPLOADING IMAGE
                    //uploadImage();
                uploadImageNew(); //New function for uploading image by Asad
//                    FIREBASE FIRESTORE

//                    CollectionReference SignupDetails = db.collection("userdetails");
//                    String ConvImageUri=imgUri.toString();
//                         UserModel userModel = new UserModel(ConvImageUri,
//
//                                 GettingEdtextName,
//                                 GettingEdtextEmail,
//                                 GettingEdtextPass,
//                                 GettingEdtextContact);
//
//                         SignupDetails.add(userModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                             @Override
//                             public void onSuccess(DocumentReference documentReference) {
//                                 Toast.makeText(MainActivity.this, "Data Stored Sucess", Toast.LENGTH_SHORT).show();
//                             }
//                         }).addOnFailureListener(new OnFailureListener() {
//                             @Override
//                             public void onFailure(@NonNull Exception e) {
//
//                                 Toast.makeText(MainActivity.this, "Data Stored Un Sucessfull", Toast.LENGTH_SHORT).show();
//
//                             }
//                         });

//                }

            }
        });


    }

//Dexter Work picking image
    void pickImage(){
        Dexter.withActivity(/*the activity on which we are working on */MainActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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


//    this work is to send image in firebase storage
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode==1 && resultCode==RESULT_OK) {
            imgUri=data.getData();
            try {

                InputStream inputStream =getContentResolver().openInputStream(imgUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ProfilePic.setImageBitmap(bitmap);
            }catch (Exception e){}
        }

        super.onActivityResult(requestCode, resultCode, data);

    }



//    Uploading image to firebase storage
    void uploadImage(){
        if(imgUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
//                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
// ager FireStoreBhe ho ga na wo Storage ka varaible uploader k ander aye ga  ,,
//                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
//                                    DatabaseReference root=db.getReference("users");
//
////                        this is my MODEL class having parameterized constructor
//                                    DataMODEL obj= new DataMODEL(email.getText().toString(),mobile.getText().toString(),name.getText().toString(),uri.toString());
////                       MARKING HERE
////                       ya root database refrence ais main simple hum na constructor ka ander values get kr wye hain
//                                    root.setValue(obj);
//                                    email.setText("");
//                                    mobile.setText("");
//                                    name.setText("");
//                                    img.setImageResource(R.drawable.ic_launcher_background);
//                                    Toast.makeText(SignUpForm.this, "Data sucessfully uploaded", Toast.LENGTH_SHORT).show();

                                    //                    FIREBASE FIRESTORE

                                    CollectionReference SignupDetails = db.collection("userdetails");
                                    String ConvImageUri=imgUri.toString();
                                    GettingEdtextName=EdTextname.getText().toString();
                                    GettingEdtextEmail=EdTextemail.getText().toString();
                                    GettingEdtextPass=EdTextpass.getText().toString();
                                    GettingEdtextContact=EdTextcontact.getText().toString();
                                    UserModel userModel = new UserModel(ConvImageUri,

                                            GettingEdtextName,
                                            GettingEdtextEmail,
                                            GettingEdtextPass,
                                            GettingEdtextContact);

                                    SignupDetails.add(userModel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                        @Override
                                        public void onSuccess(DocumentReference documentReference) {
                                            Toast.makeText(MainActivity.this, "Data Stored Sucess", Toast.LENGTH_SHORT).show();



                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(MainActivity.this, "Data Stored Un Sucessfull", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            });



                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

//  validations
//    boolean Validations(String name, String email , String pass ,String contact ){
//
//        if (name.isEmpty()){
//            EdTextname.setError("Field Required");
//            return true;
//        }
//
//        if (email.isEmpty()){
//            EdTextemail.setError("Field Required");
//            return true;
//        }
//        if (pass.isEmpty()){
//            EdTextpass.setError("Field Required");
//            return true;
//        }
//        if (contact.isEmpty()){
//            EdTextcontact.setError("Field Required");
//            return true;
//        }
//
//
//
//        return false;
//
//    }

    //New function to upload image and save user details created by Asad
    void uploadImageNew(){
        if(imgUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(imgUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //We create new document in firestore with same user id that is generated in authentication. Authentication id is
                                    //auto generated when user is authenticated successfully. To get the user id we use below code.
                                    String userId = auth.getCurrentUser().getUid();

                                    //We give user id to document function after collection and get its result in document reference. see below
                                    DocumentReference SignupDetails = db.collection("userdetails").document(userId);

                                    //You were saving imgUri in db which is your device generated uri and it won't work outside your device.
                                    //We have to use uri object which is returned by success listener of getDownloadUrl. See below
                                    //String ConvImageUri = imgUri.toString();
                                    String ConvImageUri = uri.toString();
                                    GettingEdtextName = EdTextname.getText().toString();
                                    GettingEdtextEmail = EdTextemail.getText().toString();
                                    GettingEdtextPass = EdTextpass.getText().toString();
                                    GettingEdtextContact = EdTextcontact.getText().toString();

                                    UserModel userModel = new UserModel(ConvImageUri,
                                            GettingEdtextName,
                                            GettingEdtextEmail,
                                            GettingEdtextPass,
                                            GettingEdtextContact);

                                    SignupDetails.set(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(MainActivity.this, "Data Stored Sucess", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(MainActivity.this, "Data Stored Un Sucessfull", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

}