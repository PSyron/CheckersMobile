package pl.checkersmobile.gui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.OnClick;
import pl.checkersmobile.R;
import pl.checkersmobile.gui.Adapter.InvitationsAdapter;

public class InvitationsActivity extends BaseAppBarActivity {

    @Bind(R.id.activity_invitations_list)
    ListView invitationsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);
        getSupportActionBar().setTitle("Zaproszenia");
        invitations();
    }

    protected  void invitations() {
        invitationsList.setAdapter(new InvitationsAdapter(this, new String[]{"test", "test2"}));

    }

}
