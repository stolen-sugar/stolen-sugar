package com.stolensugar.web.model.response;

import com.stolensugar.web.model.SpokenFormUser;

public class CreateSpokenFormUserResponse {
    private SpokenFormUser spokenFormUser;

    public CreateSpokenFormUserResponse(SpokenFormUser spokenFormUser) {
        this.spokenFormUser = spokenFormUser;
    }

    public SpokenFormUser getSpokenFormUser() {
        return spokenFormUser;
    }

    public void setSpokenFormUser(SpokenFormUser spokenFormUser) {
        this.spokenFormUser = spokenFormUser;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((spokenFormUser == null) ? 0 : spokenFormUser.hashCode());
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
        CreateSpokenFormUserResponse other = (CreateSpokenFormUserResponse) obj;
        if (spokenFormUser == null) {
            if (other.spokenFormUser != null)
                return false;
        } else if (!spokenFormUser.equals(other.spokenFormUser))
            return false;
        return true;
    }

    public CreateSpokenFormUserResponse(Builder builder) {
        this.spokenFormUser = builder.spokenFormUser;
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {

        private SpokenFormUser spokenFormUser;


        private Builder() {}

        public Builder withSpokenFormUser(SpokenFormUser spokenFormUser) {
            this.spokenFormUser = spokenFormUser;
            return this;
        }

        public CreateSpokenFormUserResponse build() { return new CreateSpokenFormUserResponse(this);}
    }
    
}
