package pl.checkersmobile.communication.event;

import pl.checkersmobile.communication.ResponseStatus;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public class AcceptInvitationEvent extends BaseEvent {
    private String gameID;

    public AcceptInvitationEvent(ResponseStatus status) {
        super(status);
    }

    public AcceptInvitationEvent(ResponseStatus status, String gameID) {
        super(status);
        this.gameID = gameID;
    }

    public String getGameID() {
        return gameID;
    }
}
