package pl.checkersmobile.gui;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.checkersmobile.R;

public class MenuActivity extends Activity {

    @Bind(R.id.activity_menu_btnOffline)
    TextView btnOffline;
    @Bind(R.id.activity_menu_tnLobby)
    TextView btnLobbt;
    @Bind(R.id.activity_menu_btnSettings)
    TextView btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
    }

}
