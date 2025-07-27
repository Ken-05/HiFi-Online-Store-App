package com.example.hifi.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hifi.R;
import com.example.hifi.application.Services;
import com.example.hifi.business.AccessUsers;
import com.example.hifi.business.UserService;
import com.example.hifi.objects.User;

public class UserSignInActivity extends MenuActivity {
    TextView email,password;
    Button signUpButton;
    Button signInButton;
    AccessUsers accessUser;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        accessUser = new AccessUsers();
        userService = Services.getUserService();

        email = findViewById(R.id.enterEmail);
        password = findViewById(R.id.enterPassword);

        signUpButton = findViewById(R.id.goSignUp);
        signInButton = findViewById(R.id.signIn);

        //go to sign up page
        signUpButton.setOnClickListener(view -> {
            Intent intent = new Intent(UserSignInActivity.this, UserCreateAccountActivity.class);
            startActivity(intent);
        });

        //log into account
        signInButton.setOnClickListener(v -> {
            String givenEmail = email.getText().toString();
            String givenPassword = password.getText().toString();
            if(checkUserInfo(givenEmail, givenPassword)){
                Toast.makeText(UserSignInActivity.this,"LOGGED IN",Toast.LENGTH_SHORT).show();
                userService.setCurrentUser(accessUser.getUserByEmail(givenEmail));
                //future redirect to account page or edit profile
                Intent intent = new Intent(UserSignInActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(UserSignInActivity.this,"LOGIN FAILED",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkUserInfo(String anEmail, String aPassword){
        boolean result = false;
        User currentUser = accessUser.getUserByEmail(anEmail);
        if(currentUser != null && currentUser.getUserPassword().equals(aPassword)){
            result = true;
        }
        return result;
    }


}
