package pl.checkersmobile.gui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import pl.checkersmobile.Adapter.InvitationsAdapter;
import pl.checkersmobile.R;
import pl.checkersmobile.communication.GetInvitesEvent;
import pl.checkersmobile.communication.HttpRequestHelper;
import pl.checkersmobile.communication.ResponseStatus;
import pl.checkersmobile.utils.PrefsHelper;

public class InvitationsActivity extends BaseAppBarActivity {

    @Bind(R.id.activity_invitations_list)
    ListView mInvitationList;
    InvitationsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitations);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        getSupportActionBar().setTitle(R.string.invitation);
        HttpRequestHelper.getInstance(this).getInvites(PrefsHelper.getSessionToken());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(GetInvitesEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            mAdapter = new InvitationsAdapter(this, event.getInvites());
            mInvitationList.setAdapter(mAdapter);
        } else {
            Toast.makeText(this, "Error occoured", Toast.LENGTH_SHORT).show();
        }
    }

}
