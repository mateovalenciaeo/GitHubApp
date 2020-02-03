package com.example.github.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RepoSearchResponse {
    @SerializedName("total_count")
    private int total;
    @SerializedName("items")
    private List<Repo> items;
    private Integer nextPage;

    public RepoSearchResponse(int total, List<Repo> items, Integer nextPage) {
        this.total = total;
        this.items = items;
        this.nextPage = nextPage;
    }

    public RepoSearchResponse() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }

    public Integer getNextPage() {
        return nextPage;
    }

    public void setNextPage(Integer nextPage) {
        this.nextPage = nextPage;
    }

    public List<Integer> getReposIds(){
        List<Integer> reposIds = new ArrayList<>();
        for (Repo repo: items
             ) {
            reposIds.add(repo.getId());
        }
        return reposIds;
    }
}
