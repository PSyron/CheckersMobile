package pl.checkersmobile.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.checkersmobile.R;

public class MenuActivity extends Activity {

    @Bind(R.id.activity_menu_btnOffline)
    TextView btnOffline;
    @Bind(R.id.activity_menu_btnInvitations)
    TextView btnLobbt;
    @Bind(R.id.activity_menu_btnSettings)
    TextView btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.activity_menu_btnInvitations)
    protected  void invitations()
    {
        Intent myIntent = new Intent(this, InvitationsActivity.class);
        this.startActivity(myIntent);
    }
    @OnClick(R.id.activity_menu_btnOffline)
    protected void game()
    {
        Intent myIntent = new Intent(this, GameTableActivity.class);
        this.startActivity(myIntent);
    }





}
