package com.crysoft.me.idsnitch.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxx on 4/6/2018.
 */

public class EducationModel implements Parcelable {
    private String graduationYear,qualification,institution;
    public EducationModel(){

    }
    protected EducationModel(Parcel in) {
        graduationYear = in.readString();
        qualification = in.readString();
        institution = in.readString();
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public static final Creator<EducationModel> CREATOR = new Creator<EducationModel>() {
        @Override
        public EducationModel createFromParcel(Parcel in) {
            return new EducationModel(in);
        }

        @Override
        public EducationModel[] newArray(int size) {
            return new EducationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(graduationYear);
        parcel.writeString(qualification);
        parcel.writeString(institution);
    }
}
