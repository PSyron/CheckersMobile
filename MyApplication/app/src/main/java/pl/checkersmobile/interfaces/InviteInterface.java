package pl.checkersmobile.interfaces;

import pl.checkersmobile.model.Invite;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public interface InviteInterface {

    void onRefuseClicked(Invite invite);

    void onAcceptClicked(Invite invite);
}
