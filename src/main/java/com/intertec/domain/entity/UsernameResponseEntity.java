package com.intertec.domain.entity;

import java.util.List;

public class UsernameResponseEntity {
    private Boolean validUsername;
    private List<String> usernameList;

    public UsernameResponseEntity(Boolean validUsername, List<String> usernameList) {
        this.validUsername = validUsername;
        this.usernameList = usernameList;
    }

    public Boolean getValidUsername() {
        return validUsername;
    }

    public void setValidUsername(Boolean validUsername) {
        this.validUsername = validUsername;
    }

    public List<String> getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(List<String> usernameList) {
        this.usernameList = usernameList;
    }
}
