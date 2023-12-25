package com.example.project39;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class businesslogin extends AppCompatActivity {

    EditText businessid,businessemail,businesspassword;
    Button buslogin;
    String bid,bemail,bpassword;
    boolean passwordvisible;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_businesslogin);

        //showpassword
        businesspassword=findViewById(R.id.businesspassword);
        businessid=findViewById(R.id.businessid);
        businessemail=findViewById(R.id.businessemail);
        businesspassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int End=2;
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(event.getRawX()>=businesspassword.getRight()-businesspassword.getCompoundDrawables()[End].getBounds().width()){
                        int selection= businesspassword.getSelectionEnd();
                        if(passwordvisible){
                            businesspassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24,0,R.drawable.baseline_visibility_off_24,0);
                            //hide password
                            businesspassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible=false;

                        }
                        else{
                            businesspassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24,0,R.drawable.baseline_visibility_24,0);
                            //show password
                            businesspassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible=true;

                        }
                        businesspassword.setSelection(selection);
                        return true;


                    }
                }
                return false;
            }
        });

        //validation

        buslogin=findViewById(R.id.businessloginbutton);
        buslogin.setOnClickListener(v->{
            bid=businessid.getText().toString();
            bemail=businessemail.getText().toString();
            bpassword=businesspassword.getText().toString();
            if(TextUtils.isEmpty(bid)){
                Toast.makeText(businesslogin.this,"Empty Fields",Toast.LENGTH_SHORT).show();
                businessid.requestFocus();
                businessid.setError("Required");

            }
            else if (TextUtils.isEmpty(bemail))
            {
                Toast.makeText(businesslogin.this,"Empty Fields",Toast.LENGTH_SHORT).show();
                businessemail.requestFocus();
                businessemail.setError("Required");

            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(bemail).matches())
            {
                Toast.makeText(businesslogin.this,"Re-Enter valid Email",Toast.LENGTH_SHORT).show();
                businessemail.requestFocus();
                businessemail.setError("Enter valid email");

            }
            else if (TextUtils.isEmpty(bpassword))
            {
                Toast.makeText(businesslogin.this,"Empty Fields",Toast.LENGTH_SHORT).show();
                businesspassword.requestFocus();
                businesspassword.setError("Required");

            }
            else if (bpassword.length()!=10)
            {
                Toast.makeText(businesslogin.this,"Invalid password",Toast.LENGTH_SHORT).show();
                businesspassword.requestFocus();
                businesspassword.setError("Password lenght must be of 10 characters");

            }else {
                Intent businessusertintent=new Intent(businesslogin.this,busineesprofile.class);
                startActivity(businessusertintent);
            }
        });


    }
}