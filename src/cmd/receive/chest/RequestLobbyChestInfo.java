package cmd.receive.chest;

import bitzero.server.extensions.data.BaseCmd;
import bitzero.server.extensions.data.DataCmd;


public class RequestLobbyChestInfo extends BaseCmd {
    public RequestLobbyChestInfo(DataCmd dataCmd) {
        super(dataCmd);
        unpackData();
    }
}