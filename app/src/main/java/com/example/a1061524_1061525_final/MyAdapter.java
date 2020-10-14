package com.example.a1061524_1061525_final;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.a1061524_1061525_final.ui.buildAndJoin.buildAndJoin_join_event;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<event> allEventData;
    private Context activity;
    private Context destination;
    private int userId;
    private String eventName;

    public MyAdapter(Context a,Context d, List<event> all, int id, String name) {
        activity = a;
        destination = d;
        allEventData = all;
        userId = id;
        eventName = name;
    }

    // 建立ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder{
        // 宣告元件
        private TextView event_name;
        private TextView event_time;
        private TextView event_location;

        ViewHolder(View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.text_name);
            event_time = itemView.findViewById(R.id.text_time);
            event_location = itemView.findViewById(R.id.text_location);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (destination != null) {
                        Intent intent = new Intent(activity,destination.getClass());
                        intent.putExtra("pos",getAdapterPosition());//顯示全活動才有用
                        intent.putExtra("userId",userId);
                        intent.putExtra("eventName",eventName);
                        activity.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 連結項目布局檔list_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custome_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 設置txtItem要顯示的內容
        holder.event_name.setText(allEventData.get(position).getName());
        holder.event_time.setText(allEventData.get(position).getTime());
        holder.event_location.setText(allEventData.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return allEventData.size();
    }
}