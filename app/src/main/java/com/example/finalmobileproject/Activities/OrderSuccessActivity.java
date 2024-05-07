package com.example.finalmobileproject.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalmobileproject.databinding.ActivityOrderSuccessBinding;

public class OrderSuccessActivity extends BaseActivity{
    ActivityOrderSuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        continueOrder();
    }

    private void continueOrder() {
        binding.continueOrderBtn.setOnClickListener(v -> startActivity(new Intent(OrderSuccessActivity.this, MainActivity.class)));
    }
}
