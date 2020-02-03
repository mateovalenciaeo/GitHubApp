package com.example.github.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;

import com.google.gson.annotations.SerializedName;

@Entity(indices = {@Index("id"), @Index("owner_login")},
        primaryKeys = {"name", "owner_login"})
public class Repo {
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("full_name")
    private String fullName;
    @SerializedName("description")
    private String description;
    @SerializedName("stargazers_count")
    private String stars;
    @SerializedName("owner")
    @Embedded(prefix = "owner")
    private Owner owner;

    public Repo(int id, String name, String fullName, String description, String stars, Owner owner) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.description = description;
        this.stars = stars;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
