package pl.checkersmobile.gui;

import android.os.Bundle;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.checkersmobile.R;
import pl.checkersmobile.gui.Adapter.InvitationsAdapter;

public class InvitationsActivity extends BaseAppBarActivity {

    @Bind(R.id.activity_invitations_list)
    ListView mInvitationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle(R.string.invitation);
        invitations();
    }

    protected void invitations() {
        mInvitationList.setAdapter(new InvitationsAdapter(this, new String[]{"test", "test2"}));
    }
}
