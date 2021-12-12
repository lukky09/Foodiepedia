package com.example.foodiepedia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiepedia.R;

import java.util.ArrayList;

public class UserMessageAdapter extends RecyclerView.Adapter<UserMessageAdapter.MessageHolder> {

    ArrayList<String> messages;
    ArrayList<Integer> seconds;

    public UserMessageAdapter(ArrayList<String> messages, ArrayList<Integer> seconds) {
        this.messages = messages;
        this.seconds = seconds;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_message, parent, false);
        return new UserMessageAdapter.MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        String mess = messages.get(position);
        int secs = seconds.get(position);
        holder.bind(mess,secs);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageHolder extends RecyclerView.ViewHolder {

        TextView time,mess;

        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.tvtime);
            mess = itemView.findViewById(R.id.tvmessage);
        }

        public void bind(String s,int secs){
            mess.setText(s);
            if(secs<=60){
                time.setText(secs+" detik yang lalu");
            }else if(secs<=3600){
                time.setText(secs/60+" menit yang lalu");
            }else if(secs<=86400){
                time.setText(secs/3600+" jam yang lalu");
            }else{
                time.setText(secs/86400+" hari yang lalu");
            }
        }
    }
}
