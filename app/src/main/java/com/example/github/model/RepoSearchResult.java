package com.example.github.model;

import androidx.room.Entity;
import androidx.room.TypeConverters;

import com.example.github.db.GitHubTypeConverter;

import java.util.List;

@Entity(primaryKeys = {"query"})
@TypeConverters(GitHubTypeConverter.class)
public class RepoSearchResult {
    private String query;
    private List<Integer> reposIds;
    private int totalCount;
    private Integer next;

    public RepoSearchResult(String query, List<Integer> reposIds, int totalCount, Integer next) {
        this.query = query;
        this.reposIds = reposIds;
        this.totalCount = totalCount;
        this.next = next;
    }
}
