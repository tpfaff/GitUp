package com.pfaff.tyler.gitup.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.pfaff.tyler.gitup.R;
import com.pfaff.tyler.gitup.adapters.ReposAdapter;
import com.pfaff.tyler.gitup.callbacks.RepoFetchCompletedListener;
import com.pfaff.tyler.gitup.callbacks.ShowDetailsActivityListener;
import com.pfaff.tyler.gitup.model.TrendingReposResponse;
import com.pfaff.tyler.gitup.model.Item;
import com.pfaff.tyler.gitup.networking.GitClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements RepoFetchCompletedListener, ShowDetailsActivityListener {


    final String TAG = this.getClass().getSimpleName();
    ShowDetailsActivityListener showDetailsActivityListener = this;
    @Bind(R.id.repoRecycler)
    RecyclerView mRecyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        showProgressBar();

        RepoFetchCompletedListener fetchCompletedListener = this;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GitClient.fetchTrendingRepos(fetchCompletedListener);
    }


    @Override
    public void successfulFetch(TrendingReposResponse trendingReposResponse, Response response) {
        Log.i(TAG, response.toString());

        hideProgressBar();

        List<Item> repos = trendingReposResponse.getItems();
        ReposAdapter adapter = new ReposAdapter(repos, showDetailsActivityListener);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void failedFetch(RetrofitError error) {
        Log.e(TAG, error.toString());
    }

    @Override
    public void showDetailActivity(String ownerName, String repoName) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra(RepoDetailActivity.OWNER_NAME, ownerName);
        intent.putExtra(RepoDetailActivity.REPO_NAME, repoName);
        startActivity(intent);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
