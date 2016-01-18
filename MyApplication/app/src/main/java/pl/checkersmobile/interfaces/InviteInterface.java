package pl.checkersmobile.interfaces;

import pl.checkersmobile.model.Invite;
import pl.checkersmobile.model.User;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public interface InviteInterface {

    void onRefuseClicked(Invite invite);

    void onAcceptClicked(Invite invite);

    void onSendInvitationClicked(User user);
}
