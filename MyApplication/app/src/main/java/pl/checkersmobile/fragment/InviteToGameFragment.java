package pl.checkersmobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.Bind;
import pl.checkersmobile.R;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public class InviteToGameFragment extends Fragment {

    @Bind(R.id.activity_invitations_list)
    ListView mPlayerList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_invitations,
                container, false);
        return view;

    }
}
