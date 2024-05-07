package com.example.finalmobileproject.Activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobileproject.Adapters.FoodListAdapter;
import com.example.finalmobileproject.Adapters.OrderHistoryAdapter;
import com.example.finalmobileproject.Domain.Foods;
import com.example.finalmobileproject.Domain.Order;
import com.example.finalmobileproject.databinding.ActivityOrderHistoryBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistory extends BaseActivity {

    private ActivityOrderHistoryBinding binding;
    private RecyclerView.Adapter adapterOrderHistory;
    private String userId;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initList();

        binding.backBtn.setOnClickListener(v -> finish());
    }

    private void initList() {
        DatabaseReference myRef = database.getReference("Order");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Order> list = new ArrayList<>();

        Query query = myRef.orderByChild("userId").equalTo(user.getUid().toString());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Order.class));
                    }
                    if (list.size() > 0) {
                        binding.orderHistoryView.setLayoutManager(new GridLayoutManager(OrderHistory.this, 1));
                        adapterOrderHistory = new OrderHistoryAdapter(list);
                        binding.orderHistoryView.setAdapter(adapterOrderHistory);
                    }
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
