package com.pfaff.tyler.gitup.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.pfaff.tyler.gitup.R;
import com.pfaff.tyler.gitup.adapters.ContributorsAdapter;
import com.pfaff.tyler.gitup.callbacks.ContributorsFetchCompletedListener;
import com.pfaff.tyler.gitup.callbacks.OpenProfileLinkListener;
import com.pfaff.tyler.gitup.model.Contributor;
import com.pfaff.tyler.gitup.networking.GitClient;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tylerpfaff on 7/31/15.
 */
public class RepoDetailActivity extends AppCompatActivity implements ContributorsFetchCompletedListener,OpenProfileLinkListener {


    private final String TAG = getClass().getSimpleName();

    public static final String OWNER_NAME = "OWNER_NAME";
    public static final String REPO_NAME = "REPO_NAME";

    OpenProfileLinkListener linkListener = this;

    @Bind(R.id.contributorsRecycler)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributors);
        ButterKnife.bind(this);
        setTitle("Contributors");

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fetchContributors();
    }

    private void fetchContributors(){
        Bundle extras = getIntent().getExtras();
        String ownerName = extras.getString(OWNER_NAME);
        String repoName = extras.getString(REPO_NAME);

        ContributorsFetchCompletedListener listener = this;
        GitClient.fetchContributors(ownerName, repoName, listener);


    }

    @Override
    public void successfulFetch(List<Contributor> contributors, Response retrofitResponse) {
        Log.i(TAG,"Contributors fetch success");
        ContributorsAdapter adapter = new ContributorsAdapter(contributors,this,linkListener);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void failedFetch(RetrofitError error) {
        Log.e(TAG, error.toString());
        Toast.makeText(this, "Check your connection...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void openProfileLink(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
