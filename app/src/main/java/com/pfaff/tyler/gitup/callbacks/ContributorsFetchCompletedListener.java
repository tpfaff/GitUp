package com.pfaff.tyler.gitup.callbacks;

import com.pfaff.tyler.gitup.model.Contributor;

import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;


public interface ContributorsFetchCompletedListener {
    public void successfulFetch(List<Contributor> contributors, Response retrofitResponse);

    public void failedFetch(RetrofitError error);
}
