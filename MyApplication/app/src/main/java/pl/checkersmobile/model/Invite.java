package pl.checkersmobile.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by paulc_000 on 2016-01-17.
 */
public class Invite implements Serializable {
    @SerializedName("idGame")
    private String idGame;
    @SerializedName("playerName")
    private String playerName;
    @SerializedName("dateString")
    private String dateString;

    public Invite(String dateString, String idGame, String playerName) {
        this.dateString = dateString;
        this.idGame = idGame;
        this.playerName = playerName;
    }

    public String getIdGame() {
        return idGame;
    }

    public void setIdGame(String idGame) {
        this.idGame = idGame;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    @Override
    public String toString() {
        return "ClassPojo [idGame = " + idGame + ", playerName = " + playerName + ", dateString = " + dateString + "]";
    }


}
