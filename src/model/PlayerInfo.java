package model;

import cmd.obj.demo.DemoDirection;
import cmd.obj.demo.MaxPosition;
import model.Chest.ChestController;
import model.Inventory.Inventory;
import model.Shop.Shop;
import util.RandomInt;
import util.database.DataModel;

import java.awt.*;

public class PlayerInfo extends DataModel {
    // Zing me
    /*
    - avatar
- name
- trophy
- level
- exp
- gold
- g
- inventory
- chests
     */
    private int id;
    private String name;
    private int avatar;
    private int trophy;
    private int level;
    private int exp;
    private int gold;
    private int g;

    transient public String hanh;
    private Inventory inventory;
    private ChestController chestController;
    private Shop shop;


    public Point position;


    public PlayerInfo(int id, String name) {
        super();
        this.id = id;
        this.name = name;
        this.avatar = RandomInt.randInt(1,6);
        this.trophy = RandomInt.randInt(0,100);
        this.level = RandomInt.randInt(0,10);
        this.exp = RandomInt.randInt(0,100);
        this.gold = RandomInt.randInt(0,10000);
        this.g = RandomInt.randInt(0,1000);

        this.inventory = new Inventory();

        int amountOfChests = RandomInt.randInt(0,4);
        this.chestController= new ChestController();
        for(int i=0;i<amountOfChests;i++)
            this.chestController.createChest();

        this.shop = new Shop(this);
        position = new Point(0, 0);

    }

    public String toString() {
        return String.format("%s|%s", id, name);
    }

    public Point move(short direction) {
        if (direction == DemoDirection.UP.getValue()) {
            position.x++;
        } else if (direction == DemoDirection.DOWN.getValue()) {
            position.x--;
        } else if (direction == DemoDirection.RIGHT.getValue()) {
            position.y++;
        } else {
            position.y--;
        }

        position.x = position.x % MaxPosition.X;
        position.y = position.y % MaxPosition.Y;

        return position;
    }

    public String getName() {
        return name;
    }

    /* Add By HanhND2 */
    public int getID() {
        return this.id;
    }
    public int getAvatar() {
        return this.avatar;
    }
    public int getTrophy() {
        return this.trophy;
    }
    public int getLevel() {
        return this.level;
    }
    public int getExp() {
        return this.exp;
    }
    public int getGold() {
        return this.gold;
    }
    public int getG() {
        return this.g;
    }
    public int upGold(int gold){
        this.gold += gold;
        return this.gold;
    }
    public int upG(int g){
        this.g += g;
        return this.g;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public ChestController getChestController() {
        return this.chestController;
    }


    /*        */
    public String setName(String name) {
        this.name = name;
        return this.getName();
    }
}
