package com.example.project39;

public class ReadWriteUserDetails {
    public String username,email,password;

    public ReadWriteUserDetails(){};
    public ReadWriteUserDetails(String signuser,String signemail,String signpassword){
        this.username=signuser;
        this.email=signemail;
        this.password=signpassword;

    }
}
