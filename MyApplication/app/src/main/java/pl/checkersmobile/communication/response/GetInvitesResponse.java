package pl.checkersmobile.communication.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import pl.checkersmobile.model.Invite;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public class GetInvitesResponse {
    @SerializedName("Invites")
    private ArrayList<Invite> Invites;
    @SerializedName("Session")
    private String Session;
    @SerializedName("Successful")
    private boolean Successful;

    public ArrayList<Invite> getInvites() {
        return Invites;
    }

    public String getSession() {
        return Session;
    }

    public boolean isSuccessful() {
        return Successful;
    }
}
