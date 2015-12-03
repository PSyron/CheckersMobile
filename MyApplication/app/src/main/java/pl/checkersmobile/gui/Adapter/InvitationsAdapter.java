package pl.checkersmobile.gui.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import pl.checkersmobile.R;

/**
 * Created by Tomek on 03.12.2015.
 */
public class InvitationsAdapter extends BaseAdapter {

    @Bind(R.id.item_invitations_ibAccept)
    ImageView ibAccept;
    @Bind(R.id.item_invitations_ibDecline)
    ImageView ibDecline;
    @Bind(R.id.item_invitations_tvName)
    TextView tvName;
    @Bind(R.id.item_invitations_tvTime)
    TextView tvTime;


    Context mContext;
    private static LayoutInflater inflater = null;
    //TODO sprecyzowanie modelu
    private String[] mModel = null;

    public InvitationsAdapter(Context ctx, String[] inModel) {
        mContext = ctx;
        inflater  = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mModel = inModel;
    }

    public class Holder {
        TextView name;
        TextView time;
    }

    @Override
    public Object getItem(int position) {

        return mModel[position];
    }

    @Override
    public int getCount() {

        return mModel.length;
    }

    @Override
    public long getItemId(int position) {

        return -1;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.item_invitations, null);

        holder.name = tvName;
        holder.time = tvTime;

        holder.name.setText(mModel[position]);
        holder.time.setText(mModel[position]);

        return rowView;
    }
}
