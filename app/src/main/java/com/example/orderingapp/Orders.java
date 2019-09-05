package com.example.orderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.orderingapp.Common.Common;
import com.example.orderingapp.Model.Category;
import com.example.orderingapp.Model.Items;
import com.example.orderingapp.Model.Order;
import com.example.orderingapp.ViewHolder.Details_Activity;
import com.example.orderingapp.ViewHolder.OrdersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Orders extends AppCompatActivity {
    private ArrayList<Order> mOrderList=new ArrayList<>();
    private RecyclerView recyclerMenu;
    private RecyclerView.LayoutManager layoutManager;
    private OrdersAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("User").child("Order");

        //Retrieve Data
        getData(table_user);
    }

    private void getData(DatabaseReference table_user) {
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Order o = dataSnapshot1.getValue(Order.class);
                    mOrderList.add(o);
                }
                createRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void createRecyclerView() {
        recyclerMenu = findViewById(R.id.recycler_orders);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new OrdersAdapter(mOrderList,getApplicationContext());

        recyclerMenu.setLayoutManager(layoutManager);
        recyclerMenu.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                Toast.makeText(Orders.this,"",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Orders.this, Details_Activity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
