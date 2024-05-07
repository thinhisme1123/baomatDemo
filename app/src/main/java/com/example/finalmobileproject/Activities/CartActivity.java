package com.example.finalmobileproject.Activities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.finalmobileproject.Adapters.CartAdapter;
import com.example.finalmobileproject.Domain.Foods;
import com.example.finalmobileproject.Domain.Order;
import com.example.finalmobileproject.Helper.ChangeNumberItemsListener;
import com.example.finalmobileproject.Helper.ManagementCart;
import com.example.finalmobileproject.Helper.TinyDB;
import com.example.finalmobileproject.databinding.ActivityCartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagementCart managementCart;
    private double tax;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference();
    double totalAmount = 0.0;
    private long orderId;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managementCart = new ManagementCart(this);

        setVariable();
        calculateCart();
        placeOrder();
        initList();
    }

    private void initList() {
        if (managementCart.getListCart().isEmpty()) {
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managementCart.getListCart(), this, () -> calculateCart());
        binding.cardView.setAdapter(adapter);
    }

    private void calculateCart() {
        double percentTax = 0.02;
        double delivery = 10;

        tax = Math.round(managementCart.getTotalFee() * percentTax * 100.0) / 100;

        double total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemTotal = Math.round(managementCart.getTotalFee() * 100) / 100;

        binding.totalFeeTxt.setText("$" + itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(v -> finish());
    }

    private void placeOrder() {
        if (ordersRef != null) {
            totalAmount = 0;

            //get date place order
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String newDate = dateFormat.format(date).toString();

            //get time at the moment place order
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String currentTime = timeFormat.format(calendar.getTime());

            DatabaseReference childRef = ordersRef.child("Order");
            childRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        ArrayList<Order> dataList = new ArrayList<>();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Order data = dataSnapshot.getValue(Order.class);
                            dataList.add(data);
                        }
                        orderId = (long)dataList.size();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            
            binding.placeOrderBtn.setOnClickListener(v -> {
                String noteOrder;
                if (binding.noteEdt.getText().toString().isEmpty())
                    noteOrder = "No note!";
                else
                    noteOrder = binding.noteEdt.getText().toString();
                if (managementCart.getListCart().size() == 0) {
                    Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
                } else if (binding.addressEdt.getText().toString().isEmpty()){
                    Toast.makeText(this, "Address is empty", Toast.LENGTH_SHORT).show();
                } else if (binding.phoneEdt.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Phone Number is empty", Toast.LENGTH_SHORT).show();
                }else {
                    Order newOrder = new Order(user.getUid(),
                            managementCart.getTotalFee(),
                            managementCart.getListCart(),
                            orderId,
                            newDate,
                            user.getEmail().split("@")[0],
                            binding.addressEdt.getText().toString(),
                            "Pending",
                            binding.phoneEdt.getText().toString(),
                            currentTime,
                            noteOrder);

                    ordersRef.child("Order").child(String.valueOf(orderId)).setValue(newOrder);
                    Toast.makeText(this, "Order successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CartActivity.this, OrderSuccessActivity.class));
                }
            });
        } else {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }
    }
}
