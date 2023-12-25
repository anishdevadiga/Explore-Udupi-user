package com.example.project39;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.PictureKt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class userprofile extends AppCompatActivity {
    CardView userprofile, userlogout, changepassword, book;
    ShapeableImageView profile,imagebox; //display
     //upload
    FirebaseAuth authprofile;
     private TextView profileuser,profileemail;
     private String username,email;
     StorageReference storage;
     private static final int PICK_IMAGE_REQUEST=1;
     private Uri uriImage;
     private FirebaseUser firebaseUser;
     EditText currentpassword,newpassword;
     boolean passwordvisible,passvisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        //userprofile = findViewById(R.id.profile);
        userlogout = findViewById(R.id.logout);
        changepassword = findViewById(R.id.chnagepassword);
        getSupportActionBar().hide();
        //book = findViewById(R.id.book);
        profileuser=findViewById(R.id.usernamepro);
        profileemail=findViewById(R.id.useremail);
        book=findViewById(R.id.aboutapp);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userprofile.this,aboutapp.class));
                finish();
            }
        });

        //logout
        userlogout=findViewById(R.id.logout);
        userlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Dialog logdial=new Dialog(userprofile.this);
                logdial.setContentView(R.layout.logout_dialog);
                logdial.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                logdial.show();
                logdial.findViewById(R.id.btnlogcancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logdial.dismiss();
                    }
                });
                logdial.findViewById(R.id.btnlogout).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        authprofile=FirebaseAuth.getInstance();
                        authprofile.signOut();
                        Intent logout=new Intent(userprofile.this,login.class);
                        logout.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logout);
                        finish();


                    }
                });
            }
        });



        //chnagepassword
        changepassword=findViewById(R.id.chnagepassword);
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.appcompat.app.AlertDialog.Builder builder=new androidx.appcompat.app.AlertDialog.Builder(userprofile.this);
                //View dialogpass=getLayoutInflater().inflate(R.layout.changepassword,null);;
               // EditText emailbox=dialogView.findViewById(R.id.emailbox);
                Dialog d=new Dialog(userprofile.this);
                d.setContentView(R.layout.changepassword);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                d.show();
                EditText currentpass=d.findViewById(R.id.currentpassbox);
                EditText newpass=d.findViewById(R.id.newpassbox);
                authprofile=FirebaseAuth.getInstance();
                FirebaseUser firebaseUser=authprofile.getCurrentUser();
                currentpass.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int End = 2;
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= currentpass.getRight() - currentpass.getCompoundDrawables()[End].getBounds().width()) {
                                int selection = currentpass.getSelectionEnd();
                                if (passwordvisible) {
                                    currentpass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24, 0, R.drawable.baseline_visibility_off_24, 0);
                                    //hide password
                                    currentpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                    passwordvisible = false;
                                } else {
                                    currentpass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24, 0, R.drawable.baseline_visibility_24, 0);
                                    //show password
                                    currentpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                    passwordvisible = true;
                                }
                                currentpass.setSelection(selection);
                                return true;
                            }
                        }
                        return false;
                    }
                });
                newpass.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        final int End = 2;
                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= newpass.getRight() - newpass.getCompoundDrawables()[End].getBounds().width()) {
                                int selection = currentpass.getSelectionEnd();
                                if (passvisible) {
                                    newpass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24, 0, R.drawable.baseline_visibility_off_24, 0);
                                    //hide password
                                    newpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                    passvisible = false;
                                } else {
                                    newpass.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_key_24, 0, R.drawable.baseline_visibility_24, 0);
                                    //show password
                                    newpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                    passvisible = true;
                                }
                                newpass.setSelection(selection);
                                return true;
                            }
                        }
                        return false;
                    }
                });
                d.findViewById(R.id.btncancelpass).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                d.findViewById(R.id.btnchange).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String passnew=newpass.getText().toString();
                        String passcurrent=currentpass.getText().toString();
                        if(currentpass.getText().toString().matches(newpass.getText().toString())){
                            newpass.setError(" New Pasword cannot be same as old password");
                            newpass.requestFocus();
                        } else if (passnew.length()!=10 &&!isValidPassword(passnew.trim())) {
                            newpass.setError("Password does not  match the pattern");
                            newpass.requestFocus();
                        } else if (TextUtils.isEmpty(passnew)) {
                            newpass.setError("Enter the Password");
                            newpass.requestFocus();

                        } else if (TextUtils.isEmpty(passcurrent)) {
                            currentpass.setError("Enter the Current Password and Authenicate ");
                            currentpass.requestFocus();
                        }
                        else {
                            firebaseUser.updatePassword(passnew).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(userprofile.this,"Password Changed",Toast.LENGTH_LONG).show();
                                        d.dismiss();

                                    }else{
                                        try{
                                            throw task.getException();
                                        }catch(Exception e){
                                            Toast.makeText(userprofile.this,"Error has happended",Toast.LENGTH_LONG).show();
                                        }
                                    }

                                }
                            });

                        }
                    }
                });
                d.findViewById(R.id.btnauth).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String passcurrent=currentpass.getText().toString();
                        //String passnew=newpass.getText().toString();

                        if(firebaseUser.equals(""))
                        {
                            Toast.makeText(userprofile.this,"User details not availabe",Toast.LENGTH_LONG).show();
                        }

                        if (TextUtils.isEmpty(passcurrent)) {
                                currentpass.setError("Enter the current password");
                                currentpass.requestFocus();
                            }else{

                            AuthCredential credential= EmailAuthProvider.getCredential(firebaseUser.getEmail(),passcurrent);
                            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(userprofile.this,"Password has been verified",Toast.LENGTH_LONG).show();


                                    }else {
                                        try{
                                            throw task.getException();

                                        }catch (Exception e){
                                            currentpass.setError("Invalid Passoword");
                                            currentpass.requestFocus();
                                            Toast.makeText(userprofile.this,e.getMessage(),Toast.LENGTH_LONG).show();

                                        }
                                    }
                                }
                            });

                            }
                    }});
            }
        });



                //bottom navigatin bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnavigationbar);
        bottomNavigationView.setSelectedItemId(R.id.bottom_person);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
               /* case R.id.bottom_map:
                    startActivity(new Intent(getApplicationContext(), map.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;

                case R.id.bottom_search:
                    startActivity(new Intent(getApplicationContext(), search.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;*/
                case R.id.bottom_person:
                    return true;

            }
            return false;
        });
        //profile pic upload
       userprofile=findViewById(R.id.chnageprofile);
        profile = findViewById(R.id.userprofile);
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDialog.Builder builder = new AlertDialog.Builder(userprofile.this);
                //View dialogView = getLayoutInflater().inflate(R.layout.upload_pic_dialog, null);
                Dialog dialogView=new Dialog(userprofile.this);
                dialogView.setContentView(R.layout.upload_pic_dialog);
                dialogView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogView.show();
                imagebox = dialogView.findViewById(R.id.imagebox);
                authprofile=FirebaseAuth.getInstance();
               FirebaseUser firebaseUser=authprofile.getCurrentUser();
               storage= FirebaseStorage.getInstance().getReference("DisplayPics");
               Uri uri=firebaseUser.getPhotoUrl();
               //set user current DP in imageView(if uploades already)
                Picasso.with(userprofile.this).load(uri).into(imagebox);
                imagebox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFilechooser();
                    }
                });
                dialogView.findViewById(R.id.btnupload).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UploadPic();
                        dialogView.dismiss();

                    }
                });

                dialogView.findViewById(R.id.btncancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogView.dismiss();
                    }
                });
            }
        });

      authprofile=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=authprofile.getCurrentUser();
        if(firebaseUser==null){
            Toast.makeText(userprofile.this,"User details not availabe",Toast.LENGTH_LONG).show();
        }else{
            showuserprofile(firebaseUser);
        }


    }






    private void UploadPic() {
        if(uriImage!=null){
            StorageReference file=storage.child(authprofile.getCurrentUser().getUid()+"."+ getFileExtension(uriImage));
            file.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloaduri=uri;
                                FirebaseUser firebaseUser=authprofile.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(downloaduri).build();
                                firebaseUser.updateProfile(profileUpdates);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(userprofile.this,e.getMessage(),Toast.LENGTH_LONG).show();

                            }
                        });
                }
            });
        }else{
            Toast.makeText(userprofile.this,"No file selected",Toast.LENGTH_LONG).show();

        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }


    private void openFilechooser() {
        Intent intent=new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST &&resultCode==RESULT_OK && data!=null && data.getData()!=null){
            uriImage=data.getData();
            imagebox.setImageURI(uriImage);
        }
    }

    private void showuserprofile(FirebaseUser firebaseUser) {
        String userid= firebaseUser.getUid();
        //Extracting user data
        DatabaseReference referenceProfile= FirebaseDatabase.getInstance().getReference("Registered User");
        referenceProfile.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteUserDetails readuser=snapshot.getValue(ReadWriteUserDetails.class);
                if(readuser!=null){
                    username=firebaseUser.getDisplayName();
                    email=firebaseUser.getEmail();
                    profileuser.setText(username);
                    profileemail.setText(email);
                    Uri uriprofile=firebaseUser.getPhotoUrl();
                    Picasso.with(userprofile.this).load(uriprofile).into(profile);

                }else {
                    Toast.makeText(userprofile.this,"Image not shown",Toast.LENGTH_LONG).show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(userprofile.this,"something went wrong",Toast.LENGTH_LONG).show();

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

