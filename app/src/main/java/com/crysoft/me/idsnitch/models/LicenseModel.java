package com.crysoft.me.idsnitch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxx on 4/6/2018.
 */

public class LicenseModel implements Parcelable {
    private String licenseName,licensePeriod,issuedBy;
    public LicenseModel(){

    }
    protected LicenseModel(Parcel in) {
        licenseName = in.readString();
        licensePeriod = in.readString();
        issuedBy = in.readString();
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getLicensePeriod() {
        return licensePeriod;
    }

    public void setLicensePeriod(String licensePeriod) {
        this.licensePeriod = licensePeriod;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public static final Creator<LicenseModel> CREATOR = new Creator<LicenseModel>() {
        @Override
        public LicenseModel createFromParcel(Parcel in) {
            return new LicenseModel(in);
        }

        @Override
        public LicenseModel[] newArray(int size) {
            return new LicenseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(licenseName);
        parcel.writeString(licensePeriod);
        parcel.writeString(issuedBy);
    }
}
