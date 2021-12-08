package com.example.foodiepedia.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Bahan implements Parcelable {
    private int idbahan, qty;
    private String nama_bahan;

    public Bahan(int idbahan, int qty, String nama_bahan) {
        this.idbahan = idbahan;
        this.qty = qty;
        this.nama_bahan = nama_bahan;
    }

    public int getIdbahan() {
        return idbahan;
    }

    public void setIdbahan(int idbahan) {
        this.idbahan = idbahan;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getNama_bahan() {
        return nama_bahan;
    }

    public void setNama_bahan(String nama_bahan) {
        this.nama_bahan = nama_bahan;
    }

    protected Bahan(Parcel in) {
        idbahan = in.readInt();
        qty = in.readInt();
        nama_bahan = in.readString();
    }

    public static final Creator<Bahan> CREATOR = new Creator<Bahan>() {
        @Override
        public Bahan createFromParcel(Parcel in) {
            return new Bahan(in);
        }

        @Override
        public Bahan[] newArray(int size) {
            return new Bahan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idbahan);
        parcel.writeInt(qty);
        parcel.writeString(nama_bahan);
    }
}
