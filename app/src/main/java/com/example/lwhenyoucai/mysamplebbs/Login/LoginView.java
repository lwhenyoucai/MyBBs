package com.example.lwhenyoucai.mysamplebbs.Login;

import com.example.lwhenyoucai.mysamplebbs.Bean.UserData;

/**
 * Created by lwhenyoucai on 2018/4/25.
 */
public interface LoginView {
    void login(String name,String psw,String action);
    void registery(UserData userData,String action);

}
