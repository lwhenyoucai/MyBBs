package com.example.lwhenyoucai.mysamplebbs.PostList;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lwhenyoucai.mysamplebbs.EventBusBean.PostJsonData;
import com.example.lwhenyoucai.mysamplebbs.Bean.MainPostData;
import com.example.lwhenyoucai.mysamplebbs.Bean.PostContentData;
import com.example.lwhenyoucai.mysamplebbs.Bean.PostModuleData;
import com.example.lwhenyoucai.mysamplebbs.EventBusBean.ShowText;
import com.example.lwhenyoucai.mysamplebbs.Bean.UserData;
import com.example.lwhenyoucai.mysamplebbs.PostList.adapter.MainPostDataAdapter;
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
public class PostListPresenter {
    private Activity mActivity;
    private Gson gson;
    private List<MainPostData> mainPostListAll;
    private List<PostContentData> postContentList;
    private List<PostModuleData> postModlueList;
    private List<UserData> userList;

    public PostListPresenter(Activity mActivity) {
        this.mActivity = mActivity;
        gson = new Gson();
        mainPostListAll = new ArrayList<>();
        postContentList = new ArrayList<>();
        postModlueList = new ArrayList<>();
        userList = new ArrayList<>();
    }
    private PostListView postListView = new PostListView() {
        @Override
        public void getNewPostList() {
            AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
            asyncHttpClient.get(mActivity, ServerUrl.postListUrl, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String str = new String(bytes);
                    Log.e("data", str + "");
                    EventBus.getDefault().post(new PostJsonData("getNewPostList",str));
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
                MainPostData mainPostData = gson.fromJson(element, MainPostData.class);
                mainPostListAll.add(mainPostData);
            }
            JsonArray jsonArrayContent = jsonObject.getAsJsonArray("contentList");
            for (JsonElement element : jsonArrayContent) {
                PostContentData contentData = gson.fromJson(element, PostContentData.class);
                postContentList.add(contentData);
            }
            JsonArray jsonArrayModule = jsonObject.getAsJsonArray("moduleList");
            for (JsonElement element : jsonArrayModule) {
                PostModuleData moduleData = gson.fromJson(element, PostModuleData.class);
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
                    EventBus.getDefault().post(new PostJsonData("loadMore",str));
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    EventBus.getDefault().post(new ShowText("获取数据失败"));
                }
            });
        }
    };
    public void setOnItemClickListener(final MainPostDataAdapter mainPostDataAdapter){
        mainPostDataAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Log.e("pos", position + "");
                MainPostData mainPostData = (MainPostData) adapter.getItem(position);
                postListView.addBrowsCount(mainPostData.getPostId(), (Integer.parseInt(mainPostData.getBrowsCount()) + 1));
                mainPostData.setBrowsCount(String.valueOf((Integer.parseInt(mainPostData.getBrowsCount()) + 1)));
                mainPostDataAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getNewPostList() {
        postListView.getNewPostList();
    }

    public void loadMore(int start,int count){
        postListView.loadMore(start,count);
    }

    public Map<String ,Object> setData(String jsonStr){
        return postListView.setData(jsonStr);
    }
    public void addLikeCount(String postId,int likeCount){
        postListView.addLikeCount(postId,likeCount);
    }
}
