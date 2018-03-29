package com.ttp.shoppingapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.CheckBox;
import com.ttp.shoppingapp.Common.Common;
import com.ttp.shoppingapp.Model.User;


import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {


    MaterialEditText edtPhone, edtPassword;
    Button btnSign;
    CheckBox ckbRemember;
    TextView txtForgotPwd;

    FirebaseDatabase database;
    DatabaseReference tabe_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = (MaterialEditText) findViewById(R.id.edtPassword1);
        edtPhone = (MaterialEditText) findViewById(R.id.edtPhone1);
        btnSign = findViewById(R.id.btnSignIn);
        ckbRemember = (CheckBox) findViewById(R.id.ckbRemember);
        txtForgotPwd = findViewById(R.id.txtForgotPwd);

        //paper
        Paper.init(this);

        database = FirebaseDatabase.getInstance();
        tabe_user = database.getReference("User");
        txtForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPwDialog();
            }
        });

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectionToInternet(getBaseContext())) {

                    //save login
                    if(ckbRemember.isChecked()){
                        Paper.book().write(Common.USER_KEY, edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY, edtPassword.getText().toString());
                    }

                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("please wait..");
                    mDialog.show();

                    tabe_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check if use is or not
                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {
                                mDialog.dismiss();
                                //GET USER INFORMATION
                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                user.setPhone(edtPhone.getText().toString());
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Intent intent = new Intent(SignIn.this, Home.class);
                                    Common.currentuser = user;
                                    startActivity(intent);
                                    finish();

                                }
                            } else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "WRONG PASSWORD ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(SignIn.this, "Sign in fail.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else{
                    Toast.makeText(SignIn.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    private void showForgotPwDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("패스워드 찾기");
        builder.setMessage("가입 시 설정한 보안번호를 입력하세요.");


        LayoutInflater inflater = this.getLayoutInflater();
        View forgot_view = inflater.inflate(R.layout.forgot_password_layout, null);

        builder.setView(forgot_view);
        builder.setIcon(R.drawable.ic_security_black_24dp);

        MaterialEditText  edtId = forgot_view.findViewById(R.id.edtPhone);
        final MaterialEditText edtScureCode = forgot_view.findViewById(R.id.edtSecureCode);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
             tabe_user.addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(DataSnapshot dataSnapshot) {
                     User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);

                     if(user.getSecureCode().equals(edtScureCode.getText().toString()))
                         Toast.makeText(SignIn.this, "비밀번호는 "+user.getPassword() +"입니다.", Toast.LENGTH_SHORT).show();
                     else
                         Toast.makeText(SignIn.this, "보안번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onCancelled(DatabaseError databaseError) {

                 }
             });
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }
}
