package pl.checkersmobile;

/**
 * Created by paulc_000 on 2015-11-24.
 */
public class Constants {
    public static final String SERVICES_DOMAIN = "http://156.17.130.217/Pz/Services/";

    public static final String LOGIN_CONNECT = "Login.svc/connect/"; //+session
    public static final String LOGIN_DISCONNECT = "Login.svc/disconnect/"; //+session
    public static final String LOGIN_LOGIN = "Login.svc/login/"; //+session
    public static final String LOGIN_LOGOUT = "Login.svc/logoff/"; //+session
    public static final String LOGIN_REGISTER = "Login.svc/register/"; //+<jakasNazwa>/<jakisLogin>/<jakiesHaslo>

    public static final String COMMUNITY_CHECK_ACTIVE_PLAYERS = "Community.svc/checkActivePlayers/";//{sessionToken}
    public static final String COMMUNITY_CHECK_ACTIVE_FRIENDS = "Community.svc/checkActiveFriends/";//{sessionToken}
    public static final String COMMUNITY_GET_FRIENDS = "Community.svc/getFriends/"; //{sessionToken}
    public static final String COMMUNITY_ADD_FRIEND = "Community.svc/addFriend/"; // {sessionToken}/{friendName}
    public static final String COMMUNITY_REMOVE_FRIEND = "Community.svc/removeFriend/"; // {sessionToken}/{friendName}


    public static final String TABLE_GET_INVITATIONS = "Table.svc/getInvitations/"; //{sessionToken}
    public static final String TABLE_CREATE = "Table.svc/createTable/";
    public static final String TABLE_GET_ENEMY = "Table.svc/xxx/"; //TODO
    public static final String TABLE_REFUSE_INVITATION = "Table.svc/refuseInvitation/";
    public static final String TABLE_ACCEPT_INVITATION = "Table.svc/acceptInvite/";
    public static final String TABLE_SEND_INVITATION = "Table.svc/invitePlayer/"; // sesja, friendname, game id

    public static final String GAME_GET_LAST_MOVES = "Game.svc/getLastMoves/";
    public static final String GAME_FINISH_MOVE = "Game.svc/finishMove/";
}
