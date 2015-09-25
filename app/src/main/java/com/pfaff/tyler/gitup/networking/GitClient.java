package com.pfaff.tyler.gitup.networking;

import com.google.common.collect.ImmutableMap;
import com.pfaff.tyler.gitup.callbacks.ContributorsFetchCompletedListener;
import com.pfaff.tyler.gitup.callbacks.RepoFetchCompletedListener;
import com.pfaff.tyler.gitup.model.Contributor;
import com.pfaff.tyler.gitup.model.TrendingReposResponse;
import com.pfaff.tyler.gitup.utils.DateUtil;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tylerpfaff on 7/30/15.
 */
public class GitClient {
    private GitClient() {
    }

    static final String API = "https://api.github.com";


    /**
     * Fetch the trending repos created in the last week
     * @param fetchCompletedListener required, sends response back to the listener implementer
     */
    public static void fetchTrendingRepos(String language,final RepoFetchCompletedListener fetchCompletedListener) {
        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        GitApi api = adapter.create(GitApi.class);

        int oneWeekAgo=7;
        String formattedDate = DateUtil.getFormattedDate(oneWeekAgo);

        //Retrofit docs use guava's ImmutableMap
        //Order of params was not being respected without ImmutableMap
        ImmutableMap<String,String> params;
        if(language.equals("All")){
            params = ImmutableMap.of("q=created:>", formattedDate,"sort", "stars", "perpage", "25", "page", "1");
        }else{
            params = ImmutableMap.of("q=language:"+language+"+"+"created:>", formattedDate,"sort", "stars","perpage", "25", "page", "1");

           // params = ImmutableMap.of("q=l",language,"created:>", formattedDate,"sort", "stars", "perpage", "25", "page", "1");
        }

        api.getTrendingRepositories(params, new Callback<TrendingReposResponse>() {
            @Override
            public void success(TrendingReposResponse trendingReposResponse, Response response) {
                fetchCompletedListener.successfulFetch(trendingReposResponse, response);
            }

            @Override
            public void failure(RetrofitError error) {
                fetchCompletedListener.failedFetch(error);
            }
        });
    }

    /**
     * Get the contributors for a particular repo
     * @param owner The user who owns the repo
     * @param repoName The short name of the repo
     * @param fetchCompletedListener Required, sends the Contributors back to the listener implementer
     */
    public static void fetchContributors(String owner, String repoName, final ContributorsFetchCompletedListener fetchCompletedListener) {
        RestAdapter adapter = new RestAdapter.Builder().setLogLevel(RestAdapter.LogLevel.FULL).setEndpoint(API).build();
        GitApi api = adapter.create(GitApi.class);

        api.getContributors(owner, repoName, new Callback<List<Contributor>>() {
            @Override
            public void success(List<Contributor> contributors, Response response) {
                fetchCompletedListener.successfulFetch(contributors, response);
            }

            @Override
            public void failure(RetrofitError error) {
                fetchCompletedListener.failedFetch(error);
            }
        });
    }

    public static void fetchWiki(){

    }
}
