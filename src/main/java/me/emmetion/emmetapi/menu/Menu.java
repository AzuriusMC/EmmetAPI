package me.emmetion.emmetapi.menu;

import me.emmetion.emmetapi.color.ColorTranslator;
import me.emmetion.emmetapi.exceptions.MenuManagerException;
import me.emmetion.emmetapi.exceptions.MenuManagerNotSetupException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Menu implements InventoryHolder {

    protected PlayerMenuUtility playerMenuUtility;
    protected Player p;
    protected Inventory inventory;
    protected ItemStack FILLER_GLASS = makeItem(Material.GRAY_STAINED_GLASS_PANE,"&c",null);


    public Menu(PlayerMenuUtility playerMenuUtility){
        this.playerMenuUtility = playerMenuUtility;
        this.p = playerMenuUtility.getOwner();
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract boolean cancelAllInteractions();

    public abstract void handleMenu(InventoryClickEvent event) throws MenuManagerNotSetupException, MenuManagerException;

    public abstract void setMenuItems();

    public void open() {
        inventory = Bukkit.createInventory(this, getSlots(),getMenuName());

        this.setMenuItems();

        playerMenuUtility.getOwner().openInventory(inventory);
        playerMenuUtility.pushMenu(this);
    }

    public void back() throws MenuManagerException, MenuManagerNotSetupException {
        MenuManager.openMenu(playerMenuUtility.lastMenu().getClass(),playerMenuUtility.getOwner());
    }

    protected void reloadItems() {
        for (int i = 0; i < inventory.getSize(); i++){
            inventory.setItem(i, null);
        }
        setMenuItems();
    }

    protected void reload() throws MenuManagerNotSetupException, MenuManagerException {
        p.closeInventory();
        MenuManager.openMenu(this.getClass(), p);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void setFillerGlass(){
        for (int i = 0; i < getSlots(); i++){
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, FILLER_GLASS);
            }
        }
    }

    public ItemStack makeItem(Material material, String displayName, String... lore){
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ColorTranslator.getColor(displayName));
        itemMeta.setLore(Arrays.stream(lore).map(ColorTranslator::getColor).collect(Collectors.toList()));
        item.setItemMeta(itemMeta);

        return item;
    }

}
