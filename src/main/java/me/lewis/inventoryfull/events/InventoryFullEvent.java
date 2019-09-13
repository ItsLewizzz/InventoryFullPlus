package me.lewis.inventoryfull.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InventoryFullEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private Player player;
    private Block block;

    public InventoryFullEvent(Player player, Block block) {
        this.player = player;
        this.block = block;
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

    public Block getBlock() {
        return block;
    }


}
