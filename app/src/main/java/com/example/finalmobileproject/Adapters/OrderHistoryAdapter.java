package com.example.finalmobileproject.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalmobileproject.Activities.DetailOrder;
import com.example.finalmobileproject.Domain.Order;
import com.example.finalmobileproject.R;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.viewholder>{

    ArrayList<Order> items;
    Context context;

    public OrderHistoryAdapter(ArrayList<Order> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(context).inflate(R.layout.viewholder_order_history, parent, false);
        return new viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.viewholder holder, int position) {
        holder.orderDateTxt.setText(items.get(position).getTimeOrder() + " " +  items.get(position).getDateOrder());
        holder.orderIdTxt.setText("Order" + items.get(position).getOrderId());
        holder.amountTxt.setText("$" + items.get(position).getTotalAmount());
        holder.statusTxt.setText(items.get(position).getStatus());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailOrder.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView orderIdTxt, orderDateTxt, amountTxt, statusTxt;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            orderDateTxt = itemView.findViewById(R.id.orderDateTxt);
            orderIdTxt = itemView.findViewById(R.id.orderIdTxt);
            amountTxt = itemView.findViewById(R.id.amountTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);
        }
    }
}
