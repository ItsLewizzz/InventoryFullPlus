package me.lewis.inventoryfull.alert;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private Map<String, Long> cooldowns = new HashMap<>();

    private long getCooldown(UUID uuid) {
        return calculateRemainder(cooldowns.get(uuid.toString()));
    }

    private long setCooldown(UUID uuid, long delay) {
        return calculateRemainder(cooldowns.put(uuid.toString(), System.currentTimeMillis() + (delay * 1000)));
    }

    public boolean tryCooldown(UUID uuid, long delay) {
        if (getCooldown(uuid) / 1000 > 0) return false;
        setCooldown(uuid, delay + 1);
        return true;
    }

    public void removeCooldowns(UUID uuid) {
        cooldowns.remove(uuid.toString());
    }

    private long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }
}