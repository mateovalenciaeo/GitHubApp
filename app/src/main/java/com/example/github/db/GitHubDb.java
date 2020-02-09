package com.example.github.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.github.model.Contributor;
import com.example.github.model.Repo;
import com.example.github.model.RepoSearchResult;
import com.example.github.model.User;

@Database(entities = {User.class, Repo.class, Contributor.class, RepoSearchResult.class}, version = 1)
public abstract class GitHubDb extends RoomDatabase {

    abstract public UserDao userDao();

    abstract public RepoDao repoDao();
}
