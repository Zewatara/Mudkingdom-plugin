package com.mudkingdom.managers;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageManager {
    
    private final JavaPlugin plugin;
    
    public MessageManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void sendTellrawMessage(Player player, String jsonMessage) {
    Bukkit.getScheduler().runTaskLater(plugin, () -> {
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HARP, 1.0f, 1.0f);
        
        String command = "tellraw " + player.getName() + " " + jsonMessage;
        plugin.getLogger().info("Executing: " + command); // DEBUG
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        
    }, 20L);
}
}