package com.example.lwhenyoucai.mysamplebbs.ArticleList;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lwhenyoucai.mysamplebbs.EventBusBean.ArticleJsonData;
import com.example.lwhenyoucai.mysamplebbs.Bean.MainArticleData;
import com.example.lwhenyoucai.mysamplebbs.Bean.ArticleContentData;
import com.example.lwhenyoucai.mysamplebbs.Bean.ArticleModuleData;
import com.example.lwhenyoucai.mysamplebbs.EventBusBean.ShowText;
import com.example.lwhenyoucai.mysamplebbs.Bean.UserData;
import com.example.lwhenyoucai.mysamplebbs.ArticleList.adapter.MainArticleDataAdapter;
import com.example.lwhenyoucai.mysamplebbs.Utils.ServerUrl;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lwhenyoucai on 2018/4/28.
 */
public class ArticleListPresenter {
    private Activity mActivity;
    private Gson gson;
    private List<MainArticleData> mainPostListAll;
    private List<ArticleContentData> postContentList;
    private List<ArticleModuleData> postModlueList;
    private List<UserData> userList;

    public ArticleListPresenter(Activity mActivity) {
        this.mActivity = mActivity;
        gson = new Gson();
        mainPostListAll = new ArrayList<>();
        postContentList = new ArrayList<>();
        postModlueList = new ArrayList<>();
        userList = new ArrayList<>();
    }
    private ArticleListView articleListView = new ArticleListView() {
        @Override
        public void getNewPostList() {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get(mActivity, ServerUrl.postListUrl, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String str = new String(bytes);
                    Log.e("data", str + "");
                    EventBus.getDefault().post(new ArticleJsonData("getNewPostList",str));
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    EventBus.getDefault().post(new ShowText("获取数据失败"));
                }
            });
        }

        @Override
        public Map<String ,Object> setData(String data) {
            Map<String ,Object> map = new HashMap<>();
            //注：在下拉刷新时，需清除内存---------
            map.clear();
            mainPostListAll.clear();
            postContentList.clear();
            postModlueList.clear();
            userList.clear();
            //------------------------------------
            JsonObject jsonObject = new JsonParser().parse(data).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("postList");
            for (JsonElement element : jsonArray) {
                MainArticleData mainArticleData = gson.fromJson(element, MainArticleData.class);
                mainPostListAll.add(mainArticleData);
            }
            JsonArray jsonArrayContent = jsonObject.getAsJsonArray("contentList");
            for (JsonElement element : jsonArrayContent) {
                ArticleContentData contentData = gson.fromJson(element, ArticleContentData.class);
                postContentList.add(contentData);
            }
            JsonArray jsonArrayModule = jsonObject.getAsJsonArray("moduleList");
            for (JsonElement element : jsonArrayModule) {
                ArticleModuleData moduleData = gson.fromJson(element, ArticleModuleData.class);
                postModlueList.add(moduleData);
            }
            JsonArray jsonArrayUser = jsonObject.getAsJsonArray("userList");
            for(JsonElement element: jsonArrayUser){
                UserData userData = gson.fromJson(element,UserData.class);
                userList.add(userData);
            }
            map.put("postList",mainPostListAll);
            map.put("contentList",postContentList);
            map.put("moduleList",postModlueList);
            map.put("userList",userList);
            return map;
        }

        @Override
        public void addBrowsCount(String postId, int browsCount) {
            Log.e("addBrowsCount","调用addBrowsCount");
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("action", "brows");
            params.put("postId", postId);
            params.put("browsCount", browsCount);
            asyncHttpClient.post(mActivity, ServerUrl.postAddCountUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String str = new String (bytes);
                    Log.e("addBrowsCount",str+"");
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });
        }
        @Override
        public void addLikeCount(String postId, int likeCount) {

            Log.e("addLikeCount","postId::"+postId+"likeCount::"+likeCount);
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("action", "like");
            params.put("postId", postId);
            params.put("likeCount", likeCount);
            asyncHttpClient.post(mActivity, ServerUrl.postAddCountUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String str = new String (bytes);
                    Log.e("addLikeCount",str+"");
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });
        }
        @Override
        public void loadMore(int start,int count) {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("start",start);
            params.put("count",count);
            asyncHttpClient.get(mActivity, ServerUrl.postMoreUrl, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String str = new String(bytes);
                    Log.e("data", str + "");
                    EventBus.getDefault().post(new ArticleJsonData("loadMore",str));
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    EventBus.getDefault().post(new ShowText("获取数据失败"));
                }
            });
        }
    };
    public void setOnItemClickListener(final MainArticleDataAdapter mainArticleDataAdapter){
        mainArticleDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e("pos", position + "");
                MainArticleData mainArticleData = (MainArticleData) adapter.getItem(position);
                articleListView.addBrowsCount(mainArticleData.getPostId(), (Integer.parseInt(mainArticleData.getBrowsCount()) + 1));
                mainArticleData.setBrowsCount(String.valueOf((Integer.parseInt(mainArticleData.getBrowsCount()) + 1)));
                mainArticleDataAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getNewPostList() {
        articleListView.getNewPostList();
    }

    public void loadMore(int start,int count){
        articleListView.loadMore(start,count);
    }

    public Map<String ,Object> setData(String jsonStr){
        return articleListView.setData(jsonStr);
    }
    public void addLikeCount(String postId,int likeCount){
        articleListView.addLikeCount(postId,likeCount);
    }
}
