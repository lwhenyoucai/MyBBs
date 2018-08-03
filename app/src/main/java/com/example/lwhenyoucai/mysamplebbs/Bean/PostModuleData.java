package com.example.lwhenyoucai.mysamplebbs.Bean;

/**
 * Created by Administrator on 2018/5/1.
 */

public class PostModuleData {

    String moduleId;
    String moduleTitle;
    String moduleDesc;

    public PostModuleData(String moduleId, String moduleTitle, String moduleDesc) {
        this.moduleId = moduleId;
        this.moduleTitle = moduleTitle;
        this.moduleDesc = moduleDesc;
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

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }
}
