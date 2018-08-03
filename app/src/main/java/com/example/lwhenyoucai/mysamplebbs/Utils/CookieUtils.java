package com.example.lwhenyoucai.mysamplebbs.Utils;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import org.apache.http.cookie.Cookie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwhenyoucai on 2018/4/17.
 */
public class CookieUtils {

    private static List<Cookie> cookies;

    /* 返回cookies列表 */
    public static List<Cookie> getCookies() {
        return cookies != null ? cookies : new ArrayList<Cookie>();
    }

    /* 设置cookies列表 */
    public static void setCookies(List<Cookie> cookies) {
        CookieUtils.cookies = cookies;
    }

    /* 存储cookie */
    public static void saveCookie(AsyncHttpClient client, Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        client.setCookieStore(cookieStore);
    }

    /* 得到cookie */
    public static List<Cookie> getCookie(Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        List<Cookie> cookies = cookieStore.getCookies();
        Log.e("cookies", cookies.toString());
        return cookies;
    }

    /* 清除cookie */
    public static void clearCookie(Context context) {
        PersistentCookieStore cookieStore = new PersistentCookieStore(context);
        cookieStore.clear();
    }

    /**
     * 解析保存到的 cookies
     *
     * @return cookies 值
     */
    public static String parseCookie( List<Cookie> cookies) {
        String cookies_value = null;
        // 判断cookies 是不是为空
        if (cookies.isEmpty()) {
            System.out.println("cookies为空");
        } else { // cookies 不为空，就从list中取出cookies 的value 值
            for (int i = 0; i < cookies.size(); i++) {
                if (cookies.get(i).getName().equals("token")){
                    cookies_value = cookies.get(i).getName() + "=" + cookies.get(i).getValue(); //拿到我们的 cookie 类型为 string
                    return cookies_value;
                }
            }
        }
        return cookies_value;
    }


}
