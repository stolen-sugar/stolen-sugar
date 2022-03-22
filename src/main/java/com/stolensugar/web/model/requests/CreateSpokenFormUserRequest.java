package com.stolensugar.web.model.requests;

public class CreateSpokenFormUserRequest {
    private String userId;
    private String spokenFormId;
    private String choice;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSpokenFormId() {
        return spokenFormId;
    }

    public void setSpokenFormId(String spokenFormId) {
        this.spokenFormId = spokenFormId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public CreateSpokenFormUserRequest() {}

    public CreateSpokenFormUserRequest(Builder builder) {
        this.userId = builder.userId;
        this.spokenFormId = builder.spokenFormId;
        this.choice = builder.choice;
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String userId;
        private String spokenFormId;
        private String choice;

        private Builder() {}

        public Builder withUserId(String userIdToUse) {
            this.userId = userIdToUse;
            return this;
        }

        public Builder withSpokenFormId(String spokenFormId) {
            this.spokenFormId = spokenFormId;
            return this;
        }
        public Builder withChoice(String choice) {
            this.choice = choice;
            return this;
        }

        public CreateSpokenFormUserRequest build() { return new CreateSpokenFormUserRequest(this);}
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((choice == null) ? 0 : choice.hashCode());
        result = prime * result + ((spokenFormId == null) ? 0 : spokenFormId.hashCode());
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
        CreateSpokenFormUserRequest other = (CreateSpokenFormUserRequest) obj;
        if (choice == null) {
            if (other.choice != null)
                return false;
        } else if (!choice.equals(other.choice))
            return false;
        if (spokenFormId == null) {
            if (other.spokenFormId != null)
                return false;
        } else if (!spokenFormId.equals(other.spokenFormId))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }
    
    
    
}
