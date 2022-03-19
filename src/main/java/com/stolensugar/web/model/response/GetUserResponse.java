package com.stolensugar.web.model.response;

import com.stolensugar.web.model.User;

public class GetUserResponse {
    private User user;

    public GetUserResponse(User user) {
        this.user = user; 
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((user == null) ? 0 : user.hashCode());
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
        GetUserResponse other = (GetUserResponse) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    public GetUserResponse(Builder builder) {
        this.user = builder.user;
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private User user;

        private Builder() {}

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public GetUserResponse build() { return new GetUserResponse(this);}
    }

}
