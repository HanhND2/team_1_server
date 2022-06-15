package cmd.send.demo;

import bitzero.server.extensions.data.BaseMsg;
import cmd.CmdDefine;
import model.PlayerInfo;

import java.nio.ByteBuffer;

public class ResponseRequestUserInfo extends BaseMsg {
    PlayerInfo info;

    public ResponseRequestUserInfo(PlayerInfo info) {
        super(CmdDefine.GET_USER_INFO);
        this.info = info;
    }

    @Override
    public byte[] createData() {

        ByteBuffer bf = makeBuffer();
        bf.putInt(info.getID());
        putStr(bf, info.getName());
        bf.putInt(info.getAvatar());
        bf.putInt(info.getTrophy());
        bf.putInt(info.getLevel());
        bf.putInt(info.getExp());
        bf.putInt(info.getGold());
        bf.putInt(info.getG());


        bf.putInt(info.position.x);
        bf.putInt(info.position.y);
        return packBuffer(bf);
    }
}
