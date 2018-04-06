package com.crysoft.me.idsnitch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxx on 4/6/2018.
 */

public class CertificationModel implements Parcelable {
    private String organization,certification,period,qualification,category;

    public CertificationModel(){

    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    protected CertificationModel(Parcel in) {
        organization = in.readString();
        certification = in.readString();
        period = in.readString();
        qualification = in.readString();
        category = in.readString();
    }

    public static final Creator<CertificationModel> CREATOR = new Creator<CertificationModel>() {
        @Override
        public CertificationModel createFromParcel(Parcel in) {
            return new CertificationModel(in);
        }

        @Override
        public CertificationModel[] newArray(int size) {
            return new CertificationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(organization);
        parcel.writeString(certification);
        parcel.writeString(period);
        parcel.writeString(qualification);
        parcel.writeString(category);
    }
}
