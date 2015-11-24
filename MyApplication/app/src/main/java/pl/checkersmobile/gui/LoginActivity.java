package pl.checkersmobile.gui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.checkersmobile.R;

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
}
