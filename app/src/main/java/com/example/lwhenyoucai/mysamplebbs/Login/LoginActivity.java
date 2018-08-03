package com.example.lwhenyoucai.mysamplebbs.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.lwhenyoucai.mysamplebbs.ArticleList.ArticleListMain;
import com.example.lwhenyoucai.mysamplebbs.R;
import com.example.lwhenyoucai.mysamplebbs.Utils.SharedPreferencesHelp;
/**
 * Created by lwhenyoucai on 2018/4/25.
 */
public class LoginActivity extends AppCompatActivity {

    private LoginPresenter loginPresenter;
    private TextView user_name;
    private TextView user_psw;
    private Button login;
    private Button registery;
    private SharedPreferencesHelp sharedPreferencesHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/

        initView();
        initEvent();
        if(sharedPreferencesHelp.getValue("LoginStatus").equals("1")){
            Intent intent = new Intent(LoginActivity.this, ArticleListMain.class);
            startActivityForResult(intent, 1);
            finish();
        }
    }

    private void initView() {
        sharedPreferencesHelp = new SharedPreferencesHelp(this, "");
        loginPresenter = new LoginPresenter(this);
        user_name = (TextView) findViewById(R.id.user_name);
        user_psw = (TextView) findViewById(R.id.user_psw);
        login = (Button) findViewById(R.id.login);
        registery = (Button) findViewById(R.id.registery);
    }
    private void initEvent() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(user_name.getText().toString(), user_psw.getText().toString(), "login");
            }
        });
        registery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1&&resultCode==1){
            if(data!=null){
                user_name.setText(data.getStringExtra("name"));
                user_psw.setText(data.getStringExtra("psw"));
            }
        }
    }
}
