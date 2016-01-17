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
}
