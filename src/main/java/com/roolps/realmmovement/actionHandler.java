package com.roolps.realmmovement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class actionHandler {
    public void executeSwitch(String from, String to){
        Bukkit.getServer().dispatchCommand(main.console, "mv load " + to);
        Bukkit.getServer().dispatchCommand(main.console, "mv config firstspawnworld " + to);
        for(Player p : Bukkit.getOnlinePlayers()){
            main.sendMSG(main.console, "Teleporting " + p.getDisplayName() + "...");
            Bukkit.getServer().dispatchCommand(main.console, "mvtp " + p.getDisplayName() + " " + to);
        }
        Bukkit.getServer().dispatchCommand(main.console, "mv unload " + from);
        main.sendMSG(main.console, "Loaded map " + to);
        main.sendMSG(main.console, "Unloaded map " + from);
    }

    public void clear(){
        main.active = false;
        main.sendMSG(main.console, "Plugin has completed it's cycle.");
    }

    public void boot(String active){
        Bukkit.getServer().dispatchCommand(main.console, "mv config firstworldspawn " + active);
        List<String> values = new fileHandler().getMaps();
        values.remove(active);
        values.forEach(s -> Bukkit.getServer().dispatchCommand(main.console, "mv unload " + s));
    }
}
