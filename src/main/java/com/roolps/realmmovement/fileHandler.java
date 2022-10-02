package com.roolps.realmmovement;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fileHandler {
    File file = new File(Bukkit.getServer().getPluginManager().getPlugin("RealmMovement").getDataFolder(), "config.yml");
    YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);

    public void list(CommandSender sender){
        sender.sendMessage(" === §9[REALM MOVEMENT ACTIVE MAPS]§f ===");
        List<String> values = yaml.getStringList("active-maps");
        values.forEach(s -> sender.sendMessage(" - " + s));
    }

    public boolean add(String val){
        try{
            if(file.exists()){
                List<String> values = yaml.getStringList("active-maps");
                values.add(val);
                yaml.set("active-maps", values);
                yaml.save(file);
                return true;
            }else{
                return false;
            }
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public int remove(String val){
        try{
            if(file.exists()){
                List<String> values = yaml.getStringList("active-maps");
                if(values.remove(val)){
                    if (main.map.equalsIgnoreCase(val)){
                        main.map = values.get(values.indexOf(val) + 1);
                    }
                    yaml.set("active-maps", values);
                    yaml.save(file);
                    return 0;
                }else{
                    return 1;
                }
            }else{
                return 1;
            }
        }catch (IOException e){
            e.printStackTrace();
            return 2;
        }
    }

    public void toggle(CommandSender sender){
        try{
            if(file.exists()){
                Boolean status = yaml.getBoolean("status");
                status = !status;
                yaml.set("status", status);
                if(status){
                    yaml.save(file);
                    main.active = true;
                    main.sendMSG(sender, "Countdown Enabled");
                }else{
                    yaml.set("counter", main.counter);
                    main.active = false;
                    main.sendMSG(sender, "Countdown Disabled");
                    yaml.save(file);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            main.sendMSG(sender, "An error ocurred while performing this action.");
        }
    }

    public void shutdown(){
        try{
            if(file.exists()){
                yaml.set("counter", main.counter);
                yaml.save(file);
                main.sendMSG(main.console, "Successfully saved counter");
            }
        }catch (IOException e){
            e.printStackTrace();
            main.sendMSG(main.console, "An error occured while saving the current counter");
        }

    }

    public void status(CommandSender sender){
        if(yaml.getBoolean("status")){
            main.sendMSG(sender, "Countdown Active");
        }else{
            main.sendMSG(sender, "Countdown Inactive");
        }
    }

    public List<String> active(){
        List<String> values = new ArrayList<>();
        values.add(yaml.getString("status"));
        values.add(yaml.getString("counter"));
        values.add(yaml.getString("map-interval"));
        values.add(yaml.getString("current-map"));
        return values;
    }

    public void maps(String active){
        if(file.exists()){
            try{
                List<String> values = yaml.getStringList("active-maps");
                if(values.indexOf(active) < (values.size() - 1)){
                    new actionHandler().executeSwitch(active, values.get(values.indexOf(active) + 1));
                    yaml.set("current-map", values.get(values.indexOf(active) + 1));
                    yaml.save(file);
                    main.map = values.get(values.indexOf(active) + 1);
                }else{
                    new actionHandler().clear();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public List<String> getMaps(){
        return yaml.getStringList("active-maps");
    }

    public boolean interval(Integer val){
        try{
            yaml.set("map-interval", val);
            yaml.save(file);
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean reset(){
        try{
            List<String> values = yaml.getStringList("active-maps");
            main.map = values.get(0);
            main.counter = 0;
            yaml.set("current-map", values.get(0));
            yaml.save(file);
            main.active = true;
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
}
