package com.stolensugar.web.model;

import java.util.Objects;

public class Repo {
    private String name;
    private String appName;
    private String url;
    private String id;
    
    public Repo(String name, String url, String appName, String id) {
        this.name = name;
        this.url = url;
        this.appName = appName;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }


    public void setAppName(String appName) {
        this.appName = appName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repo repo = (Repo) o;
        return getName().equals(repo.getName()) && getAppName().equals(repo.getAppName()) && getUrl().equals(repo.getUrl()) && getId().equals(repo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAppName(), getUrl(), getId());
    }

    @Override
    public String toString() {
        return "Repo{" +
                "name='" + name + '\'' +
                ", appName='" + appName + '\'' +
                ", url='" + url + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
