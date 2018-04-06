package com.crysoft.me.idsnitch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxx on 4/5/2018.
 */

public class ProfessionalModel implements Parcelable {
    private String firstName, middleName,lastName,idNumber,imageName,country,userId;
    public ProfessionalModel(){

    }
    protected ProfessionalModel(Parcel in) {
        firstName = in.readString();
        middleName = in.readString();
        lastName = in.readString();
        idNumber = in.readString();
        imageName = in.readString();
        country = in.readString();
        userId = in.readString();
    }

    public static final Creator<ProfessionalModel> CREATOR = new Creator<ProfessionalModel>() {
        @Override
        public ProfessionalModel createFromParcel(Parcel in) {
            return new ProfessionalModel(in);
        }

        @Override
        public ProfessionalModel[] newArray(int size) {
            return new ProfessionalModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(middleName);
        parcel.writeString(lastName);
        parcel.writeString(idNumber);
        parcel.writeString(imageName);
        parcel.writeString(country);
        parcel.writeString(userId);
    }
}
