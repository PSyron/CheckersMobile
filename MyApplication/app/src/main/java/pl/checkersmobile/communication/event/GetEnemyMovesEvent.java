package pl.checkersmobile.communication.event;

import java.util.List;

import pl.checkersmobile.communication.ResponseStatus;
import pl.checkersmobile.model.Move;

/**
 * Created by paulc_000 on 2016-01-19.
 */
public class GetEnemyMovesEvent extends BaseEvent {
    private List<Move> mMoves;

    public GetEnemyMovesEvent(ResponseStatus status) {
        super(status);
    }

    public GetEnemyMovesEvent(ResponseStatus status, List<Move> moves) {
        super(status);
        mMoves = moves;
    }

    public List<Move> getMoves() {
        return mMoves;
    }
}
