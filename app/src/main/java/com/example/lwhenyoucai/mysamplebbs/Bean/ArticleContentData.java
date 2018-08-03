package com.example.lwhenyoucai.mysamplebbs.Bean;

/**
 * Created by lwhenyoucai on 2018/4/28.
 */
public class ArticleContentData {

    String postContentId;
    String postContent;
    String imagList;
    String recordList;
    String videoList;

    public ArticleContentData(String postContentId, String postContent, String imagList, String recordList, String videoList) {
        this.postContentId = postContentId;
        this.postContent = postContent;
        this.imagList = imagList;
        this.recordList = recordList;
        this.videoList = videoList;
    }

    public String getPostContentId() {
        return postContentId;
    }

    public void setPostContentId(String postContentId) {
        this.postContentId = postContentId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getImagList() {
        return imagList;
    }

    public void setImagList(String imagList) {
        this.imagList = imagList;
    }

    public String getRecordList() {
        return recordList;
    }

    public void setRecordList(String recordList) {
        this.recordList = recordList;
    }

    public String getVideoList() {
        return videoList;
    }

    public void setVideoList(String videoList) {
        this.videoList = videoList;
    }
}
