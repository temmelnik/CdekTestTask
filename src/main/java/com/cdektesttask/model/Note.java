package com.cdektesttask.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Note {

    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String record;

    public Note() {
    }

    public Note(Long id, String record) {
        this.id = id;
        this.record = record;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
