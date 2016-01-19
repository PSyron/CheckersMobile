package pl.checkersmobile.model;

import pl.checkersmobile.CheckersMove;

/**
 * Created by paulc_000 on 2016-01-19.
 */
public class Move {
    private int preRow;

    private int preColumn;

    private int idGame;

    private int postRow;

    private int postColumn;

    private int idMove;


    public Move(int idGame, int postColumn, int postRow, int preRow, int preColumn, int idMove) {
        this.idGame = idGame;
        this.postColumn = postColumn;
        this.postRow = postRow;
        this.preRow = preRow;
        this.preColumn = preColumn;
        this.idMove = idMove;
    }

    public int getIdMove() {
        return idMove;
    }

    public void setIdMove(int idMove) {
        this.idMove = idMove;
    }

    public int getPreRow() {
        return preRow;
    }

    public void setPreRow(int preRow) {
        this.preRow = preRow;
    }

    public int getPreColumn() {
        return preColumn;
    }

    public void setPreColumn(int preColumn) {
        this.preColumn = preColumn;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getPostRow() {
        return postRow;
    }

    public void setPostRow(int postRow) {
        this.postRow = postRow;
    }

    public int getPostColumn() {
        return postColumn;
    }

    public void setPostColumn(int postColumn) {
        this.postColumn = postColumn;
    }


    public CheckersMove getCheckerMove() {
        return new CheckersMove(preRow, preColumn, postRow, postColumn);
    }
}