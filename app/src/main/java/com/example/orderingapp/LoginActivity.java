package com.example.orderingapp;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.orderingapp.Common.Common;
import com.example.orderingapp.Model.Category;
import com.example.orderingapp.Model.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase firebaseDatabase;
    DatabaseReference category;

    public ArrayList<Category> values;
    public TextView textFullName;
    private RecyclerView recyclerMenu;
    private RecyclerView.LayoutManager layoutManager;
    private LoginAdapter mAdapter;
    public int number=0;
    public FirebaseAuth firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        number=0;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.apply();
            showHelp();
        }
            setContentView(R.layout.activity_login);
            Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Menu");
            setSupportActionBar(toolbar);

            //Initializing firebase
            firebase = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            category = firebaseDatabase.getReference("Category");

            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Common.cart.size() == 0) {
                        Snackbar.make(view, "Add Items First", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    } else {
                        Intent toCart = new Intent(LoginActivity.this, Cart.class);
                        startActivity(toCart);
                    }
                }
            });
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);

            //Set Name for User
            View headerView = navigationView.getHeaderView(0);
            textFullName = headerView.findViewById(R.id.textFullName);
            textFullName.setText(Common.currentUser.getName());
            createArrayList();
            category.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    loadMenu(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    private void createRecyclerView() {
        recyclerMenu = findViewById(R.id.recycler_menu);
        recyclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mAdapter = new LoginAdapter(values,getApplicationContext());

        recyclerMenu.setLayoutManager(layoutManager);
        recyclerMenu.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new LoginAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(int position)
            {
                Toast.makeText(LoginActivity.this,"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAddClick(int position)
            {
                number++;
                Category foodId = values.get(position);
                Common.cart.add(new Order(foodId.getImage(),foodId.getName(),foodId.getQUANTITY(),foodId.getPrice()));
                Toast.makeText(LoginActivity.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void createArrayList(){
        values = new ArrayList<>();
    }
    private void loadMenu(@org.jetbrains.annotations.NotNull DataSnapshot dataSnapshot) {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Category c = ds.getValue(Category.class);
            values.add(c);
        }
        createRecyclerView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // Handle the camera action
        } else if (id == R.id.nav_cart)
        {
                Intent toCart = new Intent(LoginActivity.this,Cart.class);
                startActivity(toCart);
        }
        else if (id == R.id.nav_orders) {
            Intent toOrders = new Intent(LoginActivity.this,Orders.class);
            startActivity(toOrders);

        } else if (id == R.id.nav_logout) {
            FirebaseAuth fAuth = FirebaseAuth.getInstance();
            fAuth.signOut();
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            Common.currentUser=null;
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void showHelp()
    {
        Intent intent=new Intent(LoginActivity.this,FirstTime.class);
        startActivity(intent);
        finish();
    }
}
