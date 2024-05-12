package com.example.finalmobileproject.Activities;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.example.finalmobileproject.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        setVariables();
    }

    @Override
    public void onBackPressed() {
        ComponentName callingActivityName = getCallingActivity();
        if (callingActivityName != null) {
            String backActivity = callingActivityName.getClassName();
            if (backActivity.equals("com.example.finalmobileproject.Activities.MainActivity")) {
                Toast.makeText(LoginActivity.this, "Cannot go back", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        super.onBackPressed();
    }

    private void setVariables() {
        binding.loginBtn.setOnClickListener(v -> loginUser());

        binding.forgetPassBtn.setOnClickListener(v -> resetPassword());
    }

    private void loginUser() {
        String email = binding.userEdt.getText().toString().trim();
        String password = binding.passEdt.getText().toString().trim();

        if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(password)) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null && user.isEmailVerified()) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Please verify your email address!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(LoginActivity.this, "Authentication failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(LoginActivity.this, "Please enter valid email and password", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetPassword() {
        String email = binding.userEdt.getText().toString().trim();
        if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to send password reset email!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please enter a valid email address!", Toast.LENGTH_SHORT).show();
        }
    }
}
