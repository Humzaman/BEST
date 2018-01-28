package com.best.loadProfile;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.best.R;

public class ProfileInfoHolder extends RecyclerView.ViewHolder {

    private TextView name, id, dob, gender, handedness, education, notes;

    public ProfileInfoHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.profileInfoName);
        id = itemView.findViewById(R.id.profileInfoId);
        dob = itemView.findViewById(R.id.profileInfoDOB);
        gender = itemView.findViewById(R.id.profileInfoGender);
        handedness = itemView.findViewById(R.id.profileInfoHandedness);
        education = itemView.findViewById(R.id.profileInfoEducation);
        notes = itemView.findViewById(R.id.profileInfoNotes);
    }

    public TextView getInfoName() {
        return name;
    }

    public void setInfoName(TextView name) {
        this.name = name;
    }

    public TextView getInfoId() {
        return id;
    }

    public void setInfoId(TextView id) {
        this.id = id;
    }

    public TextView getInfoDob() {
        return dob;
    }

    public void setInfoDob(TextView dob) {
        this.dob = dob;
    }

    public TextView getInfoGender() {
        return gender;
    }

    public void setInfoGender(TextView gender) {
        this.gender = gender;
    }

    public TextView getInfoHandedness() {
        return handedness;
    }

    public void setInfoHandedness(TextView handedness) {
        this.handedness = handedness;
    }

    public TextView getInfoEducation() {
        return education;
    }

    public void setInfoEducation(TextView education) {
        this.education = education;
    }

    public TextView getInfoNotes() {
        return notes;
    }

    public void setInfoNotes(TextView notes) {
        this.notes = notes;
    }
}
