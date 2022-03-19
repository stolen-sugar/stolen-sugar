package com.stolensugar.web.model.requests;

public class GetUserRequest {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public GetUserRequest() {}

    public GetUserRequest(Builder builder ) {this.userId = builder.userId;}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
        GetUserRequest other = (GetUserRequest) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String userId;

        private Builder() {}

        public Builder withUserId(String userIdToUse) {
            this.userId = userIdToUse;
            return this;
        }

        public GetUserRequest build() { return new GetUserRequest(this);}
    }
}
