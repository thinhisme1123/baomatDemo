package com.example.finalmobileproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobileproject.Adapters.FoodListAdapter;
import com.example.finalmobileproject.Adapters.OrderDetailAdapter;
import com.example.finalmobileproject.Domain.Foods;
import com.example.finalmobileproject.Domain.Order;
import com.example.finalmobileproject.databinding.ActivityDetailOrderBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailOrder extends BaseActivity {

    private ActivityDetailOrderBinding binding;
    private RecyclerView.Adapter adapterDetailOrder;

    private Order order;

    private DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        initList();
    }

    private void initList() {
        ArrayList<Foods> list;
        list = order.getFood();

        binding.foodOrderDetailTxt.setLayoutManager(new GridLayoutManager(DetailOrder.this, 1));
        adapterDetailOrder = new OrderDetailAdapter(list);
        binding.foodOrderDetailTxt.setAdapter(adapterDetailOrder);

        binding.orderIdDetailTxt.setText("OrderID: " + order.getOrderId());
        binding.orderDateDetailTxt.setText(order.getTimeOrder() + " " + order.getDateOrder());
        binding.totalFeeDetailTxt.setText("Total Fee: $" + order.getTotalAmount());
        binding.addressOrderDetailTxt.setText("Delivery to: " + order.getAddress());
        binding.phoneOrderDetailTxt.setText("Phone Number: " + order.getPhoneNumber());
        binding.noteOrderTxt.setText("Note: " + order.getNoteOrder());

        binding.backBtn.setOnClickListener(v -> finish());
        binding.cancelOrderbtn.setOnClickListener(v -> {
            Date orderTime = convertStringToDate(order.getTimeOrder());

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            String currentTimeString = timeFormat.format(calendar.getTime());

            Date currentTime = convertStringToDate(currentTimeString);

            long timeDifferenceMillis = Math.abs(currentTime.getTime() - orderTime.getTime());
            long timeDifferenceMinutes = timeDifferenceMillis / (60 * 1000);

            if (timeDifferenceMinutes >= 30 || order.getStatus().equals("Accepted")) {
                Toast.makeText(this, "Can not cancel order!", Toast.LENGTH_SHORT).show();
            } else if (order.getStatus().equals("Cancelled")) {
                Toast.makeText(this, "This order has been cancelled!", Toast.LENGTH_SHORT).show();
            } else {
                ordersRef.child("Order").child(String.valueOf(order.getOrderId())).child("status").setValue("Cancelled");
                Toast.makeText(this, "Cancel successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DetailOrder.this, ProfileActivity.class));
            }
        });
    }

    private void getIntentExtra() {
        order = (Order) getIntent().getSerializableExtra("object");
    }

    private Date convertStringToDate(String timeString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        try {
            return dateFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}