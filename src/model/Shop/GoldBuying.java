package model.Shop;

import model.PlayerInfo;
import util.database.DataModel;

public class GoldBuying extends DataModel {
    private static final int[] amountOfGolds = {1000,2000,10000};
    private static final int[] priceOfGolds = {50,95,475};


    public GoldBuying() {
        super();
    }

    public void buyGold(PlayerInfo playerInfo,int type){
        playerInfo.upGold(amountOfGolds[type]);
        playerInfo.upG(priceOfGolds[type]);
    }


}
