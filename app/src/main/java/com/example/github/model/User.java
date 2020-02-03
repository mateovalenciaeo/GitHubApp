package com.example.github.model;

import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity(primaryKeys = "login")
public class User {
    @SerializedName("login")
    private String login;
    @SerializedName("avatar_url")
    private String avatarUrl;
    @SerializedName("name")
    private String name;
    @SerializedName("company")
    private String company;
    @SerializedName("repos_url")
    private String reposUrl;
    @SerializedName("blog")
    private String blog;

    public User(String login, String avatarUrl, String name, String company, String reposUrl, String blog) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.company = company;
        this.reposUrl = reposUrl;
        this.blog = blog;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public String getBlog() {
        return blog;
    }
}
