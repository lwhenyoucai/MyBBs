package com.example.lwhenyoucai.mysamplebbs.PostList;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.lwhenyoucai.mysamplebbs.ChatUI.ui.activity.ChatMain;
import com.example.lwhenyoucai.mysamplebbs.EventBusBean.PostJsonData;
import com.example.lwhenyoucai.mysamplebbs.Bean.MainPostData;
import com.example.lwhenyoucai.mysamplebbs.EventBusBean.ShowText;
import com.example.lwhenyoucai.mysamplebbs.Login.LoginActivity;
import com.example.lwhenyoucai.mysamplebbs.PostList.adapter.MainPostDataAdapter;
import com.example.lwhenyoucai.mysamplebbs.R;
import com.example.lwhenyoucai.mysamplebbs.ReleasePost.ReleaseMain;
import com.example.lwhenyoucai.mysamplebbs.Utils.SharedPreferencesHelp;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.weavey.loading.lib.LoadingLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PostListMain extends AppCompatActivity {
    //@Bind(R.id.exitSys_bt)
    private Button exitSys_bt;
    //@Bind(R.id.exitSys_bt)
    private Button releasePost_bt;
    //@Bind(R.id.chatUI_bt)
    private Button chatUI_bt;

    private SharedPreferencesHelp sharedPreferencesHelp;
    private RecyclerView msg_list;
    private PostListPresenter postListPresenter;
    private MainPostDataAdapter mainPostDataAdapter;
    private LoadingLayout loading_Post;
    private SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.postlistmain);
        //ButterKnife.bind(this);
        EventBus.getDefault().register(this);//订阅
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        postListPresenter.getNewPostList();
    }


    private void initEvent() {
        chatUI_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostListMain.this, ChatMain.class);
                startActivity(intent);
            }
        });
        exitSys_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferencesHelp.putValue("LoginStatus", "0");
                Intent intent = new Intent(PostListMain.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        releasePost_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostListMain.this, ReleaseMain.class);
                startActivityForResult(intent, 100);
            }
        });

        //下拉刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                postListPresenter.getNewPostList();
                start = 0;
            }
        });
        //错误重新加载
        loading_Post.setOnReloadListener(new LoadingLayout.OnReloadListener() {
            @Override
            public void onReload(View v) {
                postListPresenter.getNewPostList();
                start = 0;
            }
        });
    }

    private void initView() {
        sharedPreferencesHelp = new SharedPreferencesHelp(this, "");
        chatUI_bt = (Button) findViewById(R.id.chatUI_bt);
        exitSys_bt = (Button) findViewById(R.id.exitSys_bt);
        releasePost_bt = (Button) findViewById(R.id.releasePost_bt);
        msg_list = (RecyclerView) findViewById(R.id.msg_list);
        msg_list.setLayoutManager(new LinearLayoutManager(this));
        postListPresenter = new PostListPresenter(this);
        loading_Post = (LoadingLayout) findViewById(R.id.loading_Post);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));//设置经典头部，默认贝塞尔雷达Header
        refreshLayout.finishRefresh(3000);//延迟60*1000毫秒后结束刷新

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    //如果无网络或服务器没有被发现
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(ShowText showText) {
        Toast.makeText(PostListMain.this, showText.getShowText(), Toast.LENGTH_SHORT).show();
        refreshLayout.finishRefresh();
        loading_Post.setStatus(LoadingLayout.Error);
    }

    //加载post数据
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onEvent(PostJsonData event) {
        String key = event.getKey();
        if (key.equals("getNewPostList")) {
            Map<String, Object> map = postListPresenter.setData(event.getJsonData());
            if (mainPostDataAdapter != null) {
                //Log.e("setNewData","----------------->");
                //更新帖子内存数据
                mainPostDataAdapter.setNewMap(map);
                mainPostDataAdapter.setNewData((List<MainPostData>) map.get("postList"));
                if (mainPostDataAdapter.getData().size() > 0) {
                    msg_list.scrollToPosition(0); //滚动到顶部
                }
            } else {
                //Log.e("MainPostDataAdapte","----------------->");
                //加载新数据
                mainPostDataAdapter = new MainPostDataAdapter(postListPresenter, map, PostListMain.this);
                mainPostDataAdapter.addData((List<MainPostData>) map.get("postList"));
                msg_list.setAdapter(mainPostDataAdapter);
            }

            postListPresenter.setOnItemClickListener(mainPostDataAdapter);
            refreshLayout.finishRefresh();
            loading_Post.setStatus(LoadingLayout.Success);
        } else if (key.equals("loadMore")) {
            Map<String, Object> map = postListPresenter.setData(event.getJsonData());
            mainPostDataAdapter.addData((List<MainPostData>) map.get("postList"));
            //mainPostDataAdapter.loadMoreComplete();
        }
        mainPostDataAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                postListPresenter.loadMore(start, count);
            }
        }, msg_list);
        start = start + count;
    }

    private int start = 1;
    private int count = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            //Log.e("onActivityResult","PostListMain");
        }
    }
}
