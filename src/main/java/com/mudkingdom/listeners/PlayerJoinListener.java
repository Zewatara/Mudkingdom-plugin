package com.mudkingdom.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mudkingdom.managers.PlayerDataManager;
import com.mudkingdom.managers.VersionManager;

public class PlayerJoinListener implements Listener 
{
    private final PlayerDataManager playerDataManager;
    private final VersionManager versionManager;
    
    public PlayerJoinListener(PlayerDataManager playerDataManager, VersionManager versionManager) {
        this.playerDataManager = playerDataManager;
        this.versionManager = versionManager;
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        playerDataManager.handlePlayerJoin(event.getPlayer());
        versionManager.checkLastJoin(event.getPlayer());
    }
}