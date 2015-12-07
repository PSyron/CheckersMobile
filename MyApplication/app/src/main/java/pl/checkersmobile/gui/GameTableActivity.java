package pl.checkersmobile.gui;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gametable);
        ButterKnife.bind(this);
        initBoard();
    }

    private void initBoard()
    {
        //gv1.setAdapter(new GameTableAdapter(this, Enums.GridTableType.PlayerOne));
        //gv2.setAdapter(new GameTableAdapter(this, Enums.GridTableType.PlayerTwo));
        gvMain.setAdapter(new GameTableAdapter(this, Enums.GridTableType.GameTable));
    }
    //TODO - początkowa pozycja
    private void SetStartingPosition()
    {

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
