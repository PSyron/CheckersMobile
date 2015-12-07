package pl.checkersmobile.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import pl.checkersmobile.R;
import pl.checkersmobile.communication.BaseEvent;
import pl.checkersmobile.communication.HttpRequestHelper;
import pl.checkersmobile.communication.ResponseStatus;
import pl.checkersmobile.utils.Utils;

public class LoginActivity extends FragmentActivity {

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //Not tested
    @OnClick(R.id.activity_login_btnLogin)
    protected void login() {
        HttpRequestHelper.getInstance(this).login(tvLogin.getText().toString(), tvPassword.getText().toString());
        Utils.showProgressDialog(getSupportFragmentManager(), "Logowanie");
    }

    @OnClick(R.id.activity_login_btnRegister)
    protected void register()
    {
        Intent myIntent = new Intent(this, RegistrationActivity.class);
        this.startActivity(myIntent);
    }

    public void onEvent(BaseEvent event) {
        Utils.hideProgressDialog(getSupportFragmentManager());
        if(event.getStatus() == ResponseStatus.SUCCESS){
            Intent myIntent = new Intent(this, MenuActivity.class);
            this.startActivity(myIntent);
            finish();
        }else{
            Toast.makeText(this, "Login failure", Toast.LENGTH_LONG).show();
        }
    }
}
