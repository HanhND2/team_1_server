package model.Inventory;

import model.PlayerInfo;
import util.RandomInt;
import util.database.DataModel;

public class Card extends DataModel {
    private static final int[] achievedLevelUpExp = {0,5,10,20,50,100,200,300,400,500};
    private static final int[] achievedLevelUpGold = {0,5,10,20,50,100,200,300,400,500};

    private int id;   // send To client
    private String name;
    private int type;
    private int grade;
    private int level;
    private int exp;


    public Card(int id){
        super();
        this.id = id;
        this.name = "Card_"+this.id;
        this.type = RandomInt.randInt(1,3);
        this.grade = RandomInt.randInt(1,10);
        this.level = RandomInt.randInt(1,10);
        this.exp = RandomInt.randInt(1,10);
    }

    public void upExp(int exp){
        this.exp += exp;
    }
    public void upLevel(PlayerInfo playerInfo){
        this.level += 1;
        //Todo something
    }
    public int getLevelUpExp(int level){
        return achievedLevelUpExp[level-1];
    }
    public int getLevelUpGold(int level){
        return achievedLevelUpGold[level-1];
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }
}
