package com.best.loadProfile;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
        String id = "<b>ID#: </b>";
        String dob = "<b>DoB: </b>";
        String lastExam = "<b>Last Exam: </b>";

        holder.cardView.setTag(profiles.get(position).getIdNumber());
        holder.NameTextView.setText(name);
        holder.IdTextView.setText(Html.fromHtml(id));
        holder.IdTextView.append(profiles.get(position).getIdNumber());
        holder.DobTextView.setText(Html.fromHtml(dob));
        holder.DobTextView.append(profiles.get(position).getDob());
        holder.LastExamTextView.setText(Html.fromHtml(lastExam));
        holder.LastExamTextView.append(profiles.get(position).getLastExamination());
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
