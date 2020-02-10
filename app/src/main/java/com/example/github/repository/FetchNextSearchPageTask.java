package com.example.github.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.github.api.ApiResponse;
import com.example.github.api.WebServiceApi;
import com.example.github.db.GitHubDb;
import com.example.github.model.RepoSearchResponse;
import com.example.github.model.RepoSearchResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class FetchNextSearchPageTask implements Runnable {

    private final MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
    private final String query;
    private final WebServiceApi gitHubServcie;
    private final GitHubDb db;

    FetchNextSearchPageTask(String query, WebServiceApi gitHubServcie, GitHubDb db){
        this.query = query;
        this.gitHubServcie = gitHubServcie;
        this.db = db;
    }

    @Override
    public void run() {
        RepoSearchResult current = db.repoDao().findSearchResult(query);
        if(current == null){
            liveData.postValue(null);
            return;
        }

        final Integer nextPage = current.getNext();
        if(nextPage == null){
            liveData.postValue(Resource.success(false));
            return;
        }
        try{
            Response<RepoSearchResponse> response = gitHubServcie
                    .searchRepos(query, nextPage).execute();
            ApiResponse<RepoSearchResponse> apiResponse = new ApiResponse<RepoSearchResponse>(response);
            if(apiResponse.isSuccessful()){
                List<Integer> ids = new ArrayList<>();
                ids.addAll(current.getReposIds());
                ids.addAll(apiResponse.body.getReposIds());
                RepoSearchResult merged = new RepoSearchResult(query, ids, apiResponse.body.getTotal(), apiResponse.getNextPage());
                try{
                    db.beginTransaction();
                    db.repoDao().insert(merged);
                    db.repoDao().insertRepos(apiResponse.body.getItems());
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                liveData.postValue(Resource.success(apiResponse.getNextPage() != null));
            } else {
                liveData.postValue(Resource.error(apiResponse.errorMessage, true));
            }
        } catch (Exception e){
            liveData.postValue(Resource.error(e.getMessage(), true));
        }

    }

    LiveData<Resource<Boolean>> getLiveData(){
        return liveData;
    }
}
