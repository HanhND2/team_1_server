package model.Inventory;


import model.PlayerInfo;
import util.RandomInt;
import util.database.DataModel;

import java.util.Hashtable;
import java.util.Set;

public class Inventory extends DataModel {
    private static final int amountOfChosenCard = 8;
    private static final int amountOfCardCollection = 4;
    private static final int amountOfCards = amountOfChosenCard + amountOfCardCollection;

    private final Hashtable<Integer, Card> cardContainer;
    private final Hashtable<Integer, Card> chosenCardContainer;
    private final Hashtable<Integer, Card> cardCollectionContainer;


    public Inventory(){
        super();

        this.cardContainer = new  Hashtable<Integer, Card>();
        this.chosenCardContainer = new Hashtable<Integer, Card>();
        for(int i = 1; i<= amountOfChosenCard; i++)
        {
            Card newCard = new Card(i);
            this.cardContainer.put(i,newCard);
            this.chosenCardContainer.put(i,newCard);
        }

        this.cardCollectionContainer = new Hashtable<Integer, Card>();
        for(int i = amountOfChosenCard+1; i <= amountOfChosenCard + amountOfCardCollection ; i++)
        {
            Card newCard = new Card(i);
            this.cardContainer.put(i,newCard);
            this.cardCollectionContainer.put(i,newCard);
        }


    }

    public void changeCard(int collectionCardID, int chosenCardID){
        //TODO something
        Card collectionCard = this.cardCollectionContainer.get(collectionCardID);
        Card chosenCard = this.chosenCardContainer.get(chosenCardID);
        this.cardCollectionContainer.remove(collectionCardID);
        this.chosenCardContainer.remove(chosenCardID);
        this.cardCollectionContainer.put(chosenCardID,chosenCard);
        this.chosenCardContainer.put(collectionCardID,collectionCard);

    }
    public void upExpCard(int id,int exp){
        Card upCard = this.cardContainer.get(id);
        if (upCard == null) {
            return;
        }
        upCard.upExp(exp);
    }
    public void upLevelCard(PlayerInfo playerInfo,int id){
        Card upCard = this.cardContainer.get(id);
        if (upCard == null) {
            return;
        }
        upCard.upLevel(playerInfo);
    }

    public Card getCard(int id){
        return this.cardContainer.get(id);
    }
    public int getAmountOfChosenCards() {
        return amountOfChosenCard;
    }
    public Set<Integer> getChosenCardList(){
        Set<Integer> idChestList;
        idChestList = this.chosenCardContainer.keySet();
        return  idChestList;
    }
    public int getAmountOfCardCollections() {
        return amountOfCardCollection;
    }
    public Set<Integer> getCardCollectionList(){
        Set<Integer> idChestList;
        idChestList = this.cardCollectionContainer.keySet();
        return  idChestList;
    }
    public static int[] randCardIdDifference(int cardSlot){
        return RandomInt.randIntsDifference(1,amountOfCards,cardSlot);
    }
    public static int[] randAmountOfCard(int minFragment, int maxFragment, int cardSlot){
        int[] amountOfCard = new int[cardSlot];

        for(int i=0; i < cardSlot; i++) {
            amountOfCard[i] = RandomInt.randInt(minFragment,maxFragment);
        }
        return amountOfCard;
    }
}
