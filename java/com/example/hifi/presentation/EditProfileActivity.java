package com.example.hifi.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hifi.R;
import com.example.hifi.application.Services;
import com.example.hifi.business.AccessUsers;
import com.example.hifi.business.UserService;
import com.example.hifi.objects.User;

public class EditProfileActivity extends MenuActivity {
    EditText textInputEditTextFullName, textInputEditTextAddr, textInputEditTextPassword;
    Button updateButton;
    AccessUsers accessUser;
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_account);
        accessUser = new AccessUsers();
        userService = Services.getUserService();
        User currUser = userService.getCurrentUser();

        // LOAD signup UI, and load all the inputs from user
        textInputEditTextFullName = findViewById(R.id.editName);
        textInputEditTextPassword = findViewById(R.id.editPass);
        textInputEditTextAddr = findViewById(R.id.editAddr);

        updateButton = findViewById(R.id.update);

        updateButton.setOnClickListener(v -> {
            try {
                String fullname, password, addr;
                fullname = textInputEditTextFullName.getText().toString();
                addr = textInputEditTextAddr.getText().toString();
                password = textInputEditTextPassword.getText().toString();

                // update user information in database

                if (fullname.equals("")) {
                    fullname = currUser.getUserName();
                }

                if (password.equals("")) {
                    password = currUser.getUserPassword();
                }

                if (addr.equals("")) {
                    addr = currUser.getAddress();
                }
                currUser.setName(fullname);
                currUser.setAddress(addr);
                currUser.setPassword(password);

                accessUser.updateUser(currUser);
                Toast.makeText(EditProfileActivity.this, "Updated User\nName:"+fullname+" Password:"+password+" Address:"+addr, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            catch (Exception e)
            {
              System.out.println("Failed to update user:"+e.getMessage());
            }
        });
    }
}