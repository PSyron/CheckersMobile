package pl.checkersmobile.gui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import pl.checkersmobile.Adapter.GameTableAdapter;
import pl.checkersmobile.CheckersData;
import pl.checkersmobile.CheckersMove;
import pl.checkersmobile.Enums;
import pl.checkersmobile.R;

public class GameTableActivity extends BaseAppBarActivity {

    public static final int
            EMPTY = 0,
            RED = 1,
            RED_KING = 2,
            BLACK = 3,
            BLACK_KING = 4;
    @Bind(R.id.activity_gametable_gvMain)
    GridView gvMain;
    @Bind(R.id.activity_gametable_gv1)
    GridView gv1;
    @Bind(R.id.activity_gametable_gv2)
    GridView gv2;
    /*    @Bind(R.id.activity_gametable_tvNick1)
        TextView tvNick1;
        @Bind(R.id.activity_gametable_tvNick2)
        TextView tvNick2;*/
    @Bind(R.id.activity_gametable_message)
    TextView message;
    @Bind(R.id.activity_gametable_tvScore2)
    TextView tvScore2;
    CheckersData board;  // The data for the checkers board is kept here.
    //    This board is also responsible for generating
    //    lists of legal moves.

    boolean gameInProgress; // Is a game currently in progress?

   /* The next three variables are valid only when the game is in progress. */

    int currentPlayer;      // Whose turn is it now?  The possible values
    //    are CheckersData.WHITE and CheckersData.BLACK.
    int selectedRow, selectedCol;  // If the current player has selected a piece to
    //     move, these give the row and column
    //     containing that piece.  If no piece is
    //     yet selected, then selectedRow is -1.
    CheckersMove[] legalMoves;  // An array containing the legal moves for the
    //   current player.
    private GameTableAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gametable);
        ButterKnife.bind(this);
        board = new CheckersData();
        doNewGame();
        //initBoard();
    }

    void doNewGame() {
        // Begin a new game.
        message.setSelected(true);
        if (gameInProgress == true) {
            // This should not be possible, but it doens't
            // hurt to check.
            message.setText("Finish the current game first!");
            return;
        }
        board.setUpGame();   // Set up the pieces.
        currentPlayer = CheckersData.WHITE;   // WHITE moves first.
        legalMoves = board.getLegalMoves(CheckersData.WHITE);  // Get WHITE's legal moves.
        selectedRow = -1;   // WHITE has not yet selected a piece to move.
        message.setText("Red:  Make your move.");
        gameInProgress = true;
        // newGameButton.setEnabled(false);
        //resignButton.setEnabled(true);
        initBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_invite:
                Toast.makeText(this, "Zaproszenie do gry", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.action_stats:
                Toast.makeText(this, "Statystyki", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.action_leave:
                Toast.makeText(this, "Wyjscie z gry", Toast.LENGTH_SHORT)
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }


    private void initBoard()
    {
        //gv1.setAdapter(new GameTableAdapter(this, Enums.GridTableType.PlayerOne));
        //gv2.setAdapter(new GameTableAdapter(this, Enums.GridTableType.PlayerTwo));
        //  setStartingPosition();
        mAdapter = new GameTableAdapter(this, Enums.GridTableType.GameTable, board.board);
        gvMain.setAdapter(mAdapter);
        gvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (gameInProgress == false)
                    message.setText("Click \"New Game\" to start a new game.");
                else {
                    int col = position % 8;
                    int row = position / 8;
                    if (col >= 0 && col < 8 && row >= 0 && row < 8)
                        doClickSquare(row, col);
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
                    message.setText("WHITE:  Make your move.");
                else
                    message.setText("BLACK:  Make your move.");
                refreshGrid();
                return;
            }

      /* If no piece has been selected to be moved, the user must first
         select a piece.  Show an error message and return. */

        if (selectedRow < 0) {
            message.setText("Click the piece you want to move.");
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

        message.setText("Click the square you want to move to.");

    }  // end doClickSquare()

    void refreshGrid() {
        mAdapter = new GameTableAdapter(this, Enums.GridTableType.GameTable, board.board);
        gvMain.setAdapter(mAdapter);
    }

    void doMakeMove(CheckersMove move) {
        // Thiis is called when the current player has chosen the specified
        // move.  Make the move, and then either end or continue the game
        // appropriately.

        board.makeMove(move);

      /* If the move was a jump, it's possible that the player has another
         jump.  Check for legal jumps starting from the square that the player
         just moved to.  If there are any, the player must jump.  The same
         player continues moving.
      */

        if (move.isJump()) {
            legalMoves = board.getLegalJumpsFrom(currentPlayer, move.toRow, move.toCol);
            if (legalMoves != null) {
                if (currentPlayer == CheckersData.WHITE)
                    message.setText("WHITE:  You must continue jumping.");
                else
                    message.setText("BLACK:  You must continue jumping.");
                selectedRow = move.toRow;  // Since only one piece can be moved, select it.
                selectedCol = move.toCol;
                refreshGrid();
                return;
            }
        }

      /* The current player's turn is ended, so change to the other player.
         Get that player's legal moves.  If the player has no legal moves,
         then the game ends. */

        if (currentPlayer == CheckersData.WHITE) {
            currentPlayer = CheckersData.BLACK;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("BLACK has no moves.  WHITE wins.");
            else if (legalMoves[0].isJump())
                message.setText("BLACK:  Make your move.  You must jump.");
            else
                message.setText("BLACK:  Make your move.");
        } else {
            currentPlayer = CheckersData.WHITE;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("WHITE has no moves.  BLACK wins.");
            else if (legalMoves[0].isJump())
                message.setText("WHITE:  Make your move.  You must jump.");
            else
                message.setText("WHITE:  Make your move.");
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
        if(event.getAction() == MotionEvent.ACTION_MOVE){
            return true;
        }
        return false;
    }



}
