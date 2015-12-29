package pl.checkersmobile.gui;

import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import pl.checkersmobile.Enums;
import pl.checkersmobile.R;
import pl.checkersmobile.gui.Adapter.GameTableAdapter;

public class GameTableActivity extends BaseAppBarActivity {

    @Bind(R.id.activity_gametable_gvMain)
    GridView gvMain;
    @Bind(R.id.activity_gametable_gv1)
    GridView gv1;
    @Bind(R.id.activity_gametable_gv2)
    GridView gv2;
    @Bind(R.id.activity_gametable_tvNick1)
    TextView tvNick1;
    @Bind(R.id.activity_gametable_tvNick2)
    TextView tvNick2;
    @Bind(R.id.activity_gametable_tvScore1)
    TextView tvScore1;
    @Bind(R.id.activity_gametable_tvScore2)
    TextView tvScore2;

    private ArrayList<Piece> checkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gametable);
        ButterKnife.bind(this);
        setStartingPosition();
        initBoard();
    }

    private void initBoard()
    {
        //gv1.setAdapter(new GameTableAdapter(this, Enums.GridTableType.PlayerOne));
        //gv2.setAdapter(new GameTableAdapter(this, Enums.GridTableType.PlayerTwo));
        gvMain.setAdapter(new GameTableAdapter(this, Enums.GridTableType.GameTable, checkers));
    }
    //TODO - początkowa pozycja
    private void setStartingPosition()
    {
        checkers = new ArrayList<Piece>();

        checkers.add(new Piece(0, 0, Enums.PieceColor.Black));
        checkers.add(new Piece(0, 2, Enums.PieceColor.Black));
        checkers.add(new Piece(0, 4, Enums.PieceColor.Black));
        checkers.add(new Piece(0, 6, Enums.PieceColor.Black));
        checkers.add(new Piece(1, 1, Enums.PieceColor.Black));
        checkers.add(new Piece(1, 3, Enums.PieceColor.Black));
        checkers.add(new Piece(1, 5, Enums.PieceColor.Black));
        checkers.add(new Piece(1, 7, Enums.PieceColor.Black));
        checkers.add(new Piece(2, 0, Enums.PieceColor.Black));
        checkers.add(new Piece(2, 2, Enums.PieceColor.Black));
        checkers.add(new Piece(2, 4, Enums.PieceColor.Black));
        checkers.add(new Piece(2, 6, Enums.PieceColor.Black));

        checkers.add(new Piece(5, 1, Enums.PieceColor.White));
        checkers.add(new Piece(5, 3, Enums.PieceColor.White));
        checkers.add(new Piece(5, 5, Enums.PieceColor.White));
        checkers.add(new Piece(5, 7, Enums.PieceColor.White));
        checkers.add(new Piece(6, 0, Enums.PieceColor.White));
        checkers.add(new Piece(6, 2, Enums.PieceColor.White));
        checkers.add(new Piece(6, 4, Enums.PieceColor.White));
        checkers.add(new Piece(6, 6, Enums.PieceColor.White));
        checkers.add(new Piece(7, 1, Enums.PieceColor.White));
        checkers.add(new Piece(7, 3, Enums.PieceColor.White));
        checkers.add(new Piece(7, 5, Enums.PieceColor.White));
        checkers.add(new Piece(7, 7, Enums.PieceColor.White));

    }

    //wylaczenie scrollowania grida
    // pewnie to psuje drag&dropa, zobaczymy jak to rozwiazac
    @OnTouch({R.id.activity_gametable_gv1, R.id.activity_gametable_gv2, R.id.activity_gametable_gvMain})
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }
        return false;
    }



}
