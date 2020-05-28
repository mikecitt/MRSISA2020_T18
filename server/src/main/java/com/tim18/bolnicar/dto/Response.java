package com.tim18.bolnicar.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;

public class Response {
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String description;

    private Object[] data;

    public Response() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }
}
