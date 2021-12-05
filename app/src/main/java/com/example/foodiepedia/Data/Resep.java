package com.example.foodiepedia.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Resep implements Parcelable {
    private int idresep,iduser;
    private String nama_resep,desk_resep,chef_resep;
    private int statusresep;

    public Resep(int idresep, int iduser, String nama_resep, String desk_resep, String chef_resep, int statusresep) {
        this.idresep = idresep;
        this.iduser = iduser;
        this.nama_resep = nama_resep;
        this.desk_resep = desk_resep;
        this.chef_resep = chef_resep;
        this.statusresep = statusresep;
    }

    public int getIdresep() {
        return idresep;
    }

    public int getIduser() {
        return iduser;
    }

    public String getNama_resep() {
        return nama_resep;
    }

    public String getDesk_resep() {
        return desk_resep;
    }

    public String getChef_resep() {
        return chef_resep;
    }

    public int getStatusresep() {
        return statusresep;
    }

    protected Resep(Parcel in) {
        idresep = in.readInt();
        iduser = in.readInt();
        nama_resep = in.readString();
        desk_resep = in.readString();
        chef_resep = in.readString();
        statusresep = in.readInt();
    }

    public static final Creator<Resep> CREATOR = new Creator<Resep>() {
        @Override
        public Resep createFromParcel(Parcel in) {
            return new Resep(in);
        }

        @Override
        public Resep[] newArray(int size) {
            return new Resep[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idresep);
        parcel.writeInt(iduser);
        parcel.writeString(nama_resep);
        parcel.writeString(desk_resep);
        parcel.writeString(chef_resep);
        parcel.writeInt(statusresep);
    }
}
