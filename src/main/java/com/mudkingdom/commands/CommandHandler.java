package com.mudkingdom.commands;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandHandler implements CommandExecutor {
    
    private final CommandConfig configCommand;
    private final CommandAreas areasCommand;
    
    public CommandHandler(JavaPlugin plugin) {
        this.configCommand = new CommandConfig(plugin);
        this.areasCommand = new CommandAreas();
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender == null || args == null) {
            return false;
        }
        
        if (args.length == 0) {
            sender.sendMessage("§cUsage: /mk <config|areas>");
            return true;
        }
        
        String category = args[0].toLowerCase();
        
        if (category.equals("areas")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can use this command!");
                return true;
            }
            
            Player player = (Player) sender;
            
            if (player.hasPermission("mudkingdom.areas") || player.isOp()) {
                //areasCommand.execute();
            } else {
                player.sendMessage("§cYou don't have permission!");
            }
            
            return true;
        }
        
        if (category.equals("config")) {
            if (sender instanceof BlockCommandSender) {
                sender.sendMessage("§cCommand blocks cannot use this!");
                return true;
            }
            
            if (sender.hasPermission("mudkingdom.config") || sender.isOp()) {
                configCommand.execute(sender, args);
            } else {
                sender.sendMessage("§cYou don't have permission!");
            }
            
            return true;
        }
        
        sender.sendMessage("§cUnknown category! Use: config or areas");
        return true;
    }
}