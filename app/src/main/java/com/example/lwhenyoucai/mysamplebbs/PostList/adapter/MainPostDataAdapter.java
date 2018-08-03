package com.example.lwhenyoucai.mysamplebbs.PostList.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lwhenyoucai.mysamplebbs.Bean.MainPostData;
import com.example.lwhenyoucai.mysamplebbs.Bean.PostContentData;
import com.example.lwhenyoucai.mysamplebbs.Bean.PostModuleData;
import com.example.lwhenyoucai.mysamplebbs.Bean.UserData;
import com.example.lwhenyoucai.mysamplebbs.PostList.PostListPresenter;
import com.example.lwhenyoucai.mysamplebbs.R;
import com.example.lwhenyoucai.mysamplebbs.Utils.AnalysisUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lwhenyoucai on 2018/4/28.
 */
public class MainPostDataAdapter extends BaseQuickAdapter<MainPostData, BaseViewHolder> {
    private Activity mActivity;
    private Map<String ,Object> map;
    //private Gson gson;
    private List<String> imgList;
    private PostListPresenter postListPresenter;

    public MainPostDataAdapter(PostListPresenter postListPresenter,Map<String , Object> map, Activity mActivity) {
        super(R.layout.postlistmain_item);//, (List<MainPostData>) map.get("postList")
        this.mActivity = mActivity;
        this.map = map;
        //gson = new Gson();
        imgList = new ArrayList<>();
        this.postListPresenter = postListPresenter;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MainPostData item) {
        for (PostContentData postContentData : (List<PostContentData>)map.get("contentList")) {
            if (postContentData.getPostContentId().equals(item.getPostId())) {
                helper.setText(R.id.postList_content,postContentData.getPostContent()+"");
                imgList = AnalysisUtils.analysisStringList(postContentData.getImagList());
            }
        }
        for(PostModuleData postModuleData:(List<PostModuleData>)map.get("moduleList")){
            if(postModuleData.getModuleId().equals(item.getModuleId())){
                helper.setText(R.id.postList_title, item.getPostTitle() + "");
                helper.setText(R.id.postmodule,"  #"+postModuleData.getModuleTitle());
            }
        }
        for(UserData userData: (List<UserData>)map.get("userList")){
            if(userData.getUserId().equals(item.getUserId())){
                helper.setText(R.id.userName,userData.getUser_name()+"");
                final String url = userData.getHeardImgUrl();
                ImageView imageView = helper.getView(R.id.userHreadImg);
                //glide4.00  加载图片 代码施加圆角代码
                RequestOptions requestOptions = new RequestOptions();
                //圆形模糊图片
                //requestOptions.circleCrop();
                //圆形图片
                //requestOptions.optionalTransform(new CircleCrop());
                //居中裁剪显示
                requestOptions.centerCrop();
                //圆角图片
                requestOptions.optionalTransform(new RoundedCorners(20));
                Glide.with(mActivity).load(url)
                        .apply(requestOptions)
                        .into(imageView);

            }
        }

        helper.setText(R.id.browsCount,"浏览"+item.getBrowsCount());
        helper.setText(R.id.likeCount,"喜欢"+item.getLikeCount());
        helper.setText(R.id.releaseTime,"发布时间"+item.getReleaseTime());

        RecyclerView imgList_recy = helper.getView(R.id.imgList_recy);
        imgList_recy.setLayoutManager(new GridLayoutManager(mActivity,3));
        if (imgList.size() > 0) {
            ImageShowReAdapter imageShowReAdapter = new ImageShowReAdapter(mActivity, imgList);
            imgList_recy.setAdapter(imageShowReAdapter);
        }

        TextView textView = helper.getView(R.id.likeCount);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postListPresenter.addLikeCount(item.getPostId(), (Integer.parseInt(item.getLikeCount()) + 1));
                item.setLikeCount(String.valueOf(Integer.parseInt(item.getLikeCount()) + 1));
                notifyDataSetChanged();
            }
        });
    }

    public void setNewMap(Map<String ,Object> newMap){
        map.clear();
        map = newMap;
    }
}
