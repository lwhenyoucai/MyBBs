package com.example.lwhenyoucai.mysamplebbs.Bean;

/**
 * Created by Administrator on 2018/5/1.
 */

public class SubmitPostContent {

    String postTitle;
    String moduleId;
    String moduleTitle;
    String userId;
    String postContent;


    public SubmitPostContent(String postTitle, String moduleId, String moduleTitle, String userId, String postContent) {
        this.postTitle = postTitle;
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.userId = userId;
        this.postContent = postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

}
