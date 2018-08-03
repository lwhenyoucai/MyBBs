package com.example.lwhenyoucai.mysamplebbs.ArticleList.adapter;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.lwhenyoucai.mysamplebbs.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by Administrator on 2018/5/2.
 */

public class ImageShowReAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Activity mActivity;
    private List<String> imageURLList;
    public ImageShowReAdapter(Activity mActivity, List<String> data) {
        super(R.layout.item_image_show, data);
        this.mActivity = mActivity;
        imageURLList = data;
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {

        String url = item.toString();
        //Log.e("url",url+"");
        RoundedImageView roundedImageView = helper.getView(R.id.item_image_showImage);
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
                .into(roundedImageView);
    }
}
