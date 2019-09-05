package com.example.orderingapp.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderingapp.Model.Order;
import com.example.orderingapp.R;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {

        private ArrayList<Order> mOrderList;
        private OnItemClickListener mListener;
        private Context mcontext;
        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    public void setOnItemClickListener(OrdersAdapter.OnItemClickListener listener)
    {
        mListener = listener;
    }
    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView txtTotalPrice;

        public OrdersViewHolder(@NonNull View itemView, final OrdersAdapter.OnItemClickListener listener) {
            super(itemView);

            txtTotalPrice = itemView.findViewById(R.id.orders_price);

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
        }
    }

    public OrdersAdapter(ArrayList<Order> OrderArrayList, Context context) {
        mOrderList = OrderArrayList;
        this.mcontext=context;
    }


    @NonNull
    @Override
    public OrdersAdapter.OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_item,parent,false);
        OrdersAdapter.OrdersViewHolder mvh = new OrdersAdapter.OrdersViewHolder(v,mListener);
        return mvh;
    }
    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.OrdersViewHolder holder, int position){
        Order currentItem = mOrderList.get(position);

        holder.txtTotalPrice.setText(currentItem.getTotalPrice());
    }

    @Override
    public int getItemCount(){
        return mOrderList.size();
    }
}
