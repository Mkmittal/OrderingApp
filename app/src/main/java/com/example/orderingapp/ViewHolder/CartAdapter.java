package com.example.orderingapp.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.orderingapp.Model.Items;
import com.example.orderingapp.R;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private ArrayList<Items> listdata;
    private Context context;
    private OnItemClickListener mListener;

    public CartAdapter(ArrayList<Items> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }

    public interface OnItemClickListener{
        void onIncreaseClick(int position);
        void onDecreaseClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }

    class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtCartName;
        public TextView txtCartPrice;
        public TextView txtCartQuantity;
        public Button increaseButton;
        public Button decreaseButton;
        public ImageView cartImage;

        public CartViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
            super(itemView);
            txtCartName = itemView.findViewById(R.id.cart_name);
            txtCartPrice = itemView.findViewById(R.id.cart_price);
            txtCartQuantity = itemView.findViewById(R.id.cart_quantity);
            cartImage = itemView.findViewById(R.id.cart_image);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
            increaseButton = itemView.findViewById(R.id.increase_button);

            increaseButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) ;
                        {
                            listener.onIncreaseClick(position);
                        }
                    }
                }
            });
            decreaseButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) ;
                        {
                            listener.onDecreaseClick(position);
                        }
                    }
                }
            });
        }

        @Override
        public void onClick(View v) {
        }
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        CartViewHolder cvh = new CartViewHolder(itemView,mListener);
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Items currentOrder = listdata.get(position);
        holder.txtCartName.setText(currentOrder.getProductName());
        holder.txtCartQuantity.setText(currentOrder.getQuantity());
        holder.txtCartPrice.setText(currentOrder.getPrice());
        Glide.with(context).load(currentOrder.getImage()).into(holder.cartImage);
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

}
