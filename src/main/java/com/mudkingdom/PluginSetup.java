package com.mudkingdom;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.plugin.java.JavaPlugin;

public class PluginSetup {
    
    private final JavaPlugin plugin;
    
    public PluginSetup(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    public boolean initialize() {
        plugin.getLogger().info("setting up.");
        return setupFiles();
    }
    
    private boolean setupFiles() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            plugin.getLogger().info("making plugin folder.");
            dataFolder.mkdirs();
        }
        
        File configFile = new File(dataFolder, "config.yml");
        if (!configFile.exists()) {
            plugin.getLogger().info("making config file.");
            try (FileWriter writer = new FileWriter(configFile)) {
                configFile.createNewFile();
                writer.write("ServerVersion: 2.0\n");
                writer.write("UpdateMessage: Welcome to mud kingdom 2.0. I am still migrating some plugins. If you find any issues contact Zewatara <.3\n");
                plugin.getLogger().info("config file created.");
            } catch (IOException e) {
                plugin.getLogger().severe("failed to create config file: " + e.getMessage());
                return false;
            }
        }
        
        File playerData = new File(dataFolder, "playerdata");
        if (!playerData.exists()) {
            plugin.getLogger().info("making playerdata folder.");
            if (!playerData.mkdirs()) {
                plugin.getLogger().severe("failed to create playerdata folder.");
                return false;
            }
        }
        
        return true;
    }
}