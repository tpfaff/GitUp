package com.pfaff.tyler.gitup.callbacks;

import com.pfaff.tyler.gitup.model.TrendingReposResponse;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tylerpfaff on 7/30/15.
 */
public interface RepoFetchCompletedListener {
    public void successfulFetch(TrendingReposResponse trendingReposResponse, Response retrofitResponse);
    public void failedFetch(RetrofitError error);
}
