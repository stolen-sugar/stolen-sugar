package com.stolensugar.web.model;

import java.util.List;
import java.util.Map;

public class SpokenFormUser {
    String spokenFormId;
    String userId;
    String choice;
    List<String> choiceHistory;
    Map<String, String> details;

    public SpokenFormUser(String spokenFormId, String userId, String choice) {
        this.spokenFormId = spokenFormId;
        this.userId = userId;
        this.choice = choice;
    }

    public String getSpokenFormId() {
        return spokenFormId;
    }

    public void setSpokenFormId(String spokenFormId) {
        this.spokenFormId = spokenFormId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public List<String> getChoiceHistory() {
        return choiceHistory;
    }

    public void setChoiceHistory(List<String> choiceHistory) {
        this.choiceHistory = choiceHistory;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        SpokenFormUser other = (SpokenFormUser) obj;
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

    @Override
    public String toString() {
        return "SpokenFormUser [choice=" + choice + ", choiceHistory=" + choiceHistory + ", details=" + details
                + ", spokenFormId=" + spokenFormId + ", userId=" + userId + "]";
    }
    
    
}
