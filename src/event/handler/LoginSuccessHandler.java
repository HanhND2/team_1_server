package event.handler;

import bitzero.server.core.BZEvent;
import bitzero.server.core.BZEventParam;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseServerEventHandler;
import bitzero.server.extensions.ExtensionLogLevel;
import bitzero.util.ExtensionUtility;
import event.eventType.DemoEventParam;
import event.eventType.DemoEventType;
import model.PlayerInfo;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.server.ServerConstant;

import java.util.HashMap;
import java.util.Map;

public class LoginSuccessHandler extends BaseServerEventHandler {

    private static final Logger logger = LoggerFactory.getLogger("Login");
    public LoginSuccessHandler() {
        super();
    }

    public void handleServerEvent(IBZEvent iBZEvent) {
        this.onLoginSuccess((User) iBZEvent.getParameter(BZEventParam.USER));
    }

    /**
     * @param user description: after login successful to server, core framework will dispatch this event
     */
    private void onLoginSuccess(User user) {
        trace(ExtensionLogLevel.DEBUG, "On Login Success ", user.getName());
        PlayerInfo pInfo = null;
        try {
            pInfo = (PlayerInfo) PlayerInfo.getModel(user.getId(), PlayerInfo.class);
            pInfo.hanh = "Hanh";

            if (pInfo != null)
                System.out.println(pInfo.toJson());
        } catch (Exception e) {
            logger.info(ExceptionUtils.getStackTrace(e));
        }

        if (pInfo == null) {
            //pInfo = new PlayerInfo(user.getId(), "username_" + user.getId());
            pInfo = new PlayerInfo(user.getId(), user.getName()); // Replace By Hanh
            try {
                pInfo.saveModel(user.getId());
                //pInfo.saveModel(user.getName());
            } catch (Exception e) {
                logger.info(ExceptionUtils.getStackTrace(e));
            }
        }

        /**
         * cache playerinfo in RAM
         */
        user.setProperty(ServerConstant.PLAYER_INFO, pInfo);

        /**
         * send login success to client
         * after receive this message, client begin to send game logic packet to server
         */
        ExtensionUtility.instance().sendLoginOK(user);

        /**
         * dispatch event here
         */
        Map evtParams = new HashMap();
        evtParams.put(DemoEventParam.USER, user);
        evtParams.put(DemoEventParam.NAME, user.getName());
        ExtensionUtility.dispatchEvent(new BZEvent(DemoEventType.LOGIN_SUCCESS, evtParams));

    }

}
