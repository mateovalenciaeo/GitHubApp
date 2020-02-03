package com.example.github.model;

import com.google.gson.annotations.SerializedName;

public class Owner {
    @SerializedName("login")
    private String login;

    @SerializedName("url")
    private String url;

    public Owner(String login, String url) {
        this.login = login;
        this.url = url;
    }

    public Owner() {
    }
}
