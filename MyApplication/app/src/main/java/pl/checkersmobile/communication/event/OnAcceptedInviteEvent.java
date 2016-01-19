package pl.checkersmobile.communication.event;

import pl.checkersmobile.communication.ResponseStatus;

/**
 * Created by paulc_000 on 2016-01-19.
 */
public class OnAcceptedInviteEvent extends BaseEvent {

    private String SecondPlayerName;

    public OnAcceptedInviteEvent(ResponseStatus status, String secondPlayerName) {
        super(status);
        SecondPlayerName = secondPlayerName;
    }


    public OnAcceptedInviteEvent(ResponseStatus status) {
        super(status);
    }

    public String getSecondPlayerName() {
        return SecondPlayerName;
    }
}
