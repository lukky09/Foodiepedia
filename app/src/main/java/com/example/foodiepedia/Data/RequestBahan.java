package com.example.foodiepedia.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class RequestBahan implements Parcelable {
    private int id_bahan, statusbahan;
    private String nama_bahan;

    public RequestBahan(int id_bahan, int statusbahan, String nama_bahan) {
        this.id_bahan = id_bahan;
        this.statusbahan = statusbahan;
        this.nama_bahan = nama_bahan;
    }

    protected RequestBahan(Parcel in) {
        id_bahan = in.readInt();
        statusbahan = in.readInt();
        nama_bahan = in.readString();
    }

    public static final Creator<RequestBahan> CREATOR = new Creator<RequestBahan>() {
        @Override
        public RequestBahan createFromParcel(Parcel in) {
            return new RequestBahan(in);
        }

        @Override
        public RequestBahan[] newArray(int size) {
            return new RequestBahan[size];
        }
    };

    public int getId_bahan() {
        return id_bahan;
    }

    public void setId_bahan(int id_bahan) {
        this.id_bahan = id_bahan;
    }

    public int getStatusbahan() {
        return statusbahan;
    }

    public void setStatusbahan(int statusbahan) {
        this.statusbahan = statusbahan;
    }

    public String getNama_bahan() {
        return nama_bahan;
    }

    public void setNama_bahan(String nama_bahan) {
        this.nama_bahan = nama_bahan;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_bahan);
        parcel.writeInt(statusbahan);
        parcel.writeString(nama_bahan);
    }
}
