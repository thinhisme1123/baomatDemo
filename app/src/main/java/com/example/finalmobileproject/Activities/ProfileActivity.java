package com.example.finalmobileproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalmobileproject.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends BaseActivity{

    private ActivityProfileBinding binding;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getAction();
    }

    private void getAction() {
        binding.orderHistoryBtn.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this, OrderHistory.class)));
        binding.resetPassBtn.setOnClickListener(v -> {
            if (user != null) {
                String email = user.getEmail();

                auth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Email sent!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error occur!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        binding.backBtn.setOnClickListener(v -> finish());
    }
}
