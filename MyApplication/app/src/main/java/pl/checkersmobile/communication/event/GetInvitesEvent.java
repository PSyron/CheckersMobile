package pl.checkersmobile.communication.event;

import java.util.List;

import pl.checkersmobile.communication.ResponseStatus;
import pl.checkersmobile.model.Invite;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public class GetInvitesEvent extends BaseEvent {

    private List<Invite> mInvites;

    public GetInvitesEvent(ResponseStatus status, List<Invite> invites) {
        super(status);
        mInvites = invites;
    }

    public List<Invite> getInvites() {
        return mInvites;
    }
}
