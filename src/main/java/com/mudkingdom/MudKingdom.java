package com.mudkingdom;

import org.bukkit.plugin.java.JavaPlugin;

import com.mudkingdom.commands.CommandHandler;
import com.mudkingdom.listeners.PlayerJoinListener;
import com.mudkingdom.managers.MessageManager;
import com.mudkingdom.managers.PlayerDataManager;
import com.mudkingdom.managers.VersionManager;


public class MudKingdom extends JavaPlugin {

    private PlayerDataManager playerDataManager;
    private VersionManager versionManager;
    private MessageManager messageManager;
    
    @Override
    public void onEnable() {
        getLogger().info("enabled.");
        
        if (!new PluginSetup(this).initialize()) {
            getLogger().severe("Setup failed! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        initialize();
    }

    @Override
    public void onDisable() {
        getLogger().info("disabled.");
    }
    
    private void initialize() {
        messageManager = new MessageManager(this);
        playerDataManager = new PlayerDataManager(this);
        versionManager = new VersionManager(this, playerDataManager, messageManager);
        
        this.getCommand("mudkingdom").setExecutor(new CommandHandler());

        getServer().getPluginManager().registerEvents(
            new PlayerJoinListener(playerDataManager, versionManager), 
            this
        );
    }
}