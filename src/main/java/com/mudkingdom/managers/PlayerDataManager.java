package com.mudkingdom.managers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerDataManager 
{
    private final JavaPlugin plugin;
    private final File playerDataFolder;
    
    public PlayerDataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerDataFolder = new File(plugin.getDataFolder(), "playerdata");
    }
    
    public void handlePlayerJoin(Player player) {
        String uuid = player.getUniqueId().toString();
        File playerFile = new File(playerDataFolder, uuid + ".yml");
        
        if (!playerFile.exists()) {
            createPlayerFile(uuid);
        }
    }
    
    public void updatePlayerData(Player player, String handle, Object content)
    {
        String uuid = player.getUniqueId().toString();
        File playerFile = new File(playerDataFolder, uuid + ".yml");
        
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
        playerData.set(handle, content);
        
        try {
            playerData.save(playerFile);
            plugin.getLogger().info("Updated " + handle + " for player: " + uuid);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save player data: " + e.getMessage());
        }
    }
    
    private void createPlayerFile(String uuid)
    {
        File playerFile = new File(playerDataFolder, uuid + ".yml");
        try {
            playerFile.createNewFile();
            
            FileWriter writeService = new FileWriter(playerFile);
            writeService.write("LastJoin: 0.0\nAreas: false");
            writeService.close();
            
            plugin.getLogger().info("created player file for: " + uuid);
            
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to create player file: " + e.getMessage());
        }
    }
}