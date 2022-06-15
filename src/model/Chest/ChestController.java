package model.Chest;

import model.PlayerInfo;
import util.database.DataModel;

import java.util.Hashtable;
import java.util.Set;

public class ChestController extends DataModel {
    private Hashtable<Integer,Chest> chests;
    private Hashtable<Integer,Chest> lobbyChests;
    private Chest shopChest;
    private int lastChestID ;

    public ChestController(){
        super();
        this.lastChestID = 0;
        this.chests = new Hashtable<Integer, Chest>();
        this.lobbyChests = new Hashtable<Integer, Chest>();
        this.shopChest = null;

    }
    public int createChest(){
        this.lastChestID +=1;
        Chest newChest = new Chest(this.lastChestID);
        this.chests.put(this.lastChestID,newChest);
        this.lobbyChests.put(this.lastChestID,newChest);
        return this.lastChestID;
    }
    public int createChest(Chest tmpChest){
        this.lastChestID +=1;
        Chest newChest = new Chest(this.lastChestID,tmpChest);
        shopChest = newChest;
        this.chests.put(this.lastChestID,newChest);
        return this.lastChestID;
    }
    public Chest getChest(int id){
        return this.chests.get(id);
    }
    public int getAmountOfLobbyChest(){
        return lobbyChests.size();
    }
    public void openChest(int id){
        Chest openedChest = this.chests.get(id);
        if (openedChest == null){
            return;
        }
        openedChest.open();
    }
    public void receiveChest(PlayerInfo playerInfo,int id){
        Chest receivedChest = this.chests.get(id);
        if (receivedChest == null){
            return;
        }
        receivedChest.receive(playerInfo);
        this.chests.remove(id);
        if (this.lobbyChests.get(id) != null){
            this.lobbyChests.remove(id);
        }
    }
    public Set<Integer> getLobbyChests(){
        Set<Integer> idChestList;
        idChestList = this.lobbyChests.keySet();
        return  idChestList;
    }
    public void updateTimeRemainingOfLobbyChest(){
        Set<Integer> idChestList;
        idChestList = this.getLobbyChests();
        for(int id:idChestList){
            Chest chest = this.getChest(id);
            chest.updateTimeRemaining();
        }
    }

}
