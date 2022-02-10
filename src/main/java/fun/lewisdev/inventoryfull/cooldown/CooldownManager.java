package fun.lewisdev.inventoryfull.cooldown;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<String, Long> cooldowns = new HashMap<>();

    private long getCooldown(UUID uuid) {
        return calculateRemainder(cooldowns.get(uuid.toString()));
    }

    public long setCooldown(UUID uuid, long delay) {
        return calculateRemainder(cooldowns.put(uuid.toString(), System.currentTimeMillis() + (delay * 1000)));
    }

    public boolean tryCooldown(UUID uuid) {
        if (getCooldown(uuid) / 1000 > 0) return false;
        return true;
    }

    public void removeCooldowns(UUID uuid) {
        cooldowns.remove(uuid.toString());
    }

    private long calculateRemainder(Long expireTime) {
        return expireTime != null ? expireTime - System.currentTimeMillis() : Long.MIN_VALUE;
    }
}