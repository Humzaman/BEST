package com.best.loadProfile;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.best.R;
import com.best.database.Profile;

import java.util.List;

public class ProfileCardRecyclerAdapter extends RecyclerView.Adapter<ProfileCardRecyclerAdapter.ViewHolder> {

    private List<Profile> profiles;

    public ProfileCardRecyclerAdapter(List<Profile> profiles){
        this.profiles = profiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_profile_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String name = profiles.get(position).getName();
        String id = "ID#: " + profiles.get(position).getIdNumber();
        String dob = "DoB: " + profiles.get(position).getDob();
        String lastExam = "Last Exam: " + profiles.get(position).getLastExamination();

        holder.cardView.setTag(profiles.get(position).getIdNumber());
        holder.NameTextView.setText(name);
        holder.IdTextView.setText(id);
        holder.DobTextView.setText(dob);
        holder.LastExamTextView.setText(lastExam);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView NameTextView;
        TextView IdTextView;
        TextView DobTextView;
        TextView LastExamTextView;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.profileCardView);
            NameTextView = itemView.findViewById(R.id.profileNameTextView);
            IdTextView = itemView.findViewById(R.id.profileIdTextView);
            DobTextView = itemView.findViewById(R.id.profileDOBTextView);
            LastExamTextView = itemView.findViewById(R.id.profileLastExamTextView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String profileId = (String) view.getTag();
                    Intent intent = new Intent(view.getContext(), ProfileInfoActivity.class);
                    intent.putExtra("profileId", profileId);

                    view.getContext().startActivity(intent);
                }
            });
        }

    }
}
