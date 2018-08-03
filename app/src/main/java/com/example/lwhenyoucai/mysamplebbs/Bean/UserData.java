package com.example.lwhenyoucai.mysamplebbs.Bean;

/**
 * Created by lwhenyoucai on 2018/4/27.
 */
public class UserData  {

    String userId;
    String user_name;
    String user_psw;
    String heardImgUrl;
    String user_email;
    String post_count;
    String login_status;


    public UserData(){}

    public UserData(String userId, String user_name, String user_psw, String heardImgUrl, String user_email, String post_count, String login_status) {
        this.userId = userId;
        this.user_name = user_name;
        this.user_psw = user_psw;
        this.heardImgUrl = heardImgUrl;
        this.user_email = user_email;
        this.post_count = post_count;
        this.login_status = login_status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_psw() {
        return user_psw;
    }

    public void setUser_psw(String user_psw) {
        this.user_psw = user_psw;
    }

    public String getHeardImgUrl() {
        return heardImgUrl;
    }

    public void setHeardImgUrl(String heardImgUrl) {
        this.heardImgUrl = heardImgUrl;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPost_count() {
        return post_count;
    }

    public void setPost_count(String post_count) {
        this.post_count = post_count;
    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }
}
