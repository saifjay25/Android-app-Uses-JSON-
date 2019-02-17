package com.datechnologies.androidtest.api;

/**
 * A data model that represents a chat log message fetched from the D & A Technologies Web Server.
 */

public class ChatLogMessageModel
{
    public int userId;
    public String avatarUrl;
    public String username;
    public String message;

    public ChatLogMessageModel (int user, String url, String name, String message){
        this.setMessage(message);
        this.setUserId(user);
        this.setAvatarUrl(url);
        this.setUsername(name);
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
