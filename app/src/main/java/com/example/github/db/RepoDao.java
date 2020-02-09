package com.example.github.db;

import android.util.SparseArray;
import android.util.SparseIntArray;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.github.model.Contributor;
import com.example.github.model.Repo;
import com.example.github.model.RepoSearchResult;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Dao
public interface RepoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Repo... repos);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContributors(List<Contributor> contributors);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRepos(List<Repo> repositories);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long createRepoIfNotExists(Repo repo);

    @Query("SELECT * FROM repo WHERE owner_login = :login AND name = :name")
    LiveData<Repo> load(String login, String name);

    @Query("SELECT login, avatarUrl, repoName, repoOwner, contributions FROM contributor WHERE repoName = :name AND repoOwner = :owner ORDER BY contributions DESC")
    LiveData<List<Contributor>> loadContributors(String owner, String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RepoSearchResult result);

    LiveData<List<Repo>> loadOrdered(List<Integer> reposIds){
        SparseIntArray order = new SparseIntArray();
        int index = 0;

        for (Integer repoId: reposIds){
            order.put(repoId, index++);
        }

        return Transformations.map(loadById(reposIds), new Function<List<Repo>, List<Repo>>() {
            @Override
            public List<Repo> apply(List<Repo> repositories) {
                Collections.sort(repositories, new Comparator<Repo>() {
                    @Override
                    public int compare(Repo repo, Repo t1) {
                        int pos1 = order.get(repo.getId());
                        int pos2 = order.get(t1.getId());
                        return pos1 - pos2;
                    }
                });
                return repositories;
            }
        });
    }

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    LiveData<RepoSearchResult> search(String query);

    @Query("SELECT * FROM Repo WHERE id in(:repoIds)")
    LiveData<List<Repo>> loadById(List<Integer> repoIds);

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    RepoSearchResult findSearchResult(String query);
}
