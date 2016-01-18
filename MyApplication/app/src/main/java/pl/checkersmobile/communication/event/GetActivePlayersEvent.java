package pl.checkersmobile.communication.event;

import java.util.List;

import pl.checkersmobile.communication.ResponseStatus;
import pl.checkersmobile.model.User;

/**
 * Created by syron on 18.01.16.
 */
public class GetActivePlayersEvent extends BaseEvent {
    private List<User> mUsers;

    public GetActivePlayersEvent(ResponseStatus status, List<User> user) {
        super(status);
        mUsers = user;
    }

    public GetActivePlayersEvent(ResponseStatus status) {
        super(status);
    }

    public List<User> getUsers() {
        return mUsers;
    }


}
