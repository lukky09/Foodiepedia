package com.example.foodiepedia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserListViewHolder> {

    private ArrayList<User> users;
    private OnItemClickCallback onItemClickCallback;

    public UserListAdapter(ArrayList<User> users) {
        this.users = users;
    }

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_user, parent, false);
        UserListViewHolder viewHolder = new UserListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserListViewHolder extends RecyclerView.ViewHolder {

        TextView username;
        Button btnBan;

        public UserListViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textUserListUsername);
            btnBan = itemView.findViewById(R.id.btnBan);
        }

        void bind(User user){
            username.setText(user.getUser_name());

//            TAMBAHKAN IF BANNED TEXT BUTTON JADI UNBAN
            btnBan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickCallback.onItemClicked(user.getUser_name());
                }
            });
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(String username);
    }
}
