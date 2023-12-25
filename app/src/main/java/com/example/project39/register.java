package com.example.project39;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class register extends AppCompatActivity {
    EditText regpassword,regusername,regemail;
    boolean regpasswordvisible;
    String signuppassword,signupusername,signupemail;
    Button register;
    private static final String TAG="register";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regpassword=findViewById(R.id.regpassword);
        regemail=findViewById(R.id.regemail);
        regusername=findViewById(R.id.regusername);
        register=findViewById(R.id.registerbtn);
        getSupportActionBar().hide();
        //show passowrd
        regpassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int End=2;
                if(event.getAction()== MotionEvent.ACTION_UP){
                    if(event.getRawX()>=regpassword.getRight()-regpassword.getCompoundDrawables()[End].getBounds().width()){
                        int selection= regpassword.getSelectionEnd();
                        if(regpasswordvisible){
                            regpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24 ,0,R.drawable.baseline_visibility_off_24,0);
                            //hide password
                            regpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            regpasswordvisible=false;

                        }
                        else{
                            regpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24,0,R.drawable.baseline_visibility_24,0);
                            //show password
                            regpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            regpasswordvisible=true;

                        }
                        regpassword.setSelection(selection);
                        return true;


                    }
                }
                return false;
            }
        });
       // validations
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signuppassword=regpassword.getText().toString();
                signupusername=regusername.getText().toString();
                signupemail=regemail.getText().toString();

                if(TextUtils.isEmpty(signupusername)){
                    Toast.makeText(register.this,"Empty username",Toast.LENGTH_SHORT).show();
                    regusername.requestFocus();
                    regusername.setError("Required");

                }
                else if (TextUtils.isEmpty(signupemail))
                {
                    Toast.makeText(register.this,"Empty Email",Toast.LENGTH_SHORT).show();
                    regemail.requestFocus();
                    regemail.setError("Required");

                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(signupemail).matches())
                {
                     Toast.makeText(register.this,"Re-Enter valid Email",Toast.LENGTH_SHORT).show();
                     regemail.requestFocus();
                     regemail.setError("Enter valid Email");

                 }
                else if (TextUtils.isEmpty(signuppassword))
                {
                    Toast.makeText(register.this,"Empty password",Toast.LENGTH_SHORT).show();
                    regpassword.requestFocus();
                    regpassword.setError("Required");

                }
                else if (signuppassword.length()!=10)
                {
                    Toast.makeText(register.this,"Invalid password",Toast.LENGTH_SHORT).show();
                    regpassword.requestFocus();
                    regpassword.setError("Password length must be of 10 characters");
                    
                } else if (isValidPassword(signuppassword.trim())) {

                    registeruser(signupusername,signupemail,signuppassword);
                }else {
                    regpassword.requestFocus();
                    regpassword.setError("Invalid password pattern");
                    }
            }
        });
    }
    //FIREBASE CONNECTION
    private void registeruser(String signupusername, String signupemail, String signuppassword) {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(signupemail,signuppassword).addOnCompleteListener(register.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"User Registered Successfully",Toast.LENGTH_SHORT).show();
                            FirebaseUser firebaseUser=auth.getCurrentUser();

                            //update display the user
                            UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(signupusername).build();
                            firebaseUser.updateProfile(profileChangeRequest);

                            //store in realtime database
                            ReadWriteUserDetails writeuserdetails=new ReadWriteUserDetails(signupusername,signupemail,signuppassword);

                            //database reference
                            DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered User");
                            referenceProfile.child(firebaseUser.getUid()).setValue(writeuserdetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //send verifcation
                                        firebaseUser.sendEmailVerification();
                                        Toast.makeText(register.this,"User Registered Successfully Check your email",Toast.LENGTH_SHORT).show();
                                        Intent signup=new Intent(register.this,MainActivity.class);
                                        signup.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(signup);
                                        finish();
                                    }else
                                    {
                                        Toast.makeText(register.this," Registered Failed Try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else{
                            try {   //exception handling
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                regpassword.setError("Password is weak ,kindly use Alphabets,number and special character ");
                                regpassword.requestFocus();
                            }catch (FirebaseAuthInvalidCredentialsException e){
                                regpassword.setError("Invalid Email ,kindly re-enter");
                                regpassword.requestFocus();
                            }catch(FirebaseAuthUserCollisionException e){
                                regpassword.setError("User is already registered with this email,use another email");
                                regpassword.requestFocus();
                            }catch(Exception e){
                                Log.e(TAG,e.getMessage());
                                Toast.makeText(register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
}