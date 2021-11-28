package com.example.foodiepedia.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Resep implements Parcelable {
    private int idresep;
    private String nama_resep;
    private int statusresep;

    public Resep(int idresep, String nama_resep, int statusresep) {
        this.idresep = idresep;
        this.nama_resep = nama_resep;
        this.statusresep = statusresep;
    }

    protected Resep(Parcel in) {
        idresep = in.readInt();
        nama_resep = in.readString();
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
        parcel.writeString(nama_resep);
        parcel.writeInt(statusresep);
    }

    public int getIdresep() {
        return idresep;
    }

    public void setIdresep(int idresep) {
        this.idresep = idresep;
    }

    public String getNama_resep() {
        return nama_resep;
    }

    public void setNama_resep(String nama_resep) {
        this.nama_resep = nama_resep;
    }

    public int getStatusresep() {
        return statusresep;
    }

    public void setStatusresep(int statusresep) {
        this.statusresep = statusresep;
    }
}
