package model.Shop;

import model.Chest.Chest;
import model.Inventory.Inventory;
import model.PlayerInfo;
import util.RandomInt;
import util.database.DataModel;

import java.util.Arrays;

public class LoginItem extends DataModel {
    private static final int minFragment = 50;
    private static final int maxFragment = 100;
    private static final int step = 10;
    private static final int goldPerCard = 10;
    private static final int goldPerChest = 1000;
    private static final int maxCardSlot = 3;
    private static final int minCardSlot = 1;

    private int chestID; // send to client
    private Chest chest;
    private int priceOfChest;
    private byte isBoughtChest;
    private int cardSlot ;
    private int[] cardID;  // send to client
    private int[] amountOfCards; // send to client
    private int[] priceOfCards;
    private byte[] isBoughtCards;

    public LoginItem(PlayerInfo playerInfo, LoginItem tmpLoginItem){
        super();
        this.chestID = playerInfo.getChestController().createChest(tmpLoginItem.chest);
        this.chest = playerInfo.getChestController().getChest(this.chestID);
        this.priceOfChest = goldPerChest;
        this.isBoughtChest = 0;

        this.cardSlot = tmpLoginItem.cardSlot; //copy
        this.cardID = Arrays.copyOfRange(tmpLoginItem.cardID,0, this.cardSlot); // Copy
        this.amountOfCards = Arrays.copyOfRange(tmpLoginItem.amountOfCards,0,this.cardSlot); //copy
        this.priceOfCards = Arrays.copyOfRange(tmpLoginItem.priceOfCards,0,this.cardSlot); //copy
        this.isBoughtCards = Arrays.copyOfRange(tmpLoginItem.isBoughtCards,0,this.cardSlot); //copy

    }
    public LoginItem(){
        super();
        this.chestID = 0;
        this.chest = new Chest(this.chestID);
        this.priceOfChest = goldPerChest;
        this.isBoughtChest = 0;
        this.cardSlot = RandomInt.randInt(minCardSlot,maxCardSlot);
        this.cardID= Inventory.randCardIdDifference(cardSlot);
        this.amountOfCards = Inventory.randAmountOfCard(minFragment/step,maxFragment/step,cardSlot);
        this.priceOfCards = new int[cardSlot];
        this.isBoughtCards = new byte[cardSlot];
        for(int i=0;i<cardSlot;i++){
            this.amountOfCards[i]*=10;
            this.priceOfCards[i]=this.amountOfCards[i]*goldPerCard;
            this.isBoughtCards[i]=0;
        }
    }
    public void boughtChest(PlayerInfo playerInfo) {
        this.isBoughtChest = 1;
        playerInfo.upGold(this.priceOfChest * (-1));
        playerInfo.getChestController().getChest(this.chestID).receive(playerInfo);
    }
    public void boughtCard(PlayerInfo playerInfo, int id){
        int index = -1;
        for(int i=0;i<cardSlot;i++){
            if (this.cardID[i] == id) {
                index = i;
            }
        }
        if (index == -1) {
            return;
        }
        playerInfo.upGold(this.priceOfCards[index] * (-1));
        playerInfo.getInventory().getCard(this.cardID[index]).upExp(this.amountOfCards[index]);
    }



}
