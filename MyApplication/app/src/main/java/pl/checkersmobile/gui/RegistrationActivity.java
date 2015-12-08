package pl.checkersmobile.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class RegistrationActivity extends Activity {

    @Bind(R.id.activity_registration_btnRegister)
    TextView btnRegister;
    @Bind(R.id.activity_registration_tvLogin)
    TextView tvLogin;
    @Bind(R.id.activity_registration_tvPassword)
    TextView tvPassword;
    @Bind(R.id.activity_registration_tvPasswordSnd)
    TextView tvPasswordSnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }

    @OnClick(R.id.activity_registration_btnRegister)
    protected void register() {
        HttpRequestHelper.getInstance(this).register(tvLogin.getText().toString(), tvLogin.getText().toString(), tvPassword.getText().toString());
    }

    public void onEvent(BaseEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
            finish();
        } else {
            Toast.makeText(this, "Registration failure", Toast.LENGTH_LONG).show();
        }
    }

}
