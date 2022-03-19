package com.stolensugar.web.model;

public class User {
    private String id;
    private String name;
    private String imageavatarUrl;
    private String reposUrl;

    
    
    public User(String id, String name, String imageavatarUrl, String reposUrl) {
        this.id = id;
        this.name = name;
        this.imageavatarUrl = imageavatarUrl;
        this.reposUrl = reposUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageavatarUrl() {
        return imageavatarUrl;
    }

    public void setImageavatarUrl(String imageavatarUrl) {
        this.imageavatarUrl = imageavatarUrl;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public void setReposUrl(String reposUrl) {
        this.reposUrl = reposUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", imageavatarUrl=" + imageavatarUrl + ", name=" + name + ", reposUrl=" + reposUrl
                + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((imageavatarUrl == null) ? 0 : imageavatarUrl.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((reposUrl == null) ? 0 : reposUrl.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (imageavatarUrl == null) {
            if (other.imageavatarUrl != null)
                return false;
        } else if (!imageavatarUrl.equals(other.imageavatarUrl))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (reposUrl == null) {
            if (other.reposUrl != null)
                return false;
        } else if (!reposUrl.equals(other.reposUrl))
            return false;
        return true;
    }
    
    public static Builder builder() {return new Builder();}

    public User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.imageavatarUrl = builder.imageavatarUrl;
        this.reposUrl = builder.reposUrl;
    }

    public static final class Builder {
        private String id;
        private String name;
        private String imageavatarUrl;
        private String reposUrl;

        private Builder() { 

        }

        public Builder withId(String id) { 
            this.id = id;
            return this;
        }

        public Builder withName(String name) { 
            this.name = name;
            return this;
        }

        public Builder withImageAvatarUrl(String imageavatarUrl) { 
            this.imageavatarUrl = imageavatarUrl;
            return this;
        }
        
        public Builder withReposUrl(String reposUrl) { 
            this.reposUrl = reposUrl;
            return this;
        }
        public User build() {return new User(this);}
    }

}
