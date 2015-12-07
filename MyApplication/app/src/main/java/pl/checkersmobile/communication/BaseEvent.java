package pl.checkersmobile.communication;

/**
 * Created by syron on 07.12.15.
 */
public class BaseEvent {
    private ResponseStatus status;

    public BaseEvent(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }
}
