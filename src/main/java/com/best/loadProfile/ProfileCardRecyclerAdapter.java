package com.best.loadProfile;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.best.R;
import com.best.database.Profile;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileCardRecyclerAdapter extends RecyclerView.Adapter<ProfileCardRecyclerAdapter.ViewHolder> {

    private List<Profile> profiles;
    private String searchTerm;

    public ProfileCardRecyclerAdapter(List<Profile> profiles){
        this.profiles = profiles;
        this.searchTerm = "?`.|-7"; // just a string that no one would ever search for
    }

    public ProfileCardRecyclerAdapter(List<Profile> profiles, String searchTerm){
        this.profiles = profiles;
        this.searchTerm = searchTerm;
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

        // check if user searched or just default list
        if (searchTerm.equals("?`.|-7")) {
            holder.cardView.setTag(profiles.get(position).getIdNumber());
            holder.NameTextView.setText(name);
            holder.IdTextView.setText(Html.fromHtml(id));
            holder.IdTextView.append(profiles.get(position).getIdNumber());
            holder.DobTextView.setText(Html.fromHtml(dob));
            holder.DobTextView.append(profiles.get(position).getDob());
            holder.LastExamTextView.setText(Html.fromHtml(lastExam));
            holder.LastExamTextView.append(profiles.get(position).getLastExamination());
        }
        // highlight search term in results
        else {
            SpannableStringBuilder sbName = new SpannableStringBuilder(name);
            SpannableStringBuilder sbId = new SpannableStringBuilder(profiles.get(position).getIdNumber());
            SpannableStringBuilder sbDoB = new SpannableStringBuilder(profiles.get(position).getDob());

            Pattern p = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);

            Matcher matcherName = p.matcher(name);
            while (matcherName.find()) {
                sbName.setSpan(new BackgroundColorSpan(Color.parseColor("#66556b2f")), matcherName.start(), matcherName.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            Matcher matcherId = p.matcher(profiles.get(position).getIdNumber());
            while (matcherId.find()) {
                sbId.setSpan(new BackgroundColorSpan(Color.parseColor("#66556b2f")), matcherId.start(), matcherId.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            Matcher matcherDoB = p.matcher(profiles.get(position).getDob());
            while (matcherDoB.find()) {
                sbDoB.setSpan(new BackgroundColorSpan(Color.parseColor("#66556b2f")), matcherDoB.start(), matcherDoB.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            }

            holder.cardView.setTag(profiles.get(position).getIdNumber());
            holder.NameTextView.setText(sbName);
            holder.IdTextView.setText(Html.fromHtml(id));
            holder.IdTextView.append(sbId);
            holder.DobTextView.setText(Html.fromHtml(dob));
            holder.DobTextView.append(sbDoB);
            holder.LastExamTextView.setText(Html.fromHtml(lastExam));
            holder.LastExamTextView.append(profiles.get(position).getLastExamination());
        }
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
