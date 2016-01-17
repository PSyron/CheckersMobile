package pl.checkersmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.checkersmobile.R;
import pl.checkersmobile.model.Invite;

/**
 * Created by Tomek on 03.12.2015.
 */
public class InvitationsAdapter extends BaseAdapter {

/*
    @Bind(R.id.item_invitations_ibAccept)
    ImageView ibAccept;
    @Bind(R.id.item_invitations_ibDecline)
    ImageView ibDecline;
    @Bind(R.id.item_invitations_tvName)
    TextView tvName;
    @Bind(R.id.item_invitations_tvTime)
    TextView tvTime;

*/

    private static LayoutInflater inflater = null;
    Context mContext;
    //TODO sprecyzowanie modelu
    private List<Invite> invites = new ArrayList<>();

    public InvitationsAdapter(Context ctx, List<Invite> inModel) {
        mContext = ctx;
        inflater  = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        invites = inModel;
    }

    @Override
    public Invite getItem(int position) {

        return invites.get(position);
    }

    @Override
    public int getCount() {

        return invites.size();
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

        holder.name = (TextView) rowView.findViewById(R.id.item_invitations_tvName);
        holder.time = (TextView) rowView.findViewById(R.id.item_invitations_tvTime);

        holder.name.setText(invites.get(position).getPlayerName());
        holder.time.setText("Nr stołu " + invites.get(position).getIdGame());

        return rowView;
    }

    public class Holder {
        TextView name;
        TextView time;
    }
}
