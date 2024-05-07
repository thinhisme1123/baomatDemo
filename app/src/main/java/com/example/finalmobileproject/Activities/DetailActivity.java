package com.example.finalmobileproject.Activities;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.finalmobileproject.Domain.Foods;
import com.example.finalmobileproject.Helper.ChangeNumberItemsListener;
import com.example.finalmobileproject.Helper.ManagementCart;
import com.example.finalmobileproject.R;
import com.example.finalmobileproject.databinding.ActivityDetailBinding;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Foods object;
    private int num = 1;
    private ManagementCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managmentCart = new ManagementCart(this);

        binding.backBtn.setOnClickListener(v -> finish());

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);

        binding.priceTxt.setText("$" + object.getPrice());
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar() + " Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num * object.getPrice() + "$"));

        binding.plusBtn.setOnClickListener(v -> {
            num = num + 1;
            binding.numTxt.setText(num + " ");
            binding.totalTxt.setText("$" + (num * object.getPrice()));
        });

        binding.minusBtn.setOnClickListener(v -> {
            if (num > 1) {
                num = num - 1;
                binding.numTxt.setText(num + "");
                binding.totalTxt.setText("$" + (num * object.getPrice()));
            }
        });

        binding.addBtn.setOnClickListener(v -> {
//            object.setNumberInCart(num);
//            managmentCart.insertFood(object);
            ArrayList<Foods> listFoods = managmentCart.getListCart();
            boolean existAlready = false;
            int numExist = 0;
            for (int i = 0; i < listFoods.size(); i++) {
                if (listFoods.get(i).getTitle().equals(object.getTitle())) {
                    existAlready = true;
                    numExist = listFoods.get(i).getNumberInCart();
                    break;
                }
            }
            if (existAlready) {
                object.setNumberInCart(numExist + num);
                managmentCart.insertFood(object);
            } else {
                object.setNumberInCart(num);
                managmentCart.insertFood(object);
            }
        });
    }

    private void getIntentExtra() {
        object = (Foods) getIntent().getSerializableExtra("object");
    }
}
