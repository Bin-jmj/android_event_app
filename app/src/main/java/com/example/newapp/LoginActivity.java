package com.example.newapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

//    creating variables
    private TextInputEditText userNameEdit, passwordEdit;
    private Button loginBtn;
    private TextView newUserTV;
    private FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        initialize variable
        userNameEdit = findViewById(R.id.idEditUserName);
        passwordEdit = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        newUserTV = findViewById(R.id.idTVNewUser);
        mAuth  = FirebaseAuth.getInstance();
        loadingPB = findViewById(R.id.idPBLoading);
//        adding click event listener
        newUserTV.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view -> {
//                hiding progress bar
            loadingPB.setVisibility(View.VISIBLE);
//                getting data from text box
            String email = userNameEdit.getText().toString();
            String password = passwordEdit.getText().toString();
//                check for validation
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please Enter Your Credential", Toast.LENGTH_SHORT).show();
                return;
            }
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
//                            we hiding progress bar
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Please Enter Valid Username and Password", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
//        check if user is already sign in
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
//            open activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}