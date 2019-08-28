package com.example.orderingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderingapp.Model.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoginAdapter extends RecyclerView.Adapter<LoginAdapter.MenuViewHolder> {
    private ArrayList<Category> values;
    private OnItemClickListener mListener;
    private Context mcontext;
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAddClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener = listener;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        public TextView textMenuName;
        public ImageView imageView;
        public Button addButton;
        public TextView txtPrice;


        public MenuViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
                textMenuName = itemView.findViewById(R.id.menu_Name);
                imageView = itemView.findViewById(R.id.menu_image);
                addButton = itemView.findViewById(R.id.addButton);
                txtPrice = itemView.findViewById(R.id.menu_price);

                itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION)
                        {
                            listener.onAddClick(position);
                        }
                    }
                }
            });
        }
    }

    public LoginAdapter(ArrayList<Category> categoryArrayList,Context context) {
        values = categoryArrayList;
        this.mcontext=context;
    }


    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item,parent,false);
        MenuViewHolder mvh = new MenuViewHolder(v,mListener);
        return mvh;
    }
    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder,int position){
        Category currentItem = values.get(position);

        holder.textMenuName.setText(currentItem.getName());
        holder.txtPrice.setText(currentItem.getPrice());
        Glide.with(mcontext).load(currentItem.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount(){
        return values.size();
    }
}
