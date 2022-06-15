package cmd.receive.authen;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;

import java.nio.ByteBuffer;

public class RequestLogout extends BaseCmd {
    private String username = "";
    private int userId = 0;

    public RequestLogout(DataCmd dataCmd) {
        super(dataCmd);
    }

    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        //username = readString(bf);
        userId = readInt(bf);
    }

    public int getUserId(){
        return this.userId;
    }

    public String getUsername(){
        return username;
    }


}
