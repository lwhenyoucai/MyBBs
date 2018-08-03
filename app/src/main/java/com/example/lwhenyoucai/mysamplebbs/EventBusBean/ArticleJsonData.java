package com.example.lwhenyoucai.mysamplebbs.EventBusBean;

/**
 * Created by Administrator on 2018/5/1.
 */

public class ArticleJsonData {
    private String key;
    private String jsonData;

    public ArticleJsonData(String key, String jsonData) {
        this.key = key;
        this.jsonData = jsonData;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
