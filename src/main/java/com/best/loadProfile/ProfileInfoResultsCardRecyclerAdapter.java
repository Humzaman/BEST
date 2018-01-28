package com.best.loadProfile;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.best.R;
import com.best.database.Profile;
import com.best.database.Results;

import java.util.List;

public class ProfileInfoResultsCardRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> items;

    public ProfileInfoResultsCardRecyclerAdapter(List<Object> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 0) {
            View v1 = inflater.inflate(R.layout.item_profile_info, parent, false);
            viewHolder = new ProfileInfoHolder(v1);
        }
        else {
            View v2 = inflater.inflate(R.layout.item_result, parent, false);
            viewHolder = new ProfileResultHolder(v2);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 0) {
            ProfileInfoHolder profileInfoHolder = (ProfileInfoHolder) holder;
            configureProfileInfoHolder(profileInfoHolder, position);
        }
        else {
            ProfileResultHolder profileResultHolder = (ProfileResultHolder) holder;
            configureProfileResultHolder(profileResultHolder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    private void configureProfileInfoHolder(ProfileInfoHolder profileInfoHolder, int position) {
        Profile profile = (Profile) items.get(position);
        if (profile != null) {
            profileInfoHolder.getInfoName().setText(profile.getName());

            profileInfoHolder.getInfoId().setText(Html.fromHtml("<b>ID#: </b>"));
            profileInfoHolder.getInfoId().append(profile.getIdNumber());

            profileInfoHolder.getInfoDob().setText(Html.fromHtml("<b>Date of Birth: </b>"));
            profileInfoHolder.getInfoDob().append(profile.getDob());

            profileInfoHolder.getInfoGender().setText(Html.fromHtml("<b>Gender: </b>"));
            profileInfoHolder.getInfoGender().append(profile.getGender());

            profileInfoHolder.getInfoHandedness().setText(Html.fromHtml("<b>Handedness: </b>"));
            if (profile.getHandedness().equals("Ambi")) {
                profileInfoHolder.getInfoHandedness().append(profile.getHandedness() + "dextrous");
            }
            else
                profileInfoHolder.getInfoHandedness().append(profile.getHandedness() + "-handed");

            profileInfoHolder.getInfoEducation().setText(Html.fromHtml("<b>Education Level: </b>"));
            profileInfoHolder.getInfoEducation().append(profile.getEducationLevel() + " years");

            profileInfoHolder.getInfoNotes().setText(Html.fromHtml("<b>Notes: </b>"));
            profileInfoHolder.getInfoNotes().append(profile.getNotes());
        }
    }

    private void configureProfileResultHolder(ProfileResultHolder profileResultHolder, int position) {
        final Results results = (Results) items.get(position);

        if (results != null) {
            profileResultHolder.getExamDate().setText(results.getTestDate());
        }

        profileResultHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ProfileInfoViewResult.class);
                intent.putExtra("bestDate", results.getTestDate());
                intent.putExtra("id", results.getResultsId());
                intent.putExtra("rveResult", results.getRveResult());
                intent.putExtra("pre1Result", results.getPre1Result());
                intent.putExtra("pre2Result", results.getPre2Result());
                intent.putExtra("pve1Result", results.getPve1Result());
                intent.putExtra("pve2Result", results.getPve2Result());
                intent.putExtra("ppe1Result", results.getPpe1Result());
                intent.putExtra("ppe2Result", results.getPpe2Result());
                intent.putExtra("rbeResult", results.getRbeResult());
                intent.putExtra("rveTarget", results.getRveTarget());
                intent.putExtra("pre1Target", results.getPre1Target());
                intent.putExtra("pre2Target", results.getPre2Target());
                intent.putExtra("pve1Target", results.getPve1Target());
                intent.putExtra("pve2Target", results.getPve2Target());
                intent.putExtra("ppe1Target", results.getPpe1Target());
                intent.putExtra("ppe2Target", results.getPpe2Target());
                intent.putExtra("rbeTarget", results.getRbeTarget());

                view.getContext().startActivity(intent);
            }
        });
    }
}
