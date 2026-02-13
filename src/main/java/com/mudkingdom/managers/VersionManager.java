package com.mudkingdom.managers;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VersionManager {
    
    private final JavaPlugin plugin;
    private final PlayerDataManager playerDataManager;
    private final MessageManager messageManager;
    
    private final double serverVersion;
    private final String updateMessage;
    
    public VersionManager(JavaPlugin plugin, PlayerDataManager playerDataManager, MessageManager messageManager) {
        this.plugin = plugin;
        this.playerDataManager = playerDataManager;
        this.messageManager = messageManager;
        
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration serverConfig = YamlConfiguration.loadConfiguration(configFile);
        this.serverVersion = serverConfig.getDouble("ServerVersion");
        this.updateMessage = serverConfig.getString("UpdateMessage");
    }

    public void checkLastJoin(Player player) {  
        String uuid = player.getUniqueId().toString();
        File playerFile = new File(plugin.getDataFolder(), "playerdata/" + uuid + ".yml");
        
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
        double lastJoin = playerData.getDouble("LastJoin");
    
        if(lastJoin < serverVersion)
        {
            messageManager.sendTellrawMessage(player, updateMessage);
            playerDataManager.updatePlayerData(player, "LastJoin", serverVersion);
        }
    }
}