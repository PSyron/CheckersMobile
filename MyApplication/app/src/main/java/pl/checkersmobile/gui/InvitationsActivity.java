package pl.checkersmobile.gui;

import android.content.Intent;
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
import pl.checkersmobile.model.User;
import pl.checkersmobile.utils.PrefsHelper;

public class InvitationsActivity extends BaseAppBarActivity implements InviteInterface {

    @Bind(R.id.activity_invitations_list)
    ListView mInvitationList;
    InvitationsAdapter mAdapter;
    Invite mInvite;

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
        mInvite = invite;
        HttpRequestHelper.getInstance(this).acceptInvitation(PrefsHelper.getSessionToken(), invite
                .getIdGame());
    }

    @Override
    public void onSendInvitationClicked(User user) {
        // nie dotyczy
    }


    public void onEvent(GetInvitesEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            mAdapter = new InvitationsAdapter(this, event.getInvites());
        } else {
            mAdapter = new InvitationsAdapter(this, null);
            // Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
        }
        mInvitationList.setAdapter(mAdapter);
    }


    public void onEvent(AcceptInvitationEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            Intent intent = new Intent(this, GameTableActivity.class);
            intent.putExtra(GameTableActivity.EXTRA_IS_WHIE, false);
            intent.putExtra(GameTableActivity.EXTRA_ENEMY_NAME, mInvite.getPlayerName());
            PrefsHelper.setGameId(event.getGameID());
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
        }
    }

}
