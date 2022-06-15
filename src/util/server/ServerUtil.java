package util.server;

public class ServerUtil {
    public static String SQL_DATABASE = "Portal";

    ServerUtil() {
        super();
    }

    public static String getModelKeyName(String model, String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConstant.GAME_DATA_KEY_PREFIX);
        sb.append(ServerConstant.SEPERATOR);
        sb.append(key);
        sb.append(ServerConstant.SEPERATOR);
        sb.append(model);

        return sb.toString();
    }

    public static String getModelKeyName(String model, int userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConstant.GAME_DATA_KEY_PREFIX);
        sb.append(ServerConstant.SEPERATOR);
        sb.append(userId);
        sb.append(ServerConstant.SEPERATOR);
        sb.append(model);

        return sb.toString();
    }

    public static String getModelKeyName(String model, long userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConstant.GAME_DATA_KEY_PREFIX);
        sb.append(ServerConstant.SEPERATOR);
        sb.append(userId);
        sb.append(ServerConstant.SEPERATOR);
        sb.append(model);

        return sb.toString();
    }

    public static String getSocialModelKeyName(String model, long userId) {
        StringBuilder sb = new StringBuilder();
        sb.append(ServerConstant.GAME_DATA_KEY_PREFIX);
        sb.append(ServerConstant.SEPERATOR);
        sb.append(userId);
        sb.append(ServerConstant.SEPERATOR);
        sb.append(model);

        return sb.toString();
    }
}
