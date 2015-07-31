package com.pfaff.tyler.gitup.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pfaff.tyler.gitup.R;
import com.pfaff.tyler.gitup.callbacks.ShowDetailsActivityListener;
import com.pfaff.tyler.gitup.model.Item;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tylerpfaff on 7/30/15.
 */
public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.RepoViewHolder>{

    List<Item> repos;
    ShowDetailsActivityListener showDetailsActivityListener;

    /**
     *
     * @param repos List of repos used as data backing
     * @param showDetailsActivityListener Required, use this to avoid an Activity dependency in this adapter.
     */
    public ReposAdapter(List<Item> repos, ShowDetailsActivityListener showDetailsActivityListener) {
        this.repos = repos;
        this.showDetailsActivityListener = showDetailsActivityListener;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View repoCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_card, parent, false);
        return new RepoViewHolder(repoCardView);
    }

    @Override
    public void onBindViewHolder(RepoViewHolder holder, int position) {
        Item repo = repos.get(position);
        String repoName = repo.getName();
        String repoDescription = repo.getDescription();

        holder.repoNameTextView.setText(repoName);
        holder.repoDescriptionTextView.setText(repoDescription);
        holder.repoStarCountTextView.setText(repo.getStargazersCount() + " stars");
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.text_view_repo_name)
        TextView repoNameTextView;
        @Bind(R.id.text_view_repo_description)
        TextView repoDescriptionTextView;
        @Bind(R.id.text_view_repo_stars)
        TextView repoStarCountTextView;
        @Bind(R.id.repo_card)
        CardView repoCardView;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            repoCardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Item repo = repos.get(getAdapterPosition());
                    String ownerName = repo.getOwner().getLogin();
                    String repoName = repo.getName();
                    showDetailsActivityListener.showDetailActivity(ownerName,repoName);
                }
            });
        }
    }
}
