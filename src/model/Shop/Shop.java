package model.Shop;

import model.PlayerInfo;
import util.database.DataModel;

import java.time.LocalDate;

public class Shop extends DataModel {
    private static LoginItem commonLoginItem;
    private static LocalDate curDate = LocalDate.MIN;
    private LoginItem loginItem;
    private GoldBuying goldBuying;

    public Shop(PlayerInfo playerInfo){
        super();
        this.updateCommonLoginItem();
        loginItem = new LoginItem(playerInfo,commonLoginItem);
        goldBuying = new GoldBuying();
    }
    public void updateCommonLoginItem(){
        LocalDate today = LocalDate.now();
        if (!curDate.equals(today)) {
            curDate = today;
            commonLoginItem = new LoginItem();
        }
    }
    public LoginItem getLoginItem() {
        return loginItem;
    }
    public GoldBuying getGoldBuying() {
        return goldBuying;
    }

}
