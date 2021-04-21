package ru.skelotron.win63.email_sender;

import java.io.Serializable;

public class Attachment implements Serializable {
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
