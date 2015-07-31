package com.pfaff.tyler.gitup.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfaff.tyler.gitup.R;
import com.pfaff.tyler.gitup.callbacks.OpenProfileLinkListener;
import com.pfaff.tyler.gitup.model.Contributor;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tylerpfaff on 7/31/15.
 */
public class ContributorsAdapter extends RecyclerView.Adapter<ContributorsAdapter.ContributorViewHolder> {

    List<Contributor> contributors;
    Context context;
    OpenProfileLinkListener linkListener;

    public ContributorsAdapter(List<Contributor> contributors, Context context,OpenProfileLinkListener linkListener){
        this.contributors = contributors;
        this.context = context;
        this.linkListener = linkListener;

    }
    @Override
    public ContributorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.contributor_card,parent,false);
        return new ContributorViewHolder(card);
    }

    @Override
    public void onBindViewHolder(ContributorViewHolder holder, int position) {
        Contributor contributor = contributors.get(position);
        String avatarUrl = contributor.getAvatarUrl();
        String profileName = contributor.getLogin();
        String profileLink = contributor.getHtmlUrl();

        Picasso.with(context).load(avatarUrl).into(holder.avatarImageView);
        holder.usernameTextView.setText(profileName);
        holder.profileLinkTextView.setText(profileLink);

    }

    @Override
    public int getItemCount() {
        return contributors.size();
    }

    public class ContributorViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.user_card)
        CardView userCard;
        @Bind(R.id.usercard_avatar_image_view)
        ImageView avatarImageView;
        @Bind(R.id.usercard_profile_link_text_view)
        TextView profileLinkTextView;
        @Bind(R.id.usercard_username_text_view)
        TextView usernameTextView;

        public ContributorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            userCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = contributors.get(getAdapterPosition()).getHtmlUrl();
                    linkListener.openProfileLink(url);
                }
            });
        }
    }
}
