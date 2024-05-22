package com.example.finalmobileproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.finalmobileproject.R;
import com.example.finalmobileproject.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends BaseActivity {
    ActivitySignupBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        setVariable();
        toLogin();
    }

    private void toLogin() {
        binding.toLoginBtn.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();
            String confirmPassword = binding.confirmPassEdt.getText().toString();

            if (!isValidEmail(email)) {
                Toast.makeText(SignupActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(password) || password.length() < 6) {
                Toast.makeText(SignupActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(SignupActivity.this, "Password does not matches", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, task -> {
                if (task.isSuccessful()) {
                    Log.i(TAG, "User registration successful");
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Check mail for verifying", Toast.LENGTH_SHORT).show();
                                }
                            });
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    Toast.makeText(SignupActivity.this, "Signup successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Log.e(TAG, "User registration failed", task.getException());
                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(SignupActivity.this, "Registration failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
