package com.udacity.notepad.data;

import android.support.annotation.Nullable;

import java.util.Date;

// This is a POJO/Model class, should be in a model package
// It does not make sense in the same package than the contract or the open helper
public class Note {

    private int id = -1;
    @Nullable private String text;
    private boolean isPinned = false;
    private Date createdAt = new Date();
    private Date updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Nullable
    public String getText() {
        return text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
