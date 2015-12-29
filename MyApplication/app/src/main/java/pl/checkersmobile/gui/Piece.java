package pl.checkersmobile.gui;

import pl.checkersmobile.Enums;

/**
 * Created by Tomek on 29.12.2015.
 */
public class Piece {

    public int positionX;
    public int positionY;
    public int position;
    public Enums.PieceColor pieceColor;

    public Piece(int x, int y, Enums.PieceColor color)
    {
        positionX = x;
        positionY = y;
        position = calculatePosition(positionX, positionY);
        pieceColor = color;
    }

    public int calculatePosition(int x, int y)
    {
        return (x*8 + y);
    }
}
