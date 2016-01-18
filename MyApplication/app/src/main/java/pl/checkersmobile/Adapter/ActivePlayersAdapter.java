package pl.checkersmobile.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.checkersmobile.R;
import pl.checkersmobile.interfaces.InviteInterface;
import pl.checkersmobile.model.User;


public class ActivePlayersAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    Context mContext;
    InviteInterface mCallback;
    private List<User> players = new ArrayList<>();

    public ActivePlayersAdapter(Context ctx, InviteInterface callback, List<User> inModel) {
        mContext = ctx;
        mCallback = callback;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        players = inModel;
    }

    @Override
    public User getItem(int position) {

        return players.get(position);
    }

    @Override
    public int getCount() {

        return players.size();
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
        holder.accept = (ImageView) rowView.findViewById(R.id.item_invitations_ibAccept);
        holder.refuse = (ImageView) rowView.findViewById(R.id.item_invitations_ibDecline);
        holder.refuse.setVisibility(View.GONE);
        holder.name.setText(players.get(position).getName());
        holder.time.setVisibility(View.INVISIBLE);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onSendInvitationClicked(players.get(position));
            }
        });
        return rowView;
    }

    public class Holder {
        TextView name;
        TextView time;
        ImageView accept;
        ImageView refuse;
    }
}
