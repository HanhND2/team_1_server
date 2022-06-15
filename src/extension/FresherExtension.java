package extension;


import bitzero.engine.sessions.ISession;
import bitzero.server.config.ConfigHandle;
import bitzero.server.core.BZEventType;
import bitzero.server.entities.User;
import bitzero.server.entities.managers.ConnectionStats;
import bitzero.server.extensions.BZExtension;
import bitzero.server.extensions.data.DataCmd;
import bitzero.util.ExtensionUtility;
import bitzero.util.common.business.Debug;
import bitzero.util.datacontroller.business.DataController;
import bitzero.util.socialcontroller.bean.UserInfo;
import cmd.receive.authen.RequestLogin;
import event.handler.LoginSuccessHandler;
import event.handler.LogoutHandler;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import service.ChestHandler;
import service.DemoHandler;
import service.UserHandler;
import util.GuestLogin;
import util.server.ServerConstant;
import util.server.ServerLoop;


public class FresherExtension extends BZExtension {
    private static String SERVERS_INFO =
            ConfigHandle.instance().get("servers_key") == null ? "servers" : ConfigHandle.instance().get("servers_key");

    private ServerLoop svrLoop;

    public FresherExtension() {
        super();
        setName("Fresher");
        svrLoop = new ServerLoop();
    }

    public void init() {

        /**
         * register new handler to catch client's packet
         */
        trace("  Register Handler ");
        addRequestHandler(UserHandler.USER_MULTI_IDS, UserHandler.class);
        addRequestHandler(DemoHandler.DEMO_MULTI_IDS, DemoHandler.class);
        addRequestHandler(ChestHandler.CHEST_MULTI_IDS, ChestHandler.class);

        /**
         * register new event
         */
        trace(" Event Handler ");
        addEventHandler(BZEventType.USER_LOGIN, LoginSuccessHandler.class);
        addEventHandler(BZEventType.USER_LOGOUT, LogoutHandler.class);
        addEventHandler(BZEventType.USER_DISCONNECT, LogoutHandler.class);
    }

    public ServerLoop getServerLoop() {
        return svrLoop;
    }

    @Override
    public void monitor() {
        try {
            ConnectionStats connStats = bz.getStatsManager().getUserStats();
            JSONObject data = new JSONObject();

            data.put("totalInPacket", bz.getStatsManager().getTotalInPackets());
            data.put("totalOutPacket", bz.getStatsManager().getTotalOutPackets());
            data.put("totalInBytes", bz.getStatsManager().getTotalInBytes());
            data.put("totalOutBytes", bz.getStatsManager().getTotalOutBytes());

            data.put("connectionCount", connStats.getSocketCount());
            data.put("totalUserCount", bz.getUserManager().getUserCount());

            DataController.getController().setCache(SERVERS_INFO, 60 * 5, data.toString());
        } catch (Exception e) {
            trace("Ex monitor");
        }
    }

    /**
     * @param cmdId
     * @param session
     * @param objData the first packet send from client after handshake success will dispatch to doLogin() function
     */
    public void doLogin(short cmdId, ISession session, DataCmd objData) {
        RequestLogin reqGet = new RequestLogin(objData);
        reqGet.unpackData();

        try {

            UserInfo uInfo = getUserInfo(reqGet.getUsername(), reqGet.getUserId());
            User u = ExtensionUtility.instance().canLogin(uInfo, "", session);
            if (u != null)
                u.setProperty("userId", uInfo.getUserId());
        } catch (Exception e) {
            Debug.warn("DO LOGIN EXCEPTION " + e.getMessage());
            Debug.warn(ExceptionUtils.getStackTrace(e));
        }

    }

    private UserInfo getUserInfo(String username, int userId) throws Exception {
        int customLogin = ServerConstant.CUSTOM_LOGIN;
        switch (customLogin) {
            case 1: // login zingme
                return ExtensionUtility.getUserInfoFormPortal(username);
            case 2: // set direct userid
                return GuestLogin.setInfo(userId, "Fresher_" + userId);
            default: // auto increment
                return GuestLogin.newGuest();
        }
    }

}
