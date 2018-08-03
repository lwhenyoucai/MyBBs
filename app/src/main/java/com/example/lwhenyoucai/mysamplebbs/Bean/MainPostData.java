package com.example.lwhenyoucai.mysamplebbs.Bean;

/**
 * Created by lwhenyoucai on 2018/4/28.
 */
public class MainPostData {

    String postId;
    String postTitle;
    String userId;
    String moduleId;
    String browsCount;
    String likeCount;
    String releaseTime;

    public MainPostData(String postId, String postTitle, String userId, String moduleId, String browsCount, String likeCount, String releaseTime) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.userId = userId;
        this.moduleId = moduleId;
        this.browsCount = browsCount;
        this.likeCount = likeCount;
        this.releaseTime = releaseTime;
    }

    public MainPostData(String postId, String postTitle, String userId, String moduleId, String browsCount, String likeCount) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.userId = userId;
        this.moduleId = moduleId;
        this.browsCount = browsCount;
        this.likeCount = likeCount;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getBrowsCount() {
        return browsCount;
    }

    public void setBrowsCount(String browsCount) {
        this.browsCount = browsCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }
}
