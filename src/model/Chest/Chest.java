package model.Chest;


import model.Inventory.Card;
import model.Inventory.Inventory;
import model.PlayerInfo;
import util.RandomInt;
import util.database.DataModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class Chest extends DataModel {
    private static final int unlockDuration = 10800;
    private static final int minGold = 10;
    private static final int maxGold = 10;
    private static final int minFragment = 10;
    private static final int maxFragment = 20;
    private static final int maxCardSlot = 2;
    private static final int minCardSlot = 1;
    private static final int secondPerCoin = 600;


    private int id;   // send To client
    private String name;
    private byte isOpen;   // send to client
    private byte isReceive;  // send to client
    private int price;
    private LocalDateTime timeline;
    private long timeRemaining;  // send to client
    private int cardSlot;
    private int[] cardID;  // send to client
    private int[] amountOfCard; // send to client
    private int gold;  // send to client

    public Chest(int id){
        super();
        /*
        this.id = id;
        this.name = "Chest_"+this.id;
        this.isOpen = 0;
        this.isReceive = 0;
        this.price = unlockDuration / secondPerCoin ;
        this.timeline = LocalDateTime.now();
        this.timeRemaining = RandomInt.randInt(0,unlockDuration); // Should repair
        int[] tmpCards = RandomInt.randIntsDifference(0,amountIdOfCards,cardSlots); //Should repair
        cards = new int[cardSlots];
        amountOfCards = new int[cardSlots];

        for(int i=0; i < cardSlots; i++) {
            cards[i] = tmpCards[i];
            amountOfCards[i] = RandomInt.randInt(minFragment,maxFragment);
        }

        this.gold = RandomInt.randInt(minGold,maxGold);

         */


        this.id = id;
        this.name = "Chest_"+this.id;
        this.isOpen = 0;
        this.isReceive = 0;
        this.price = unlockDuration / secondPerCoin ;
        this.timeline = LocalDateTime.now();
        this.timeRemaining = unlockDuration;
        this.cardSlot = RandomInt.randInt(minCardSlot,maxCardSlot);
        cardID= Inventory.randCardIdDifference(cardSlot);
        amountOfCard = Inventory.randAmountOfCard(minFragment,maxFragment,cardSlot);
        this.gold = RandomInt.randInt(minGold,maxGold);
    }
    public Chest(int id,Chest tmpChest){
        super();

        this.id = id;
        this.name = "Chest_"+this.id;
        this.isOpen = 0;
        this.isReceive = 0;
        this.price = unlockDuration / secondPerCoin ;
        this.timeline = LocalDateTime.now();
        this.timeRemaining = unlockDuration;
        this.cardSlot = tmpChest.cardSlot; // copy
        cardID= Arrays.copyOfRange(tmpChest.cardID,0, this.cardSlot); //copy
        amountOfCard = Arrays.copyOfRange(tmpChest.amountOfCard,0, this.cardSlot); //copy
        this.gold = tmpChest.gold; //copy
    }
    public boolean open(){
        if (this.isReceive == 1){
            return false;
        }
        this.isOpen = 1;
        this.timeline = LocalDateTime.now();
        this.timeRemaining = unlockDuration;
        return true;
    }
    public boolean receive(PlayerInfo player){
        if (this.isReceive == 1){
            return false;
        }
        this.isOpen = 1;
        this.isReceive = 1;
        this.timeRemaining = 0;
        player.upGold(this.gold);
        for(int i=0;i<cardSlot;i++){
            Card card = player.getInventory().getCard(cardID[i]);
            card.upExp(this.amountOfCard[i]);
        }
        // TODO something
        return true;
    }
    public long updateTimeRemaining(){
        if (this.isOpen == 0){
            return 0;
        }
        LocalDateTime curTime = LocalDateTime.now();
        this.timeRemaining -=  Duration.between(this.timeline, curTime).getSeconds();
        this.timeline = curTime;
        if (this.timeRemaining <= 0) {
            this.timeRemaining = 0;
        }
        return this.timeRemaining;
    }

    public int getTimeRemaining() {
        this.updateTimeRemaining();
        return (int) this.timeRemaining;
    }

    public int getId() {
        return id;
    }

    public byte getIsOpen() {
        return isOpen;
    }

    public byte getIsReceive() {
        return isReceive;
    }

    public int[] getAmountOfCard() {
        return amountOfCard;
    }

    public int[] getCardID() {
        return cardID;
    }

    public int getGold() {
        return gold;
    }
}


