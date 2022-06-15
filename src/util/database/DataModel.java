package util.database;

import com.google.gson.Gson;
import net.spy.memcached.CASResponse;
import net.spy.memcached.CASValue;
import util.server.ServerUtil;

public class DataModel {
    static final Gson gson = new Gson();

    public DataModel() {
        super();
    }

    public void saveModel(String key) throws Exception {
        String globalKey = ServerUtil.getModelKeyName(this.getClass().getSimpleName(), key);
        String sobj = gson.toJson(this);
        DataHandler.set(globalKey, sobj);
    }

    public void saveModel(int uId) throws Exception {
        String key = ServerUtil.getModelKeyName(this.getClass().getSimpleName(), uId);
        String sobj = gson.toJson(this);
        DataHandler.set(key, sobj);
    }

    public void saveModel(long uId) throws Exception {
        String key = ServerUtil.getModelKeyName(this.getClass().getSimpleName(), uId);
        String sobj = gson.toJson(this);
        DataHandler.set(key, sobj);
    }

    public static Object getModel(String key, Class c) throws Exception {
        String globalKey = ServerUtil.getModelKeyName(c.getSimpleName(), key);
        return gson.fromJson((String) DataHandler.get(globalKey), c);
    }

    public static Object getModel(int uId, Class c) throws Exception {
        String key = ServerUtil.getModelKeyName(c.getSimpleName(), uId);
        return gson.fromJson((String) DataHandler.get(key), c);
    }

    public static Object getModel(long uId, Class c) throws Exception {
        String key = ServerUtil.getModelKeyName(c.getSimpleName(), uId);
        return gson.fromJson((String) DataHandler.get(key), c);
    }

    public static Object getSocialModel(long uId, Class c) throws Exception {
        String key = ServerUtil.getSocialModelKeyName(c.getSimpleName(), uId);
        return DataHandler.get(key);
    }

    public void saveSocialModel(long uId) throws Exception {
        String key = ServerUtil.getSocialModelKeyName(this.getClass().getSimpleName(), uId);
        DataHandler.set(key, this);
    }

    public static CASValue getS(int uId, Class c) throws Exception {
        String key = ServerUtil.getModelKeyName(c.getSimpleName(), uId);
        return DataHandler.getS(key);
    }

    public boolean checkAndSet(int uId, long valCAS) throws Exception {
        String key = ServerUtil.getModelKeyName(this.getClass().getSimpleName(), uId);
        CASResponse casRes = DataHandler.checkAndSet(key, valCAS, this);
        if (casRes.equals(CASResponse.OK))
            return true;
        else
            return false;
    }

    public String toJson() {
        return gson.toJson(this);
    }
}
