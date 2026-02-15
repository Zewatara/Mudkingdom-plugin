package com.mudkingdom;

import org.bukkit.command.PluginCommand;
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
        
        PluginCommand command = this.getCommand("mudkingdom");
        if (command != null) {
            command.setExecutor(new CommandHandler(this));
        } else {
            getLogger().severe("Failed to register command 'mudkingdom' - check plugin.yml!");
        }
        
        getServer().getPluginManager().registerEvents(
            new PlayerJoinListener(playerDataManager, versionManager), 
            this
        );
    }
}