package com.example.foodiepedia.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int user_id;
    private String user_name;
    private String password;
    private int user_isbanned;

    public User(int user_id, String user_name, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.user_isbanned = 0;
    }

    protected User(Parcel in) {
        user_id = in.readInt();
        user_name = in.readString();
        password = in.readString();
        user_isbanned = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUser_isbanned() {
        return user_isbanned;
    }

    public void setUser_isbanned(int user_isbanned) {
        this.user_isbanned = user_isbanned;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(user_id);
        parcel.writeString(user_name);
        parcel.writeString(password);
        parcel.writeInt(user_isbanned);
    }
}
