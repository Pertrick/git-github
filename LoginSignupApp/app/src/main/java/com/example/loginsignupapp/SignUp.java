package com.example.loginsignupapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    //variables
    private TextInputLayout regName, regUsername, regEmail, regPhoneNo, regPassword;
    private Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference  reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //hooks to all xml elements in activity_sign_up.xml
        regName = findViewById(R.id.reg_name);
        regUsername = findViewById(R.id.reg_username);
        regEmail = findViewById(R.id.reg_email);
        regPhoneNo = findViewById(R.id.reg_phoneNo);
        regPassword = findViewById(R.id.reg_password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                //Get all the values
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String phoneNo = regPhoneNo.getEditText().getText().toString();
                String password = regPassword.getEditText().getText().toString();

                UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);

                reference.child(phoneNo).setValue(helperClass);
            }
        });

        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });




    }








    private Boolean validateName(){
            String val = regName.getEditText().getText().toString();

            if(val.isEmpty()){
                regName.setError("Field cannot be empty");
                return false;
            }
            else{
                regName.setError(null);
                regName.setErrorEnabled(false);
                return true;
            }

    }

    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\S+$)";

        if(val.isEmpty()){
            regUsername.setError("Field cannot be empty");
            return  false;
        } else if(val.length()>=15){
            regUsername.setError("Username too long");
            return false;
        }

        else  if(!val.matches(noWhiteSpace)){
            regUsername.setError("white spaces are not allowed");
            return  false;
        }

        else{
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()){
            regEmail.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid email address");
            return false;
        }
        else{
            regEmail.setError(null);
            return true;
        }

    }

    private Boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString();

        if(val.isEmpty()){
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }
        else{
            regPhoneNo.setError(null);
            return true;
        }
    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +      // at least 1 digit
                //"(?=.*[a-z])" +     // at least 1 lower case letter
                //"(?=.*[A-Z])" +    // at least 1 upper case letter
                "(?=.*[a-zA-Z])" +    //any letter
                "(?=.*[@#$%^&+=])" +   //at least 1 special character
                "(?=\\S+$)" +          // no white spaces
                ".{4,}" +              // at least 4 characters
                "$";

        if(val.isEmpty()){
            regPassword.setError("Field cannot be empty");
            return false;
        }else if(!val.matches(passwordVal)) {
            regPassword.setError("password is too weak");
            return false;
        }
        else{
            regPassword.setError(null);
            return true;
        }

    }



    public void registerUser(View view){


        //Get all the values
        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNo = regPhoneNo.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNo, password);

        reference.child(phoneNo).setValue(helperClass);

       if(!validateName() | !validatePassword() | !validateUsername() | !validateEmail() | !validatePhoneNo()){

           return;
        }
    }






}

