package com.roolps.realmmovement;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class commandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            main.sendMSG(sender, "Realm Movement version BETA-1.0 written by Roolps");
        }else {
            switch (args[0].toLowerCase()) {
                case "help":
                    sender.sendMessage("=== §9[REALM MOVEMENT COMMANDS]§f ===");
                    sender.sendMessage("rmov help");
                    sender.sendMessage("rmov toggle");
                    sender.sendMessage("rmov status");
                    sender.sendMessage("rmov test");
                    sender.sendMessage("rmov list");
                    sender.sendMessage("");
                    sender.sendMessage("rmov add <map name>");
                    sender.sendMessage("rmov remove <map name>");
                    sender.sendMessage("rmov interval <time in minutes>");
                    sender.sendMessage("");
                    sender.sendMessage("rmov reset");
                    sender.sendMessage("rmov run");
                    break;
                case "toggle":
                    new fileHandler().toggle(sender);
                    break;
                case "status":
                    new fileHandler().status(sender);
                    break;
                case "add":
                    if (args.length > 1) {
                        main.sendMSG(sender, "Adding Map...");
                        if (new fileHandler().add(args[1])) {
                            main.sendMSG(sender, "Successfully added map " + args[1]);
                        } else {
                            main.sendMSG(sender, "An error ocurred while performing this action.");
                        }
                    } else {
                        main.sendMSG(sender, "Please use the correct parameters!");
                    }
                    break;
                case "remove":
                    if (args.length > 1) {
                        if (new fileHandler().remove(args[1]) == 0){
                            main.sendMSG(sender, "Successfully removed map " + args[1]);
                        }else if (new fileHandler().remove(args[1]) == 1){
                            main.sendMSG(sender, "Invalid map name " + args[1]);
                        }else{
                            main.sendMSG(sender, "An error ocurred while performing this action.");
                        }
                    } else {
                        main.sendMSG(sender, "Please use the correct parameters!");
                    }
                    break;
                case "list":
                    new fileHandler().list(sender);
                    break;
                case "test":
                    main.sendMSG(sender, "There are " + (main.interval - main.counter) + " Minutes remaining before a map change.");
                    break;
                case "interval":
                    if(args.length > 1) {
                        if(new fileHandler().interval(Integer.valueOf(args[1]))){
                            main.interval = Integer.valueOf(args[1]);
                            main.sendMSG(sender, "Interval is now " + args[1] + " Minutes");
                        }else{
                            main.sendMSG(sender, "An error ocurred while performing this action.");
                        }
                    }
                    break;
                case "reset":
                    if(new fileHandler().reset()){
                        main.sendMSG(sender, "Maps reset");
                    }else{
                        main.sendMSG(sender, "An error ocurred while performing this action.");
                    }
                    break;
                case "run":
                    main.counter = 0;
                    new fileHandler().maps(main.map);
                    break;
                default:
                    main.sendMSG(sender, "Unknown Parameters");
            }
        }
        return true;
    }
}
