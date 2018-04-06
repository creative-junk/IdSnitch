package com.crysoft.me.idsnitch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxx on 4/6/2018.
 */

public class BusinessModel implements Parcelable {
    private String businessName,id,country;
    public  BusinessModel(){

    }
    protected BusinessModel(Parcel in) {
        businessName = in.readString();
        id = in.readString();
        country=in.readString();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(businessName);
        dest.writeString(id);
        dest.writeString(country);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BusinessModel> CREATOR = new Creator<BusinessModel>() {
        @Override
        public BusinessModel createFromParcel(Parcel in) {
            return new BusinessModel(in);
        }

        @Override
        public BusinessModel[] newArray(int size) {
            return new BusinessModel[size];
        }
    };
}
