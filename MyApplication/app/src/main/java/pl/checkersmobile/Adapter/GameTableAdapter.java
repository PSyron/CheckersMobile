package pl.checkersmobile.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import pl.checkersmobile.CheckersData;
import pl.checkersmobile.Enums;
import pl.checkersmobile.R;

public class GameTableAdapter extends BaseAdapter {

    private final int mMobileValues;
    Enums.GridTableType mGridType;
    LayoutInflater inflater;
    int[][] checkers;
    private Context context;
    private ArrayList<Integer> legalPositions;

    public GameTableAdapter(Context context, Enums.GridTableType inGridType, int[][] inCheckers) {

        this.context = context;
        mGridType = inGridType;
        checkers = inCheckers;
        mMobileValues = mGridType == Enums.GridTableType.GameTable ? 64 : 12;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public GameTableAdapter(Context context, Enums.GridTableType inGridType, int[][] inCheckers, ArrayList<Integer> inLegalPositions) {

        this(context, inGridType, inCheckers);
        legalPositions = inLegalPositions;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, null);
            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.item_gridview_piece);

            switch (mGridType) {
                case GameTable: {
                    imageView = SetView(imageView, position);
                    break;
                }
                case PlayerOne: {
                    imageView.setImageResource(R.drawable.piece_black);
                    break;
                }
                case PlayerTwo: {
                    imageView.setImageResource(R.drawable.piece_white);
                    break;
                }

            }
        }

        return convertView;
    }

    private ImageView SetView(ImageView inImageView, int inPosition) {
        //background black or white depending of the position

        int col = inPosition / 8 % 2;
        if (col == 0) {
            if (inPosition % 2 == 0) {
                inImageView.setBackgroundColor(Color.WHITE);
            } else {
                inImageView.setBackgroundColor(Color.BLACK);
            }
        } else {
            if (inPosition % 2 == 0) {
                inImageView.setBackgroundColor(Color.BLACK);
            } else {
                inImageView.setBackgroundColor(Color.WHITE);
            }
        }

     /*   for (int i = 0; i < checkers.length; i++) {
            for (int j = 0; j < checkers[i].length; j++) {*/
        if (legalPositions != null && legalPositions.size() > 0) {
            for (int legalMove : legalPositions) {
                if (legalMove == inPosition)
                    inImageView.setBackgroundColor(ContextCompat.getColor(context, R.color.availableMove));
            }
        }

        if (valueInPosition(inPosition) == CheckersData.WHITE) {
            inImageView.setImageResource(R.drawable.piece_white);
        } else if (valueInPosition(inPosition) == CheckersData.BLACK) {
            inImageView.setImageResource(R.drawable.piece_black);
        }

        return inImageView;
    }

    public int getWhiteCount() {
        int count = 0;
        for (int i = 0; i < checkers.length; i++) {
            for (int j = 0; j < checkers[i].length; j++) {
                if (checkers[i][j] == CheckersData.WHITE || checkers[i][j] == CheckersData.WHITE_KING) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getBlackCount() {
        int count = 0;
        for (int i = 0; i < checkers.length; i++) {
            for (int j = 0; j < checkers[i].length; j++) {
                if (checkers[i][j] == CheckersData.BLACK || checkers[i][j] == CheckersData.BLACK_KING) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public int getCount() {
        return mMobileValues;
    }

    private int valueInPosition(int inPosition) {
        return checkers[inPosition / 8][inPosition % 8];
    }

    @Override
    public Integer getItem(int position) {
        return valueInPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}