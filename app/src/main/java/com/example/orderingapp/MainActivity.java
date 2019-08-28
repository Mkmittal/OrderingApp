package com.example.orderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderingapp.Common.Common;
import com.example.orderingapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private EditText mTextUsername;
    private EditText mTextPassword;
    private Button mButtonLogin;
    private TextView mTextViewRegister;
    private EditText mTextOtp;
    private Button mButtonVerify;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    private FirebaseAuth mAuth;
    private String verificationCode;
    private User mUser;
    private DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        mButtonLogin=findViewById(R.id.login_button);
        mTextPassword=findViewById(R.id.password);
        mTextUsername=findViewById(R.id.username);
        mTextViewRegister=findViewById(R.id.register_now);
        mTextOtp=findViewById(R.id.otp);
        mButtonVerify=findViewById(R.id.verify_button);


        //Initializing firebase
        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        table_user = firebaseDatabase.getReference("User");
        mAuth = FirebaseAuth.getInstance();

        settingCallBack();

        settingOnClickListener();
    }

    private void settingOnClickListener() {
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("please Wait...");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //check if user doesnt exist
                        if (mTextUsername.getText().toString().length() != 10) {
                            mDialog.dismiss();
                            Toast.makeText(MainActivity.this, "INVALID USERNAME", Toast.LENGTH_SHORT).show();
                            mTextUsername.getText().clear();
                        } else {
                            if (dataSnapshot.child(mTextUsername.getText().toString()).exists()) {
                                //get user information
                                mDialog.dismiss();
                                mUser = dataSnapshot.child(mTextUsername.getText().toString()).getValue(User.class);
                                if (mUser.getPassword().equals(mTextPassword.getText().toString())) {
                                    Toast.makeText(MainActivity.this, "Sending OTP Please Verify", Toast.LENGTH_SHORT).show();
                                    sendSms();
                                } else {
                                    mDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "PASSWORD DOESN'T MATCH", Toast.LENGTH_SHORT).show();
                                }
                            } else{
                                mDialog.dismiss();
                                Toast.makeText(MainActivity.this, "UNREGISTERED USER", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        mButtonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = mTextOtp.getText().toString().trim();
                if (code.isEmpty() || code.length() < 6) {
                    mTextOtp.setError("Enter valid code");
                    mTextOtp.requestFocus();
                    return;
                }

                //verifying the code entered manually
                verify(code);
            }
        });
    }

    private void settingCallBack() {
        mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                String code = phoneAuthCredential.getSmsCode();
                if (code != null) {
                    mTextOtp.setText(code);
                    //verifying the code
                    verify(code);
                }
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(MainActivity.this, "Verification Failed", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken forceResendingToken)
            {
                super.onCodeSent(s,forceResendingToken);
                verificationCode = s;
                Toast.makeText(MainActivity.this, "Sending OTP Please Verify", Toast.LENGTH_SHORT).show();
            }
        };
        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
    }

    public void signIn(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    Intent toLogedIn = new Intent(MainActivity.this, LoginActivity.class);
                    Common.currentUser = mUser;
                    startActivity(toLogedIn);
                } else {

                    //verification unsuccessful.. display an error message

                    String message = "Somthing is wrong, we will fix it soon...";

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }

                    Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                    snackbar.setAction("Dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    snackbar.show();
                }
            }
        });
    }

    public void verify(String Otp){
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,Otp);
            signIn(credential);
    }

    public void sendSms(){
    String number=mTextUsername.getText().toString();
    number = "+91"+number;
    PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, TimeUnit.SECONDS,this,mCallBacks);
        Toast.makeText(MainActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
    }
}
