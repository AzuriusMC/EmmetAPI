package me.emmetion.emmetapi;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Vector;

public final class EmmetAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("EmmetAPI has been enabled!");
    }
@NotNull
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Vector v = new Vector();


    }
}
