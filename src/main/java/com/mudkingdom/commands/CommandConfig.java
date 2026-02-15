package com.mudkingdom.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandConfig {
    
    private final JavaPlugin plugin;
    
    public CommandConfig(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUsage: /mk config <version|updateMessage>");
            return;
        }
        
        String subCommand = args[1].toLowerCase();
        
        if (subCommand.equals("version")) {
            handleVersion(sender, args);
        } else if (subCommand.equals("updatemessage")) {
            handleUpdateMessage(sender, args);
        } else {
            sender.sendMessage("§cUnknown config option! Use: version or updateMessage");
        }
    }
    
    private void handleVersion(CommandSender sender, String[] args) {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        
        if (args.length == 2) {
            double version = config.getDouble("ServerVersion");
            sender.sendMessage("§aCurrent server version: §e" + version);
            return;
        }
        
        if (args.length == 4 && args[2].equalsIgnoreCase("set")) {
            try {
                double newVersion = Double.parseDouble(args[3]);
                
                if (newVersion < 0 || newVersion >= 10) {
                    sender.sendMessage("§cVersion must be between 0.000 and 9.999");
                    return;
                }
                
                config.set("ServerVersion", newVersion);
                config.save(configFile);
                
                sender.sendMessage("§aServer version set to: §e" + newVersion);
            } catch (NumberFormatException e) {
                sender.sendMessage("§cInvalid number format! Use: 2.5 or 2.500");
            } catch (IOException e) {
                sender.sendMessage("§cFailed to save config: " + e.getMessage());
            }
            return;
        }
        
        sender.sendMessage("§cUsage: /mk config version [set <number>]");
    }
    
    private void handleUpdateMessage(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUsage: /mk config updateMessage \"[{json...}]\"");
            return;
        }
        
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            if (i > 2) messageBuilder.append(" ");
            messageBuilder.append(args[i]);
        }
        
        String newMessage = messageBuilder.toString();
        
        if (newMessage.startsWith("\"") && newMessage.endsWith("\"")) {
            newMessage = newMessage.substring(1, newMessage.length() - 1);
        }
        
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        
        config.set("UpdateMessage", newMessage);
        
        try {
            config.save(configFile);
            sender.sendMessage("§aUpdate message has been set!");
        } catch (IOException e) {
            sender.sendMessage("§cFailed to save config: " + e.getMessage());
        }
    }
}