package com.ttp.shoppingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ttp.shoppingapp.Common.Common;
import com.ttp.shoppingapp.Model.User;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    Button btnSignIn, btnSignUp;
    TextView txtSlogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSignIn = findViewById(R.id.btnSignIp);
        btnSignUp = findViewById(R.id.btnSignUp);

        txtSlogan = findViewById(R.id.txtSlogan);

        Paper.init(this);
//        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/08SeoulNamsanEB.ttp");
//        txtSlogan.setTypeface(face);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIn = new Intent(MainActivity.this, SignIn.class);
                startActivity(signIn);

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(MainActivity.this, SignUp.class);
                startActivity(signup);

            }
        });

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);
        if(user != null && pwd != null) {
            if(!user.isEmpty() && !pwd.isEmpty()) login(user,pwd);
        }


    }

    private void login(final String phone, final String pwd) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference tabe_user = database.getReference("User");

        if (Common.isConnectionToInternet(getBaseContext())) {

            final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
            mDialog.setMessage("please wait..");
            mDialog.show();

            tabe_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //check if use is or not
                    if (dataSnapshot.child(phone).exists()) {
                        mDialog.dismiss();
                        //GET USER INFORMATION
                        User user = dataSnapshot.child(phone).getValue(User.class);
                        user.setPhone(phone);
                        if (user.getPassword().equals(pwd)) {
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            Common.currentuser = user;
                            startActivity(intent);
                            finish();

                        }
                    } else {
                        mDialog.dismiss();
                        Toast.makeText(MainActivity.this, "WRONG PASSWORD ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(MainActivity.this, "Sign in fail.", Toast.LENGTH_SHORT).show();
                }
            });
        } else{
            Toast.makeText(MainActivity.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}