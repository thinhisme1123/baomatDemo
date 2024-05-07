package com.example.finalmobileproject.Activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.finalmobileproject.Adapters.FoodListAdapter;
import com.example.finalmobileproject.Domain.Foods;
import com.example.finalmobileproject.databinding.ActivityListFoodsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListFoodsActivity extends BaseActivity {
    ActivityListFoodsBinding binding;
    private RecyclerView.Adapter adapterListFood;
    private int categoryId;
    private String categoryName;
    private String searchText;
    private boolean isSearch;
    private long timeId;
    private long priceId;
    private long locationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListFoodsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        initList();
        setVariable();
    }

    private void setVariable() {

    }

    private void initList() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        int flag = 0;

        Query query;
        if (isSearch) {
            query = myRef.orderByChild("Title").startAt(searchText).endAt(searchText + '\uf8ff');
            binding.titleTxt.setText("Search");
        } else {
            if (timeId == -1) {
                query = myRef.orderByChild("CategoryId").equalTo(categoryId);
                binding.titleTxt.setText(categoryName);
            } else {
                binding.titleTxt.setText("Search by criteria");
                query = myRef.orderByChild("LocationId").equalTo(locationId);
                flag = 1;
            }
        }
        if (flag == 0) {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot issue : snapshot.getChildren()) {
                            list.add(issue.getValue(Foods.class));
                        }
                        if (list.size() > 0) {
                            binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodsActivity.this, 2));
                            adapterListFood = new FoodListAdapter(list);
                            binding.foodListView.setAdapter(adapterListFood);
                        }
                        binding.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } else if (flag == 1) {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<Foods> list = new ArrayList<>();

                    for (DataSnapshot locationSnapshot : snapshot.getChildren()) {
                        Long snapshotTimeId = locationSnapshot.child("TimeId").getValue(Long.class);
                        Long snapshotPriceId = locationSnapshot.child("PriceId").getValue(Long.class);

                        if (snapshotTimeId != null && snapshotPriceId != null
                                && snapshotTimeId.equals(timeId) && snapshotPriceId.equals(priceId)) {
                            Foods food = locationSnapshot.getValue(Foods.class);
                            if (food != null) {
                                list.add(food);
                            }
                        }
                    }

                    if (!list.isEmpty()) {
                        binding.foodListView.setLayoutManager(new GridLayoutManager(ListFoodsActivity.this, 2));
                        adapterListFood = new FoodListAdapter(list);
                        binding.foodListView.setAdapter(adapterListFood);
                    }

                    binding.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Xử lý lỗi
                }
            });
        }
    }

    private void getIntentExtra() {
        isSearch = getIntent().getBooleanExtra("isSearch", false);
        categoryId = getIntent().getIntExtra("CategoryId", 0);
        categoryName = getIntent().getStringExtra("CategoryName");
        searchText = getIntent().getStringExtra("text");
        timeId = getIntent().getLongExtra("time", -1);
        priceId = getIntent().getLongExtra("price", -1);
        locationId = getIntent().getLongExtra("location", -1);

        Log.d("Check isSearch", isSearch + "");
        Log.d("Check time", timeId + "");
        binding.backBtn.setOnClickListener(v -> finish());
    }
}