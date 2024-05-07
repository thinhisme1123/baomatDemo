package com.example.finalmobileproject.Activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalmobileproject.R;
import com.example.finalmobileproject.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        toSignUp();

    }

    @Override
    public void onBackPressed() {
        ComponentName callingActivityName = getCallingActivity();
        if (callingActivityName != null) {
            Activity callingActivity = getActivityFromComponentName(callingActivityName);
            if (callingActivity != null) {
                String backActivity = callingActivity.getLocalClassName();
                if (backActivity.equals("MainActivity")) {
                    Toast.makeText(LoginActivity.this, "Can not back", Toast.LENGTH_SHORT).show();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    private Activity getActivityFromComponentName(ComponentName componentName) {
        try {
            Class<?> activityClass = Class.forName(componentName.getClassName());
            return (Activity) activityClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void toSignUp() {
        binding.toSignUpBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SignupActivity.class)));
    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(v -> {
            String email=binding.userEdt.getText().toString();
            String password=binding.passEdt.getText().toString();
            if(!email.isEmpty() && !password.isEmpty()){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, task -> {
                    if(task.isSuccessful()){
                        String loggedInUserEmail = mAuth.getCurrentUser().getEmail();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                Toast.makeText(LoginActivity.this, "Please fill username and password", Toast.LENGTH_SHORT).show();
            }
        });

        binding.forgetPassBtn.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            String email = binding.userEdt.getText().toString();
            if (!email.equals("")) {


                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Email sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Your email is not exist!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please enter email!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
