package com.example.lwhenyoucai.mysamplebbs.ArticleList;

import java.util.Map;

/**
 * Created by lwhenyoucai on 2018/4/28.
 */
public interface ArticleListView {
    void getNewPostList();
    Map<String ,Object> setData(String data);
    void addBrowsCount(String postId,int browsCount);
    void addLikeCount(String postId,int likeCount);

    void loadMore(int start,int count);

}
