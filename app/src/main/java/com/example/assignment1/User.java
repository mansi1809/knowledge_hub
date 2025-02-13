package com.example.assignment1;

import android.widget.EditText;

public class User {
    String Name, Email, Password, UserName;

    public String getName() {
        return Name;
    }

    public void setName(String signname) {
        Name = signname;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String signemail) {
        Email = signemail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String signpassword) {
        Password = signpassword;
    }
    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public User(String signname, String signemail, String signpassword, String signuserName) {
        Name = signname;
        Email = signemail;
        Password = signpassword;
        UserName = signuserName;
    }

    public  User(){

    }
}
