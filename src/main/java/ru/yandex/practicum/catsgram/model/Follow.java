package ru.yandex.practicum.catsgram.model;

public class Follow {

    String userId;
    String followerId;

    public Follow(String userId, String followerId) {
        this.userId = userId;
        this.followerId = followerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }
}
