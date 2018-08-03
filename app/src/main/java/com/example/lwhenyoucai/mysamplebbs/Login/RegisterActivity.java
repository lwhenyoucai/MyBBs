package com.example.lwhenyoucai.mysamplebbs.Login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.lwhenyoucai.mysamplebbs.Bean.UserData;
import com.example.lwhenyoucai.mysamplebbs.R;

/**
 * Created by lwhenyoucai on 2018/4/27.
 */
public class RegisterActivity extends AppCompatActivity {

    private EditText registery_userName;
    private EditText registery_userPsw;
    private EditText registery_userEmail;
    private Button registery_okBt;
    private LoginPresenter loginPresenter;
    private UserData userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registery);
        initView();
        initEvent();
    }

    private void initEvent() {
        registery_okBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = registery_userName.getText().toString();
                String psw = registery_userPsw.getText().toString();
                String email = registery_userEmail.getText().toString();
                userData.setUser_name(name);
                userData.setUser_psw(psw);
                userData.setUser_email(email);
                userData.setPost_count("0");
                userData.setLogin_status("0");

                loginPresenter.registery( userData,"register");
            }
        });
    }

    private void initView() {
        userData = new UserData();
        loginPresenter = new LoginPresenter(this);
        registery_userName = (EditText) findViewById(R.id.registery_userName);
        registery_userPsw = (EditText) findViewById(R.id.registery_userPsw);
        registery_userEmail = (EditText) findViewById(R.id.registery_userEmail);
        registery_okBt = (Button) findViewById(R.id.registery_okBt);
    }
}
