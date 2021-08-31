package me.emmetion.emmetapi.menu;

import org.bukkit.entity.Player;

import java.util.Stack;

public class PlayerMenuUtility {

    private final Player owner;
    private final Stack<Menu> history = new Stack<>();

    public PlayerMenuUtility(Player p) {
        this.owner = p;
    }

    public Player getOwner(){
        return owner;
    }

    public Menu lastMenu(){
        this.history.pop();
        return this.history.pop();
    }

    public void pushMenu(Menu menu){
        this.history.push(menu);
    }

}
