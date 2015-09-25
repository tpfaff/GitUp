package com.pfaff.tyler.gitup.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.pfaff.tyler.gitup.R;
import com.pfaff.tyler.gitup.adapters.LanguageSpinnerAdapter;
import com.pfaff.tyler.gitup.adapters.ReposAdapter;
import com.pfaff.tyler.gitup.callbacks.RepoFetchCompletedListener;
import com.pfaff.tyler.gitup.callbacks.ShowDetailsActivityListener;
import com.pfaff.tyler.gitup.model.TrendingReposResponse;
import com.pfaff.tyler.gitup.model.Item;
import com.pfaff.tyler.gitup.networking.GitClient;

import java.util.Arrays;
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
    @Bind(R.id.language_tool_bar)
    Toolbar languageBar;
    @Bind(R.id.lanuage_spinner)
    Spinner languageSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        showProgressBar();

        RepoFetchCompletedListener fetchCompletedListener = this;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        languageSpinner.setAdapter(new LanguageSpinnerAdapter(this, Arrays.asList(getResources().getStringArray(R.array.display_languages))));
        setSupportActionBar(languageBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        GitClient.fetchTrendingRepos(languageSpinner.getSelectedItem().toString(), fetchCompletedListener);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showProgressBar();
                GitClient.fetchTrendingRepos(languageSpinner.getSelectedItem().toString(), MainActivity.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        Toast.makeText(this,"Check your connection...",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDetailActivity(String ownerName, String repoName) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra(RepoDetailActivity.OWNER_NAME, ownerName);
        intent.putExtra(RepoDetailActivity.REPO_NAME, repoName);
        startActivity(intent);
    }

    @Override
    public void openUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
