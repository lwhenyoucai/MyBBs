package com.example.lwhenyoucai.mysamplebbs.PostArticle;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.example.lwhenyoucai.mysamplebbs.R;
import com.example.lwhenyoucai.mysamplebbs.PostArticle.adapter.GridImageAdapter;
import com.example.lwhenyoucai.mysamplebbs.PostArticle.imagUtils.PhotoSelectUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/5/1.
 */

public class ReleaseMain extends AppCompatActivity {

    private EditText postTitle;
    private EditText postContentEdit;
    private SuperTextView moduleSelect;
    private TextView contentCount;
    private RecyclerView contentImag;

    private Button release_bt;
    private ReleasePresenter ReleasePresenter;

    private List<LocalMedia> imgList;
    private GridImageAdapter gridImageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.releasemain);
        initView();
        initEvent();
        initData();
    }

    private void initData() {

        imgList = new ArrayList<LocalMedia>();
        gridImageAdapter = new GridImageAdapter(ReleaseMain.this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                PhotoSelectUtils.setPictureSelector(ReleaseMain.this, imgList);
            }
        });
        gridImageAdapter.setList(imgList);
        gridImageAdapter.setSelectMax(3);
        contentImag.setAdapter(gridImageAdapter);

    }
    private void initEvent() {
        release_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReleasePresenter.submit(postTitle.getText().toString()
                        , postContentEdit.getText().toString()
                        , "1"
                        , imgList);
            }
        });
    }

    private void initView() {
        ReleasePresenter = new ReleasePresenter(this);
        postTitle = (EditText) findViewById(R.id.postTitle);
        postContentEdit = (EditText) findViewById(R.id.postContentEdit);
        moduleSelect = (SuperTextView) findViewById(R.id.moduleSelect);
        contentCount = (TextView) findViewById(R.id.contentCount);
        contentImag = (RecyclerView) findViewById(R.id.contentImag);
        contentImag.setLayoutManager(new GridLayoutManager(this, 3));
        release_bt = (Button) findViewById(R.id.release_bt);

        //imgList = new ArrayList<LocalMedia>();

    }

    //跳转返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    imgList.clear();
                    imgList.addAll(PictureSelector.obtainMultipleResult(data));
                    gridImageAdapter.setList(imgList);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    gridImageAdapter.notifyDataSetChanged();
                    //DebugUtil.i(TAG, "onActivityResult:" + selectList.size());
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(ReleaseMain.this);
                } else {
                    Toast.makeText(ReleaseMain.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
        super.onDestroy();
    }


}
