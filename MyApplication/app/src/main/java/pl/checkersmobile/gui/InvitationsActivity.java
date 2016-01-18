package pl.checkersmobile.gui;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import pl.checkersmobile.Adapter.InvitationsAdapter;
import pl.checkersmobile.R;
import pl.checkersmobile.communication.HttpRequestHelper;
import pl.checkersmobile.communication.ResponseStatus;
import pl.checkersmobile.communication.event.AcceptInvitationEvent;
import pl.checkersmobile.communication.event.GetInvitesEvent;
import pl.checkersmobile.interfaces.InviteInterface;
import pl.checkersmobile.model.Invite;
import pl.checkersmobile.utils.PrefsHelper;

public class InvitationsActivity extends BaseAppBarActivity implements InviteInterface {

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

    @Override
    public void onRefuseClicked(Invite invite) {
        HttpRequestHelper.getInstance(this).refuseInvitation(PrefsHelper.getSessionToken(), invite
                .getIdGame());
        HttpRequestHelper.getInstance(this).getInvites(PrefsHelper.getSessionToken());
    }

    @Override
    public void onAcceptClicked(Invite invite) {
        HttpRequestHelper.getInstance(this).acceptInvitation(PrefsHelper.getSessionToken(), invite
                .getIdGame());
    }


    public void onEvent(GetInvitesEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            mAdapter = new InvitationsAdapter(this, event.getInvites());
            mInvitationList.setAdapter(mAdapter);
        } else {
            Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
        }
    }


    public void onEvent(AcceptInvitationEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            //TODO
        } else {
            Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
        }
    }

}
