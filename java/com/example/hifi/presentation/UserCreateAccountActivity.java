package com.example.hifi.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hifi.R;
import com.example.hifi.business.AccessUsers;
import com.example.hifi.objects.User;


public class UserCreateAccountActivity extends MenuActivity {
    EditText textInputEditTextFullName, textInputEditTextEmail, textInputEditTextPassword;
    Button buttonSignUp;
    AccessUsers accessUser;
    private static int userID = 2;
    
    private final static String[] ACCEPTABLE_EMAILS = {"test.com", "gmail.com", "outlook.com", "myumanitoba.ca", "yahoo.com", "hotmail.com"};
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        
        accessUser = new AccessUsers();
        // LOAD signup UI, and load all the inputs from user
        textInputEditTextFullName = findViewById(R.id.fullName);
        textInputEditTextPassword = findViewById(R.id.enterPassword);
        textInputEditTextEmail = findViewById(R.id.enterEmail);
        buttonSignUp = findViewById(R.id.createAccount);
        
        
        buttonSignUp.setOnClickListener(v -> {
            String fullname, username, password, email;
            fullname = textInputEditTextFullName.getText().toString();
            email = textInputEditTextEmail.getText().toString();
            password = textInputEditTextPassword.getText().toString();
            // add user information to database
            if (!fullname.equals("") && !email.equals("") && !password.equals("")) {
                if (isValidEmail(email)) {
                    accessUser.insertUser(new User(fullname, email, password, userID++ + ""));
                    Toast.makeText(UserCreateAccountActivity.this, "Account Creation for " + fullname + " successful.\nCongratulations!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserCreateAccountActivity.this, UserSignInActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserCreateAccountActivity.this, "This email is already being used or this email is invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }
    
    public void signInOnClick(View v) {
        Intent intent = new Intent(UserCreateAccountActivity.this, UserSignInActivity.class);
        startActivity(intent);
    }
    
    //check validity of email
    public boolean isValidEmail(String email) {
        boolean acceptable = false;
        boolean valid = false;
        String emailDomain = email.substring(email.indexOf("@") + 1);
        for (String acceptableEmail : ACCEPTABLE_EMAILS) {
            if (emailDomain.equals(acceptableEmail)) {
                acceptable = true;
                break;
            }
        }
        if (acceptable)//if it is an acceptable email
        {
            //check the database if it is already used
            User exist = accessUser.getUserByEmail(email);
            if (exist == null) {
                valid = true;
            }
        }
        return acceptable && valid;
    }
}
