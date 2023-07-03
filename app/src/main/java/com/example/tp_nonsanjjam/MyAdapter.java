package com.example.tp_nonsanjjam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

    Context context;
    ArrayList<Item> items;

    public MyAdapter(Context context, ArrayList<Item> item) {
        this.context = context;
        this.items = item;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.recycler_item, parent, false);

        VH holder = new VH(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        Item item = items.get(position); // position.. 1,2,3 에 따라 아이템도 순서대로..?

        holder.meal1.setText(item.dates);
        holder.meal2.setText(item.brst);
        holder.meal3.setText(item.lunc);
        holder.meal4.setText(item.dinr);


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView meal1;
        TextView meal2;
        TextView meal3;
        TextView meal4;
        TextView meal5;
        TextView meal6;



        public VH(@NonNull View itemView) {
            super(itemView);
            meal1= itemView.findViewById(R.id.tv_meal1);
            meal2= itemView.findViewById(R.id.tv_meal2);
            meal3= itemView.findViewById(R.id.tv_meal3);
            meal4= itemView.findViewById(R.id.tv_meal4);
            meal5= itemView.findViewById(R.id.tv_meal5);
            meal6= itemView.findViewById(R.id.tv_meal6);

        }


    }




}
