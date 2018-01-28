package com.best.loadProfile;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.best.R;

public class ProfileResultHolder extends RecyclerView.ViewHolder {

    private TextView examDate;

    public ProfileResultHolder(View itemView) {
        super(itemView);
        examDate = itemView.findViewById(R.id.profileInfoResult);
    }

    public TextView getExamDate() {
        return examDate;
    }

    public void setExamDate(TextView examDate) {
        this.examDate = examDate;
    }
}
