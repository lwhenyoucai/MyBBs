package com.example.lwhenyoucai.mysamplebbs.Login;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.example.lwhenyoucai.mysamplebbs.Bean.UserData;
import com.example.lwhenyoucai.mysamplebbs.ArticleList.ArticleListMain;
import com.example.lwhenyoucai.mysamplebbs.Utils.CookieUtils;
import com.example.lwhenyoucai.mysamplebbs.Utils.FinalAsyncHttpClient;
import com.example.lwhenyoucai.mysamplebbs.Utils.ServerUrl;
import com.example.lwhenyoucai.mysamplebbs.Utils.SharedPreferencesHelp;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by lwhenyoucai on 2018/4/25.
 */
public class LoginPresenter {
    private Activity mContext;
    private SharedPreferencesHelp sharedPreferencesHelp;

    LoginPresenter(Activity mContext){
        this.mContext = mContext;
        sharedPreferencesHelp = new SharedPreferencesHelp(mContext,"");
    }

    private LoginView loginView = new LoginView() {
        @Override
        public void login(String name, String psw, String action) {
            Log.e("login", "name:" + name + ",psw:" + psw + ",action:" + action + "");
            if (name.equals("") || psw.equals("")) {
                Toast.makeText(mContext, "信息不完整", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    //AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    AsyncHttpClient asyncHttpClient = new FinalAsyncHttpClient().getAsyncHttpClient();
                    CookieUtils.saveCookie(asyncHttpClient, mContext);
                    RequestParams params = new RequestParams();
                    params.put("action", action);
                    params.put("username", name);
                    params.put("password", psw);
                    asyncHttpClient.post(mContext, ServerUrl.loginUrl, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            CookieUtils.setCookies(CookieUtils.getCookie(mContext));//保存获得的cookies
                            String token = CookieUtils.parseCookie(CookieUtils.getCookie(mContext));
                            Log.e("token",token+"");
                            sharedPreferencesHelp.putValue("Token", token);
                            String str = new String(bytes);
                            JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
                            JsonPrimitive loginStaus = jsonObject.getAsJsonPrimitive("success");
                            if (loginStaus.getAsString().equals("1")) {
                                //Log.e("onSuccess", str + "登陆成功");
                                sharedPreferencesHelp.putValue("LoginStatus", "1");
                                Intent intent = new Intent(mContext, ArticleListMain.class);
                                mContext.startActivity(intent);
                                mContext.finish();
                            } else {
                                Toast.makeText(mContext, "账号或密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void registery(final UserData userData, String action) {
            Log.e("registery","name:"+userData.getUser_name()+",psw:"+userData.getUser_psw()+",action:"+action+"");
            if (userData.getUser_name().equals("") || userData.getUser_psw().equals("")) {
                Toast.makeText(mContext, "信息不完整", Toast.LENGTH_SHORT).show();
            } else {
                Gson gson = new Gson();
                String data = gson.toJson(userData);
                try {
                    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("action", action);
                    params.put("username", userData.getUser_name());
                    params.put("password", userData.getUser_psw());
                    params.put("jsonUserData",data);
                    asyncHttpClient.post(mContext, ServerUrl.loginUrl, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            String str = new String(bytes);
                            Log.e("data",str+"");
                            JsonObject jsonObject = new JsonParser().parse(str).getAsJsonObject();
                            JsonPrimitive jsonPrimitive = jsonObject.getAsJsonPrimitive("register_result");
                            if(jsonPrimitive.getAsString().equals("0")){
                                Toast.makeText(mContext,"用户已存在",Toast.LENGTH_SHORT).show();
                            }else if(jsonPrimitive.getAsString().equals("1")){
                                Toast.makeText(mContext,"注册成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("name",userData.getUser_name());
                                intent.putExtra("psw",userData.getUser_psw());
                                mContext.setResult(1,intent);
                                mContext.finish();
                            }else if(jsonPrimitive.getAsString().equals("2")){
                                Toast.makeText(mContext,"注册失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            Toast.makeText(mContext,"注册失败",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    public void login(String name,String psw,String action){
        loginView.login(name,psw,action);
    }
    public void registery(UserData userData,String action){
        loginView.registery(userData,action);
    }

}
