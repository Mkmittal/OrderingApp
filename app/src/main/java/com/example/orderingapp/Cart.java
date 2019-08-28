package com.example.orderingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.orderingapp.Common.Common;
import com.example.orderingapp.Model.Order;
import com.example.orderingapp.ViewHolder.CartAdapter;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotalPrice;
    Button placeOrderButton;

    ArrayList<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    public Cart(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cart= Common.cart;
        getTotal();
        assignWorkToButton();
        createRecyclerview();
    }

    private void assignWorkToButton() {
        txtTotalPrice = findViewById(R.id.total);
        placeOrderButton = findViewById(R.id.place_order);

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPayment = new Intent(Cart.this,Payment.class);
                startActivity(toPayment);
            }
        });
    }

    private void createRecyclerview() {
        recyclerView = findViewById(R.id.recycler_cart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter= new CartAdapter(cart,this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void onIncreaseClick(int position) {
                cart.get(position).increaseQuantity();
                adapter.notifyItemChanged(position);
                getTotal();
            }

            @Override
            public void onDecreaseClick(int position) {
                if (Integer.parseInt(cart.get(position).getQuantity()) == 1) {
                    removeItem(position);
                    getTotal();
                } else {
                    cart.get(position).decreaseQuantity();
                    adapter.notifyItemChanged(position);
                    getTotal();
                }
            }
        });
    }

    public void removeItem(int position)
    {
        cart.remove(position);
        adapter.notifyItemRemoved(position);
    }
    public void getTotal(){
        txtTotalPrice=findViewById(R.id.total);
        int total = 0;
        for (Order order : cart)
            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
        txtTotalPrice.setText(String.valueOf(total));
    }

}
