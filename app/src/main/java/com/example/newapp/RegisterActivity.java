package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

//    creating variable for edit text and textview
//    firebase auth , button and progress bar
    private TextInputEditText userNameEdit ,passwordEdit, confirmPassEdit;
    private TextView loginTV;
    private Button registerBtn;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameEdit = findViewById(R.id.idEditUserName);
        passwordEdit = findViewById(R.id.idEdtPassword);
        loadingPB = findViewById(R.id.idPBLoading);
        confirmPassEdit =findViewById(R.id.idEdtConfirmPassword);
        loginTV = findViewById(R.id.idTVLoginUser);
        registerBtn = findViewById(R.id.idBtnRegister);
        mAuth = FirebaseAuth.getInstance();

//        adding on click event for login
        loginTV.setOnClickListener(view -> {
//                open login activity
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

//        adding click listener for register button
        registerBtn.setOnClickListener(view -> {
            //view progress bar
            loadingPB.setVisibility(View.VISIBLE);

//                getting data from our edit text
            String userName = userNameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            String confirmPass = confirmPassEdit.getText().toString();

            if (!password.equals(confirmPass)) {
                Toast.makeText(RegisterActivity.this, "Password not Match", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(password)) {
//                    check for empty inputs
                Toast.makeText(RegisterActivity.this, "Please Enter you Credentials", Toast.LENGTH_SHORT).show();
            }
            else {
                mAuth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(task -> {
//                            check if the task if successful
                    if (task.isSuccessful()) {
//                                hiding progress bar
                        loadingPB.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, "User Registered...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
//                                the above fail
                        Toast.makeText(RegisterActivity.this, "Fail to Register User ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}