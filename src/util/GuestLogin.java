package util;

import bitzero.util.common.business.Debug;
import bitzero.util.socialcontroller.bean.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.database.DataHandler;
import util.server.ServerConstant;

import java.util.concurrent.atomic.AtomicInteger;

public class GuestLogin {
    private static final AtomicInteger guestCount = new AtomicInteger(1);

    public static UserInfo newGuest() {
        int userId = 0;
        userId = guestCount.incrementAndGet();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Integer.toString(userId));
        userInfo.setUsername("Fresher" + userId);
        userInfo.setHeadurl("");

        return userInfo;
    }

    public static UserInfo setInfo(int userId, String username) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(Integer.toString(userId));
        userInfo.setUsername(username);
        userInfo.setHeadurl("");

        return userInfo;
    }


    public static void saveData() {
        try {
            Integer currCount = Integer.valueOf(guestCount.get());
            DataHandler.set(ServerConstant.FARM_ID_KEY, currCount);
        } catch (Exception e) {
            Debug.trace("Error saving guest id count");
        }
    }

}
