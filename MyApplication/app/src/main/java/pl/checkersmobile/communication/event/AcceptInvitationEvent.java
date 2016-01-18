package pl.checkersmobile.communication.event;

import pl.checkersmobile.communication.ResponseStatus;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public class AcceptInvitationEvent extends BaseEvent {
    public AcceptInvitationEvent(ResponseStatus status) {
        super(status);
    }
}
