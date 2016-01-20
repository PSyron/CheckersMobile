package pl.checkersmobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import pl.checkersmobile.Adapter.ActivePlayersAdapter;
import pl.checkersmobile.R;
import pl.checkersmobile.communication.HttpRequestHelper;
import pl.checkersmobile.communication.ResponseStatus;
import pl.checkersmobile.communication.event.GetActivePlayersEvent;
import pl.checkersmobile.interfaces.InviteInterface;
import pl.checkersmobile.model.Invite;
import pl.checkersmobile.model.User;
import pl.checkersmobile.utils.PrefsHelper;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public class InviteToGameFragment extends Fragment implements InviteInterface {

    ListView mPlayerList;

    ActivePlayersAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_invitations,
                container, false);
        mPlayerList = (ListView) view.findViewById(R.id.activity_invitations_list);
        EventBus.getDefault().register(this);
        HttpRequestHelper.getInstance(getActivity()).checkActivePlayers(PrefsHelper.getSessionToken());
        //mAdapter = new ActivePlayersAdapter(getActivity(),)
        return view;

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onRefuseClicked(Invite invite) {
        // nie dotyczy
    }

    @Override
    public void onAcceptClicked(Invite invite) {
        //nie dotyczy
    }

    @Override
    public void onSendInvitationClicked(User user) {
        Toast.makeText(getActivity(), "Wysłano zaproszenie do " + user.getName(), Toast.LENGTH_SHORT).show();
        HttpRequestHelper.getInstance(getActivity()).sendInvitations(PrefsHelper.getSessionToken(), user.getName(), PrefsHelper.getGameId());
    }

    public void onEvent(GetActivePlayersEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            mAdapter = new ActivePlayersAdapter(getActivity(), this, event.getUsers());
            mPlayerList.setAdapter(mAdapter);
        } else {
            Toast.makeText(getActivity(), "Get active players failure", Toast.LENGTH_LONG).show();
        }
    }
}
