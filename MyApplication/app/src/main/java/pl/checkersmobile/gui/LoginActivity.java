package pl.checkersmobile.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.checkersmobile.R;
import pl.checkersmobile.communication.HttpRequestHelper;

public class LoginActivity extends Activity {

    @Bind(R.id.activity_login_btnLogin)
    TextView btnLogin;
    @Bind(R.id.activity_login_btnRegister)
    TextView btnRegister;
    @Bind(R.id.activity_login_tvLogin)
    TextView tvLogin;
    @Bind(R.id.activity_login_tvPassword)
    TextView tvPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    //Not tested
    @OnClick(R.id.activity_login_btnLogin)
    protected void login() {
        HttpRequestHelper.getInstance(this).login(tvLogin.getText().toString(), tvPassword.getText().toString());
    }

    @OnClick(R.id.activity_login_btnRegister)
    protected   void register()
    {
        Intent myIntent = new Intent(this, RegistrationActivity.class);
        this.startActivity(myIntent);
    }
}
