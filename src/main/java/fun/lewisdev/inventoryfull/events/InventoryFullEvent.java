package fun.lewisdev.inventoryfull.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryFullEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private Player player;
    private List<ItemStack> items;

    public InventoryFullEvent(Player player, List<ItemStack> items) {
        this.player = player;
        this.items = items;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public HandlerList getHandlersList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public List<ItemStack> getItems() {
        return items;
    }


}
