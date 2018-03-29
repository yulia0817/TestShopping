package com.ttp.getapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.ttp.getapp.Model.User;

public class SignIn extends AppCompatActivity {


    MaterialEditText edtPhone, edtPassword;
    Button btnSign;

    FirebaseDatabase database;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword1);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone1);
        btnSign = findViewById(R.id.btnSignIn);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("User");

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signInUser(edtPhone.getText().toString(), edtPassword.getText().toString());


//                tabe_user.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        //check if use is or not
//                        if(dataSnapshot.child(edtPhone.getText().toString()).exists()) {
//                            mDialog.dismiss();
//                            //GET USER INFORMATION
//                            User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
//                            user.setPhone(edtPhone.getText().toString());
//                            if (user.getPassword().equals(edtPassword.getText().toString())) {
//                                Intent intent = new Intent(SignIn.this, Home.class);
//                                Common.Currentuser = user;
//                                startActivity(intent);
//                                finish();
//
//                            }
//                        } else {
//                            mDialog.dismiss();
//                            Toast.makeText(SignIn.this, "WRONG PASSWORD ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        Toast.makeText(SignIn.this, "Sign in fail.", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

    }

    private void signInUser(String phone, String password) {
        final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
        mDialog.setMessage("please wait..");
        mDialog.show();

        final String localPhone = phone;
        final String localPassword = password;

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(localPhone).exists()) {
                    mDialog.dismiss();
                    User user = dataSnapshot.child(localPhone).getValue(User.class);
                    user.setPhone(localPhone);
                    if (Boolean.parseBoolean(user.getIsStaff())) {
                        if (user.getPassword().equals(localPassword)) {
                            startActivity(new Intent(SignIn.this, Home.class));

                        } else {
                            Toast.makeText(SignIn.this, "Wrong Password.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignIn.this, "Please login with staff account", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    mDialog.dismiss();
                    Toast.makeText(SignIn.this, "db not has the user", Toast.LENGTH_SHORT).show();
                }

                }

                @Override
                public void onCancelled (DatabaseError databaseError){

                }
            });

        }
    }
