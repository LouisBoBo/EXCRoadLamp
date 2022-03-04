package com.exc.roadlamp.bean;

public class UpdateInfoData {
    private String lastestVersionName;
    private int force;
    private String lastestVersionCode;
    private String url;
    private String content;

    public String getLastestVersionName() {
        return lastestVersionName;
    }

    public void setLastestVersionName(String lastestVersionName) {
        this.lastestVersionName = lastestVersionName;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public String getLastestVersionCode() {
        return lastestVersionCode;
    }

    public void setLastestVersionCode(String lastestVersionCode) {
        this.lastestVersionCode = lastestVersionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
