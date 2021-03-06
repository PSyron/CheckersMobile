package pl.checkersmobile.gui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import de.greenrobot.event.EventBus;
import pl.checkersmobile.Adapter.GameTableAdapter;
import pl.checkersmobile.CheckerApplication;
import pl.checkersmobile.CheckersData;
import pl.checkersmobile.CheckersMove;
import pl.checkersmobile.Enums;
import pl.checkersmobile.R;
import pl.checkersmobile.communication.HttpRequestHelper;
import pl.checkersmobile.communication.ResponseStatus;
import pl.checkersmobile.communication.event.GetEnemyMovesEvent;
import pl.checkersmobile.communication.event.OnAcceptedInviteEvent;
import pl.checkersmobile.fragment.InviteToGameFragment;
import pl.checkersmobile.model.Move;
import pl.checkersmobile.utils.PrefsHelper;

public class GameTableActivity extends BaseAppBarActivity {

    public static final String EXTRA_IS_WHIE = "extra_is_white";
    public static final String EXTRA_ENEMY_NAME = "extra_enemy_name";
    private final static int INTERVAL = 1000 * 2; //5 sec
    public static int LAST_MOVE_ID = 0;
    @Bind(R.id.activity_gametable_gvMain)
    GridView gvMain;
    @Bind(R.id.activity_gametable_message)
    TextView message;
    @Bind(R.id.activity_gametable_tvScore1)
    TextView tvScore1;
    @Bind(R.id.activity_gametable_tvScoreValue1)
    TextView tvScoreValue1;
    @Bind(R.id.activity_gametable_tvScore2)
    TextView tvScore2;
    @Bind(R.id.activity_gametable_tvScoreValue2)
    TextView tvScoreValue2;
    @Bind(R.id.fragment_container)
    FrameLayout mContainer;
    @Bind(R.id.layout_container)
    LinearLayout mLayoutContainer;
    boolean isPlayerListVisible = false;
    boolean isWhite = true;
    boolean isOnline = false;
    //    This board is also responsible for generating
    //    lists of legal moves.
    CheckersData board;  // The data for the checkers board is kept here.
    boolean gameInProgress; // Is a game currently in progress?
    int actualRow = -1;

    /* The next three variables are valid only when the game is in progress. */
    int actualCol = -1;
    int currentPlayer;      // Whose turn is it now?  The possible values
    //    are CheckersData.WHITE and CheckersData.BLACK.
    int selectedRow, selectedCol;  // If the current player has selected a piece to
    //     move, these give the row and column
    //     containing that piece.  If no piece is
    //     yet selected, then selectedRow is -1.
    CheckersMove[] legalMoves;  // An array containing the legal moves for the
    String mFirstPlayer = "Biały";
    String mSecondPlayer = "Czarny";
    Handler mHandler = new Handler();
    Runnable mHandlerTask = new Runnable() {
        @Override
        public void run() {
            HttpRequestHelper.getInstance(CheckerApplication.getInstance()).getLastMoves
                    (PrefsHelper.getSessionToken(), PrefsHelper.getGameId(), LAST_MOVE_ID + "");
            mHandler.postDelayed(mHandlerTask, INTERVAL);
        }
    };
    //   current player.
    private GameTableAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gametable);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        board = new CheckersData();
        doNewGame(false);
        LAST_MOVE_ID = 0;
        getSupportActionBar().setTitle("Gra offline");
        getSupportActionBar().setHomeButtonEnabled(true);

        if (getIntent().hasExtra(EXTRA_ENEMY_NAME)) {
            onPlayerBlackJoin(getIntent().getStringExtra(EXTRA_ENEMY_NAME));
        } else {
            tvScore1.setText(mFirstPlayer + ":");
            tvScore2.setText(mSecondPlayer + ":");
            HttpRequestHelper.getInstance(this).createTable(PrefsHelper.getSessionToken());
        }
        //initBoard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    void onPlayerWhiteBack(String secondName) {
        hidePlayerList();
        getSupportActionBar().setTitle("Gra online");
        isOnline = true;
        isWhite = true;
        mFirstPlayer = PrefsHelper.getUserLogin();
        mSecondPlayer = secondName;
        tvScore1.setText(mFirstPlayer + ":");
        tvScore2.setText(mSecondPlayer + ":");
        doNewGame(true);
        startGetMoves();
    }

    void onPlayerBlackJoin(String firstPlayer) {
        getSupportActionBar().setTitle("Gra online");
        isOnline = true;
        isWhite = false;
        mFirstPlayer = firstPlayer;
        mSecondPlayer = PrefsHelper.getUserLogin();
        tvScore1.setText(mFirstPlayer + ":");
        tvScore2.setText(mSecondPlayer + ":");
        doNewGame(true);
        startGetMoves();
    }

    void doNewGame(boolean isForceStart) {

        // Begin a new game.
        message.setSelected(true);
        if (gameInProgress == true && !isForceStart) {
            // This should not be possible, but it doens't
            // hurt to check.
            message.setText("Skończ pierwsze aktualną gre!");
            return;
        }
        board.setUpGame();   // Set up the pieces.
        currentPlayer = CheckersData.WHITE;   // WHITE moves first.
        legalMoves = board.getLegalMoves(CheckersData.WHITE);  // Get WHITE's legal moves.
        selectedRow = -1;   // WHITE has not yet selected a piece to move.
        message.setText(mFirstPlayer + getString(R.string.make_move));
        gameInProgress = true;
        // newGameButton.setEnabled(false);
        //resignButton.setEnabled(true);
        initBoard();
    }

    private void showPlayerList() {
        //HttpRequestHelper.getInstance(this).checkActivePlayers(PrefsHelper.getSessionToken());
        isPlayerListVisible = true;
        mContainer.setVisibility(View.VISIBLE);
        mLayoutContainer.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new
                InviteToGameFragment()).commit();
    }

    private void hidePlayerList() {
        isPlayerListVisible = false;
        mContainer.setVisibility(View.GONE);
        mLayoutContainer.setVisibility(View.VISIBLE);
        //TODO remove fragment
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if (isPlayerListVisible) {
            hidePlayerList();
        } else {
            CheckerApplication.getInstance().stopLookingForPlayers();
            stopGetMoves();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_invite:
               /* Toast.makeText(this, "Zaproś do gry", Toast.LENGTH_SHORT)
                        .show();*/
                showPlayerList();
                CheckerApplication.getInstance().startLookingForPlayers();
                break;
            case R.id.action_newgame:
                createAndShowAlertDialog();
                break;
            //    case R.id.action_stats:
             /*   Toast.makeText(this, "Statystyki", Toast.LENGTH_SHORT)
                        .show();*/
            //  break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void initBoard() {
        mAdapter = new GameTableAdapter(this, Enums.GridTableType.GameTable, board.board);
        gvMain.setAdapter(mAdapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((isOnline && ((isWhite && currentPlayer == CheckersData.WHITE) || (!isWhite &&
                        currentPlayer ==
                                CheckersData.BLACK))) || !isOnline) {
                    if (gameInProgress == false)
                        message.setText("Wybierz nową gre");
                    else {
                        int col = position % 8;
                        int row = position / 8;
                        actualRow = row;
                        actualCol = col;
                        if (col >= 0 && col < 8 && row >= 0 && row < 8) {
                            doClickSquare(row, col);
                        }
                    }
                }
            }
        });
    }

    void doClickSquare(int row, int col) {
        // This is called by mousePressed() when a player clicks on the
        // square in the specified row and col.  It has already been checked
        // that a game is, in fact, in progress.

      /* If the player clicked on one of the pieces that the player
         can move, mark this row and col as selected and return.  (This
         might change a previous selection.)  Reset the message, in
         case it was previously displaying an error message. */

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) {
                selectedRow = row;
                selectedCol = col;
                if (currentPlayer == CheckersData.WHITE)
                    message.setText(mFirstPlayer + ":  " + getString(R.string.make_move));
                else
                    message.setText(mSecondPlayer + ":  " + getString(R.string.make_move));
                refreshGrid();
                return;
            }

      /* If no piece has been selected to be moved, the user must first
         select a piece.  Show an error message and return. */

        if (selectedRow < 0 && currentPlayer == (isWhite ? CheckersData.WHITE : CheckersData.BLACK)) {
            message.setText("Zaznacz pionka którego chcesz poruszyć");
            refreshGrid();
            return;
        }

      /* If the user clicked on a squre where the selected piece can be
         legally moved, then make the move and return. */

        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol
                    && legalMoves[i].toRow == row && legalMoves[i].toCol == col) {
                doMakeMove(legalMoves[i]);
                return;
            }

      /* If we get to this point, there is a piece selected, and the square where
         the user just clicked is not one where that piece can be legally moved.
         Show an error message. */
        if (currentPlayer == (isWhite ? CheckersData.WHITE : CheckersData.BLACK)) {
            message.setText("Zaznacz pole na które chcesz przenieść pionka");
        }
        refreshGrid();

    }  // end doClickSquare()

    void refreshGrid() {
        mAdapter = new GameTableAdapter(this, Enums.GridTableType.GameTable, board.board, getLegalPositions());
        tvScoreValue2.setText((12 - mAdapter.getWhiteCount()) + "");
        tvScoreValue1.setText((12 - mAdapter.getBlackCount()) + "");
        gvMain.setAdapter(mAdapter);
    }

    ArrayList<Integer> getLegalPositions() {

        ArrayList<Integer> tempPositions = new ArrayList<Integer>();
        if (legalMoves != null && legalMoves.length > 0) {
            for (int i = 0; i < legalMoves.length; i++)
                if (actualRow == legalMoves[i].fromRow && actualCol == legalMoves[i].fromCol)
                    tempPositions.add(legalMoves[i].toRow * 8 + legalMoves[i].toCol);
        }
        return tempPositions;
    }

    void doMakeMove(CheckersMove move) {
        // This is called when the current player has chosen the specified
        // move.  Make the move, and then either end or continue the game
        // appropriately.

        board.makeMove(move);
        if (isOnline && currentPlayer == (isWhite ? CheckersData.WHITE : CheckersData.BLACK))
            HttpRequestHelper.getInstance(this).makeMove(PrefsHelper.getSessionToken(), PrefsHelper
                    .getGameId(), move);
      /* If the move was a jump, it's possible that the player has another
         jump.  Check for legal jumps starting from the square that the player
         just moved to.  If there are any, the player must jump.  The same
         player continues moving.
      */

        if (move.isJump()) {
            legalMoves = board.getLegalJumpsFrom(currentPlayer, move.toRow, move.toCol);
            if (legalMoves != null) {
                if (currentPlayer == CheckersData.WHITE)
                    message.setText(mFirstPlayer + ":  musi skakać dalej.");
                else
                    message.setText(mSecondPlayer + ": musi skakać dalej.");
                selectedRow = move.toRow;  // Since only one piece can be moved, select it.
                selectedCol = move.toCol;
                refreshGrid();
                return;
            } else {
                sendFinishMove();
            }
        } else {
            sendFinishMove();
        }


      /* The current player's turn is ended, so change to the other player.
         Get that player's legal moves.  If the player has no legal moves,
         then the game ends. */

        if (currentPlayer == CheckersData.WHITE) {
            currentPlayer = CheckersData.BLACK;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
                gameOver(mSecondPlayer + " niema ruchu. " + mFirstPlayer + " wygrywa.");
            else if (legalMoves[0].isJump())
                message.setText(mSecondPlayer + ":  " + getString(R.string.make_move) + ".  Musi " +
                        "skakać.");
            else
                message.setText(mSecondPlayer + ":  " + getString(R.string.make_move));
        } else {
            currentPlayer = CheckersData.WHITE;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
                gameOver(mFirstPlayer + " niema ruchu. " + mSecondPlayer + " wygrywa.");
            else if (legalMoves[0].isJump())
                message.setText(mFirstPlayer + ":  " + getString(R.string.make_move) + ".  Musi " +
                        "skakać.");
            else
                message.setText(mFirstPlayer + ":  " + getString(R.string.make_move));
        }

      /* Set selectedRow = -1 to record that the player has not yet selected
          a piece to move. */

        selectedRow = -1;

      /* As a courtesy to the user, if all legal moves use the same piece, then
         select that piece automatically so the use won't have to click on it
         to select it. */

        if (legalMoves != null) {
            boolean sameStartSquare = true;
            for (int i = 1; i < legalMoves.length; i++)
                if (legalMoves[i].fromRow != legalMoves[0].fromRow
                        || legalMoves[i].fromCol != legalMoves[0].fromCol) {
                    sameStartSquare = false;
                    break;
                }
            if (sameStartSquare) {
                selectedRow = legalMoves[0].fromRow;
                selectedCol = legalMoves[0].fromCol;
            }
        }


      /* Make sure the board is redrawn in its new state. */

        refreshGrid();
    }  // end doMakeMove();

    void sendFinishMove() {
   /*     final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isOnline && currentPlayer == (isWhite ? CheckersData.WHITE : CheckersData.BLACK))
                    HttpRequestHelper.getInstance(GameTableActivity.this).finishMove(PrefsHelper
                                    .getSessionToken(),
                            PrefsHelper
                            .getGameId());
            }
        }, 1000);*/
    }

    void gameOver(String str) {
        // The game ends.  The parameter, str, is displayed as a message
        // to the user.  The states of the buttons are adjusted so playes
        // can start a new game.
        message.setText(str);
        // newGameButton.setEnabled(true);
        // resignButton.setEnabled(false);
        gameInProgress = false;
    }

    //wylaczenie scrollowania grida
    // pewnie to psuje drag&dropa, zobaczymy jak to rozwiazac
    @OnTouch(R.id.activity_gametable_gvMain)
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return false;
    }

    private void createAndShowAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aktualna rozgrywka zostanie zakończona. Czy jesteś pewien?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //TODO
                HttpRequestHelper.getInstance(GameTableActivity.this).createTable(PrefsHelper.getSessionToken());
                doNewGame(true);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void startGetMoves() {
        if (isOnline)
            mHandlerTask.run();
    }

    public void stopGetMoves() {
        if (mHandler != null)
            mHandler.removeCallbacks(mHandlerTask);
    }


    public void onEvent(GetEnemyMovesEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            //Ruch jest nowszy niz ostatni tutaj
            if (LAST_MOVE_ID < event.getMoves().get(event.getMoves().size() - 1).getIdMove()) {
                LAST_MOVE_ID = event.getMoves().get(event.getMoves().size() - 1).getIdMove();
                for (Move move : event.getMoves()) {
                    if (move.getPreColumn() > -1) //it is not -1 or -2
                        doMakeMove(move.getCheckerMove());
                   /* else{
                        currentPlayer = isWhite ? CheckersData.WHITE : CheckersData.BLACK;
                    }*/
                }
                //  currentPlayer = isWhite ? CheckersData.WHITE : CheckersData.BLACK;
            }
        } else {
            Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
        }
    }

    public void onEvent(OnAcceptedInviteEvent event) {
        if (event.getStatus() == ResponseStatus.SUCCESS) {
            Toast.makeText(this, event.getSecondPlayerName() + " zaakceptował zaproszenie", Toast.LENGTH_SHORT).show();
            onPlayerWhiteBack(event.getSecondPlayerName());
        } else {
            Toast.makeText(this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
        }
    }

}
