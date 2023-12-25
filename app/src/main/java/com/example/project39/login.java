package com.example.project39;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.project39.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class login extends AppCompatActivity {
    TextView register,forgot;
    ImageView logingif;
    EditText password,email,emailbox;
    Button blogin,userlogin,reset,cancel;
    String loginpassword,loginemail;//variable
    boolean passwordvisible;//visibility of password
    private FirebaseAuth authprofile;
    private static final String TAG="login";
    private FirebaseAuth.AuthStateListener authStateListener;
    private BroadcastReceiver broadcastReceiver;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password=findViewById(R.id.loginpassword);
        email=findViewById(R.id.loginemail);
        getSupportActionBar().hide();
        broadcastReceiver=new NetworkBroadcast() ;
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        //register page
        register=findViewById(R.id.registertext);
        register.setOnClickListener(v->{
            Intent reg=new Intent(login.this,register.class);
            startActivity(reg);

        });
        //login gif
        logingif=findViewById(R.id.logingif);
        Glide.with(this).asGif().load(R.raw.loginnew).into(logingif);


        authprofile= FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =  authprofile.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        //forgotpassword
        forgot=findViewById(R.id.forgot);
        emailbox=findViewById(R.id.emailbox);
        cancel=findViewById(R.id.btncancel);
        reset=findViewById(R.id.btnreset);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(login.this);
                View dialogView=getLayoutInflater().inflate(R.layout.forgotpassword,null);
                EditText emailbox=dialogView.findViewById(R.id.emailbox);
                builder.setView(dialogView);
                AlertDialog dialog=builder.create();
                dialogView.findViewById(R.id.btnreset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String useremail=emailbox.getText().toString();
                        if(TextUtils.isEmpty(useremail)&&!Patterns.EMAIL_ADDRESS.matcher(useremail).matches()){
                            emailbox.setError("Please enter your valid  registered email");
                            emailbox.requestFocus();

                        }
                       authprofile=FirebaseAuth.getInstance();
                        authprofile.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(login.this,"Please check your inbox for password reset link",Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                                else
                                {
                                    try{
                                        throw task.getException();
                                    }catch(FirebaseAuthInvalidUserException e){
                                        emailbox.setError("user doesnot exists");
                                    }catch(Exception e){
                                        Log.e(TAG,e.getMessage());
                                        Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                });

                //cancel button
                dialogView.findViewById(R.id.btncancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if(dialog.getWindow()!=null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();

            }
        });
                //show passsword
                password.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int End = 2;
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= password.getRight() - password.getCompoundDrawables()[End].getBounds().width()) {
                                int selection = password.getSelectionEnd();
                                if (passwordvisible) {
                                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24, 0, R.drawable.baseline_visibility_off_24, 0);
                                    //hide password
                                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                    passwordvisible = false;
                                } else {
                                    password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24, 0, R.drawable.baseline_visibility_24, 0);
                                    //show password
                                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                    passwordvisible = true;
                                }
                                password.setSelection(selection);
                                return true;
                            }
                        }
                        return false;
                    }
                });

        authprofile=FirebaseAuth.getInstance();
        //loginbutton
        userlogin=findViewById(R.id.loginButton);
        userlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginemail=email.getText().toString();
                loginpassword=password.getText().toString();
                if (TextUtils.isEmpty(loginemail)) {
                    email.requestFocus();
                    email.setError("Enter the email");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(loginemail).matches()) {
                    email.requestFocus();
                    email.setError("Enter valid Email");

                } else if (TextUtils.isEmpty(loginpassword)) {
                    password.requestFocus();
                    password.setError("Enter the  password");

                }
                 else if (loginpassword.length()!=10) {
                    password.requestFocus();
                    password.setError("Password length must be of 10 characters");

                } else if (isValidPassword(loginpassword.trim())) {

                    loginuser(loginemail,loginpassword);
                }
                else {
                    password.setError("Invalid Password Pattern");
                    password.requestFocus();


                }
            }
        });
    }

    private void loginuser(String loginuseremail, String loginuserpassword) {
        authprofile.signInWithEmailAndPassword(loginuseremail,loginuserpassword).addOnCompleteListener(login.this,new  OnCompleteListener<AuthResult>() {
            @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(login.this,"Logged in",Toast.LENGTH_SHORT).show();
                    Intent dash=new Intent(login.this,MainActivity.class);
                    startActivity(dash);
                    finish();
                }
                else{
                    try{
                        throw  task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        email.setError("User does not Exist,Register again");
                        email.requestFocus();
                    }catch(FirebaseAuthInvalidCredentialsException e){
                        email.setError("Check the email");
                        email.requestFocus();
                        password.setError("Check the passowrd");
                        password.requestFocus();
                    }catch(Exception e)
                    {
                        Log.e(TAG,e.getMessage());
                        Toast.makeText(login.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
   public boolean isValidPassword(final String password) {
       Pattern pattern;
       Matcher matcher;
       final String PASSWORD_PATTERN = "(?=.*[0-9])" +     //at least 1 digit
               "(?=.*[a-z])" +       //at least 1 lower case letter
               "(?=.*[A-Z])" +          //at least 1 upper case letter
               "(?=.*[a-zA-Z])" +         //any letter
               "(?=.*[@#$%^&+=])" +            //at least 1 special character
               "(?=\\S+$)" +                   //no white spaces
               ".{10,}" +                      //at least 10 characters
               "$";
       pattern = Pattern.compile(PASSWORD_PATTERN);
       matcher = pattern.matcher(password);
       return matcher.matches();
   }

   //check whether user is logged or not


    @Override
    protected void onStart() {
        super.onStart();
        if(authprofile.getCurrentUser()!=null){
            startActivity(new Intent(login.this,MainActivity.class));
            finish();
        }else {
            Toast.makeText(login.this,"Welcome to Explore Udupi ",Toast.LENGTH_SHORT).show();

        }
    }

}