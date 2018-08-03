package com.example.lwhenyoucai.mysamplebbs.Utils;

import com.example.lwhenyoucai.mysamplebbs.Utils.CookieUtils;
import com.loopj.android.http.AsyncHttpClient;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * Created by lwhenyoucai on 2018/4/17.
 */
public class FinalAsyncHttpClient {
    private AsyncHttpClient client;

    /* 构造方法 */
    public FinalAsyncHttpClient() {
        client = new AsyncHttpClient();//实例化client
        client.setTimeout(5);//设置5秒超时
        // 获取cookie列表
        if (CookieUtils.getCookies() != null) {
            BasicCookieStore bcs = new BasicCookieStore();
            bcs.addCookies(CookieUtils.getCookies().toArray(
                    new Cookie[CookieUtils.getCookies().size()]));//得到cookie列表
            client.setCookieStore(bcs);//给client加载cookie
        }
    }
    /* 得到client对象方法 */
    public AsyncHttpClient getAsyncHttpClient() {
        return this.client;
    }

}
