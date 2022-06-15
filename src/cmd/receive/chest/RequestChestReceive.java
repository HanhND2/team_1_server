package cmd.receive.chest;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;
import bitzero.util.common.business.CommonHandle;
import cmd.obj.demo.DemoDirection;

import java.nio.ByteBuffer;


public class RequestChestReceive extends BaseCmd {
    private int id;
    public RequestChestReceive(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }
    @Override
    public void unpackData() {
        ByteBuffer bf = makeBuffer();
        try {
            id = readInt(bf);
        } catch (Exception e) {
            id = DemoDirection.UP.getValue();
            CommonHandle.writeErrLog(e);
        }
    }

    public int getID() {
        return id;
    }
}