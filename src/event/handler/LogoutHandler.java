package event.handler;

import bitzero.server.core.BZEventParam;
import bitzero.server.core.IBZEvent;
import bitzero.server.entities.User;
import bitzero.server.extensions.BaseServerEventHandler;

public class LogoutHandler extends BaseServerEventHandler {


    public LogoutHandler() {
        super();
    }

    public void handleServerEvent(IBZEvent iBZEvent) {
        this.onLogOut((User) iBZEvent.getParameter(BZEventParam.USER));
    }

    private void onLogOut(User user) {
        // process when user logout
    }


}
