package com.crysoft.me.idsnitch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxx on 4/6/2018.
 */

public class ProductModel implements Parcelable {
    private String serialNumber,distributor,description,brand;
    public ProductModel(){

    }
    protected ProductModel(Parcel in) {
        serialNumber = in.readString();
        distributor = in.readString();
        description = in.readString();
        brand = in.readString();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(serialNumber);
        parcel.writeString(distributor);
        parcel.writeString(description);
        parcel.writeString(brand);
    }
}
