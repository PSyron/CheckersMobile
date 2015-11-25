package pl.checkersmobile.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.checkersmobile.R;

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
    }

    @OnClick(R.id.activity_registration_btnRegister)
    protected  void register()
    {
        Intent myIntent = new Intent(this, LoginActivity.class);
        this.startActivity(myIntent);
    }
}
