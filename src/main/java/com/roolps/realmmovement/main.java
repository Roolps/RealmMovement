package com.roolps.realmmovement;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public final class main extends JavaPlugin {
    public final static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
    public final static String prefix = "§9[RealmMovement] §f";
    public static boolean active = false;
    public static int counter;
    public static String map;
    public static int interval;

    @Override
    public void onEnable() {

        this.getCommand("rmov").setExecutor(new commandHandler());

        if (!new File(getDataFolder(), "config.yml").exists()){
            saveResource("config.yml", false);
            sendMSG(console, "Initiated default configuration");
        }

        initialise();
    }

    @Override
    public void onDisable() {
        new fileHandler().shutdown();
        sendMSG(console, "Bye!!!");
    }

    public static void sendMSG(CommandSender sender, Object msg){
        sender.sendMessage(prefix + msg);
    }

    public static void initialise(){
        List<String> values = new fileHandler().active();
        active = Boolean.valueOf(values.get(0));
        counter = Integer.valueOf(values.get(1));
        interval = Integer.valueOf(values.get(2));
        map = values.get(3);
        new actionHandler().boot(map);
        for(Player p : Bukkit.getOnlinePlayers()) {
            Bukkit.getServer().dispatchCommand(main.console, "mvtp " + p.getDisplayName() + " " + map);
        }

        if(!active){
            sendMSG(console,"World Countdown is Currently Inactive.");
        }

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("RealmMovement"), new Runnable() {
            @Override
            public void run() {
                if(active){
                    if(counter < interval){
                        counter++;
                    }else{
                        counter = 0;
                        new fileHandler().maps(map);
                    }
                }
            }
        }, 0L, 1200L);

    }
}
