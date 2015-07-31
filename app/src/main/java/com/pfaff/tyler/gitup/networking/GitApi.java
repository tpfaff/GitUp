package com.pfaff.tyler.gitup.networking;

import com.pfaff.tyler.gitup.model.Contributor;
import com.pfaff.tyler.gitup.model.TrendingReposResponse;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;

/**
 * Created by tylerpfaff on 7/26/15.
 */
public interface GitApi {

    //https://github.com/search?q=created:%3E=2015-07-11&sort=stars&order=desc&perpage=25&page=1



    @GET("/search/repositories")
    public void getTrendingRepositories(@QueryMap Map<String, String> filters, Callback<TrendingReposResponse> response);

    @GET("/repos/{owner}/{repoName}/contributors")
    public void getContributors(@Path("owner") String owner,
                                @Path("repoName") String repoName,
                                Callback<List<Contributor>> responseCallback);
}
