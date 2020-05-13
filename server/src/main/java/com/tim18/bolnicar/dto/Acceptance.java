package com.tim18.bolnicar.dto;

public class Acceptance {
    private String userJmbg;
    private boolean accept;
    private String message;

    public Acceptance() {
    }

    public Acceptance(String userJmbg, boolean accept, String message) {
        this.userJmbg = userJmbg;
        this.accept = accept;
        this.message = message;
    }

    public String getUserJmbg() {
        return userJmbg;
    }

    public void setUserJmbg(String userJmbg) {
        this.userJmbg = userJmbg;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
