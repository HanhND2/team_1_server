package service;

import bitzero.server.BitZeroServer;
import bitzero.server.core.BZEventParam;
import bitzero.server.core.BZEventType;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseClientRequestHandler;
import bitzero.server.extensions.data.DataCmd;
import cmd.CmdDefine;
import cmd.send.demo.ResponseRequestUserInfo;
import event.eventType.DemoEventParam;
import event.eventType.DemoEventType;
import extension.FresherExtension;
import model.PlayerInfo;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.server.ServerConstant;

import java.util.List;

public class UserHandler extends BaseClientRequestHandler {
    public static final short USER_MULTI_IDS = 1000;
    private final Logger logger = LoggerFactory.getLogger("UserHandler");

    public UserHandler() {
        super();
    }

    public void init() {
        getExtension().addEventListener(BZEventType.USER_DISCONNECT, this);
        getExtension().addEventListener(BZEventType.USER_RECONNECTION_SUCCESS, this);

        /**
         *  register new event, so the core will dispatch event type to this class
         */
        getExtension().addEventListener(DemoEventType.CHANGE_NAME, this);
    }

    private FresherExtension getExtension() {
        return (FresherExtension) getParentExtension();
    }

    public void handleServerEvent(IBZEvent ibzevent) {

        if (ibzevent.getType() == BZEventType.USER_DISCONNECT)
            this.userDisconnect((User) ibzevent.getParameter(BZEventParam.USER));
        else if (ibzevent.getType() == DemoEventType.CHANGE_NAME)
            this.userChangeName((User) ibzevent.getParameter(DemoEventParam.USER), (String) ibzevent.getParameter(DemoEventParam.NAME));
    }

    public void handleClientRequest(User user, DataCmd dataCmd) {
        try {
            switch (dataCmd.getId()) {
                case CmdDefine.GET_USER_INFO:
                    getUserInfo(user);
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            logger.warn("USERHANDLER EXCEPTION " + e.getMessage());
            logger.warn(ExceptionUtils.getStackTrace(e));
        }

    }

    private void getUserInfo(User user) {
        try {
            PlayerInfo userInfo = (PlayerInfo) user.getProperty(ServerConstant.PLAYER_INFO);
            if (userInfo == null) {
                userInfo = new PlayerInfo(user.getId(), "username_" + user.getId());
                //userInfo  = new PlayerInfo(user.getId(), user.getName() + user.getId()); // Replace By Hanh
                userInfo.saveModel(user.getId());
            }
            send(new ResponseRequestUserInfo(userInfo), user);
        } catch (Exception e) {
            logger.info(ExceptionUtils.getStackTrace(e));
        }

    }


    private void userDisconnect(User user) {
        // log user disconnect
    }

    private void userChangeName(User user, String name) {
        List<User> allUser = BitZeroServer.getInstance().getUserManager().getAllUsers();
        for (User aUser : allUser) {
            // notify user's change
        }
    }

}
