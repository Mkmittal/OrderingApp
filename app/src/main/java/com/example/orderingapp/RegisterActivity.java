package com.example.orderingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingapp.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    EditText mTextUsername;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    EditText mTextFullName;
    Button mSignUp;
    TextView mTextViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize firebase
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("User");

        mTextCnfPassword = findViewById(R.id.register_password2);
        mTextFullName = findViewById(R.id.register_name);
        mSignUp = findViewById(R.id.register_SIGNUP);
        mTextPassword = findViewById(R.id.register_password);
        mTextUsername = findViewById(R.id.register_username);
        mTextViewLogin = findViewById(R.id.register_login);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent LoginIntent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(LoginIntent);
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                mDialog.setMessage("please Wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (mTextUsername.getText().toString().length() != 10)
                        {
                            mDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, "INVALID USERNAME", Toast.LENGTH_SHORT).show();
                            mTextUsername.getText().clear();
                        }
                        else
                            {

                                if (mTextPassword.getText().toString().equals(mTextCnfPassword.getText().toString()))
                                    {
                                        if (dataSnapshot.child(mTextUsername.getText().toString()).exists())
                                        {
                                            mDialog.dismiss();
                                            Toast.makeText(RegisterActivity.this, "USER ALREADY REGISTERED", Toast.LENGTH_SHORT).show();
                                            mTextPassword.getText().clear();
                                            mTextCnfPassword.getText().clear();
                                            mTextUsername.getText().clear();
                                            mTextFullName.getText().clear();
                                        }
                                        else
                                        {
                                            mDialog.dismiss();
                                            User user = new User(mTextFullName.getText().toString(), mTextPassword.getText().toString());
                                            table_user.child(mTextUsername.getText().toString()).setValue(user);
                                            Toast.makeText(RegisterActivity.this, "REGISTERED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                                            mTextPassword.getText().clear();
                                            mTextCnfPassword.getText().clear();
                                            mTextUsername.getText().clear();
                                            mTextFullName.getText().clear();
                                            finish();
                                            Intent toLogin = new Intent(RegisterActivity.this,LoginActivity.class);
                                            startActivity(toLogin);
                                        }
                                    }
                                else
                                    {
                                        mDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "PASSWORD DOESN'T MATCH", Toast.LENGTH_SHORT).show();
                                        mTextPassword.getText().clear();
                                        mTextCnfPassword.getText().clear();
                                    }
                            }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                }
        });
    }
}
