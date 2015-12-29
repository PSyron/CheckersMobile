package pl.checkersmobile.gui.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.security.Policy;
import java.util.ArrayList;

import pl.checkersmobile.Enums;
import pl.checkersmobile.R;
import pl.checkersmobile.gui.Piece;

public class GameTableAdapter extends BaseAdapter {

    private Context context;
    private final int mMobileValues;
    Enums.GridTableType mGridType;
    LayoutInflater inflater;
    ArrayList<Piece> checkers;

    public GameTableAdapter(Context context, Enums.GridTableType inGridType, ArrayList<Piece> inCheckers) {

        this.context = context;
        mGridType = inGridType;
        checkers = inCheckers;
        mMobileValues = mGridType == Enums.GridTableType.GameTable ? 64 : 12;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.item_gridview, null);
            ImageView imageView = (ImageView)convertView
                    .findViewById(R.id.item_gridview_piece);

            switch (mGridType)
            {
                case GameTable:
                {
                    imageView = SetView(imageView, position);
                    break;
                }
                case PlayerOne:
                {
                    imageView.setImageResource(R.drawable.piece_black);
                    break;
                }
                case PlayerTwo:
                {
                    imageView.setImageResource(R.drawable.piece_white);
                    break;
                }

            }
        }

        return convertView;
    }

    private  ImageView SetView(ImageView inImageView, int inPosition)
    {
        //background black or white depending of the position
        int col = inPosition/8 %2;
        if (col == 0)
        {
            if (inPosition%2 == 0)
            {
                inImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.bng_light));
            }
            else {
                inImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.bng_dark));
            }
        }
        else
        {
            if (inPosition%2 == 0) {
                inImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.bng_dark));
            }
            else {
                inImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.bng_light));
            }
        }

        for (Piece checker : checkers)
        {
            if(checker.position == inPosition)
            {
                if(checker.pieceColor == Enums.PieceColor.Black)
                    inImageView.setImageResource(R.drawable.piece_black);
                else
                    inImageView.setImageResource(R.drawable.piece_white);
            }
        }


        return inImageView;
    }

    @Override
    public int getCount() {
        return mMobileValues;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}