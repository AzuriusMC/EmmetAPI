package me.emmetion.emmetapi.menu;

import me.emmetion.emmetapi.exceptions.MenuManagerException;
import me.emmetion.emmetapi.exceptions.MenuManagerNotSetupException;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class MenuManager {

    private static final HashMap<Player, PlayerMenuUtility> playerMenuUtilityMap = new HashMap<>();
    private static boolean isSetup = false;

    private static void registerMenuListener(Server server, Plugin plugin){
        boolean isAlreadyRegistered = false;
        for (RegisteredListener rl : InventoryClickEvent.getHandlerList().getRegisteredListeners()){
            System.out.println(rl.getListener().getClass().getSimpleName());
            if (rl.getListener() instanceof MenuListener){
                isAlreadyRegistered = true;
                break;
            }
        }

        if (!isAlreadyRegistered){
            server.getPluginManager().registerEvents(new MenuListener(),plugin);
        }
    }

    public static void setup(Server server, Plugin plugin){

        if (isSetup){
            return;
        }

        System.out.println("MenuManager has been setup for " + plugin.getName() + "!");
        registerMenuListener(server,plugin);
        isSetup = true;
    }

    public static void openMenu(Class<? extends Menu> menuClass, Player player) throws MenuManagerNotSetupException, MenuManagerException {
        try{
            menuClass.getConstructor(PlayerMenuUtility.class).newInstance(getPlayerMenuUtility(player)).open();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

        }
    }

    public static PlayerMenuUtility getPlayerMenuUtility(Player p) throws MenuManagerNotSetupException {
        if (!isSetup){
            throw new MenuManagerNotSetupException();
        }

        PlayerMenuUtility playerMenuUtility;
        if (!(playerMenuUtilityMap.containsKey(p))){
            playerMenuUtility = new PlayerMenuUtility(p);
            playerMenuUtilityMap.put(p,playerMenuUtility);

            return playerMenuUtility;
        }else {
            return playerMenuUtilityMap.get(p);
        }

    }


}
