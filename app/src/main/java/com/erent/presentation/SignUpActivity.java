package com.erent.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.erent.R;
import com.erent.application.Services;
import com.erent.logic.IUserLogic;
import com.erent.logic.UserLogic;
import com.erent.persistence.stubs.UserPersistence;

public class SignUpActivity extends AppCompatActivity {

    private IUserLogic uLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.sign_up);

        uLogic = Services.getUserLogic();
    }

    public void signUp(View view) {
        EditText usernameField;
        EditText passwordField;
        String username;
        String password;

        /* get the fields the text is typed into */
        usernameField = (EditText) findViewById(R.id.inputButtonUsername);
        passwordField = (EditText) findViewById(R.id.inputButtonPassword);

        /* get the text from the fields */
        username = usernameField.getText().toString();
        password = passwordField.getText().toString();

        if(uLogic.userExists(username)) {
            usernameField.setError("Username is already taken");
        } else if (username.length() < 5) {
            usernameField.setError("Username must be at least 5 characters");
        }
        else {
            if(uLogic.validPassword(password)) {

                uLogic.createNewUser(username, password);

                Intent switchActivityIntent = new Intent(this, LoginActivity.class);
                startActivity(switchActivityIntent);
            } else {
                passwordField.setError("Password must be 8 characters and contain 1 number");
            }
        }
    }

    public void goToLogin(View view) {
        Intent switchActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(switchActivityIntent);
    }

}