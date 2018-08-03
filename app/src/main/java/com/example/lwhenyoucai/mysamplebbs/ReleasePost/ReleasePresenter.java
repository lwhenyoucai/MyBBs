package com.example.lwhenyoucai.mysamplebbs.ReleasePost;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.example.lwhenyoucai.mysamplebbs.Bean.SubmitPostContent;
import com.example.lwhenyoucai.mysamplebbs.Utils.ServerUrl;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.luck.picture.lib.entity.LocalMedia;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Administrator on 2018/5/1.
 */

public class ReleasePresenter {

    private Activity mActivity;
    private Gson gson;
    ReleasePresenter(Activity mActivity){
        this.mActivity = mActivity;
        gson = new Gson();
    }

    ReleaseView releaseView = new ReleaseView() {
        @Override
        public void submit(String postTitle, String postContent, String moduleId, List<LocalMedia> imgList) {
            //String str = gson.toJson(imgList);
            //Log.e("strImg",str+"");

            SubmitPostContent submitPostContent = new SubmitPostContent(postTitle,"1","模板标题","1",postContent);
            String data = gson.toJson(submitPostContent);

            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("postContent",data);
            for (int i = 0; i < imgList.size(); i++) {
                File myFile = new File(imgList.get(i).getCompressPath());
                try {
                    params.put("photo"+i,myFile);
                } catch (FileNotFoundException e1) {
                    continue;
                }
            }
            client.setTimeout(30 * 1000);
            client.post(mActivity, ServerUrl.postImgListUrl, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String str = new String(bytes);
                    Log.e("postImgListUrl", str + "");
                    if (str.equals("success")) {
                        mActivity.setResult(100);
                        mActivity.finish();
                    } else {
                        Toast.makeText(mActivity, "内容发布失败", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    Toast.makeText(mActivity, "网络异常", Toast.LENGTH_SHORT).show();
                }
            });


        }
    };

    public void submit(String postTitle, String postContent, String moduleId, List<LocalMedia> imgList){
        releaseView.submit(postTitle,postContent,moduleId,imgList);
    }

}
