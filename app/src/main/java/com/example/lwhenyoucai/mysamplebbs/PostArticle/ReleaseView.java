package com.example.lwhenyoucai.mysamplebbs.PostArticle;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by Administrator on 2018/5/1.
 */

public interface ReleaseView {

    void submit(String postTitle, String postContent, String moduleId, List<LocalMedia> imgList);

}
