package baubolp.clans.command.subcommand.type.clan;

import baubolp.clans.Clans;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

import java.util.ArrayList;

public class ColorCommand extends SubCommand {

    public ArrayList<String> colors;

    public ColorCommand() {
        super("color");
        colors = new ArrayList<>();
        loadColors();
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        UserManager userManager = Clans.getUserManager();
        if(!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if(!user.isInClan()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You are clanless");
            return;
        }

        if(!user.hasPermission("color.update")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your role can't do that!");
            return;
        }

        if(args.length != 2) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/clan color <Color>");
            sender.sendMessage(Clans.PREFIX + Color.YELLOW + "Colors: " + Color.GOLD + String.join(", ", colors));
            sender.sendMessage(Clans.PREFIX + Color.GREEN + "Tip: Use /cui for colors. There you can see how it looks too.");
            return;
        }


        String color = args[1];
        if(!colors.contains(color)) {
            sender.sendMessage(Clans.PREFIX + Color.YELLOW + "Colors: " + Color.GOLD + String.join(", ", colors));
            return;
        }

        user.getClan().setColor(color.replace("&", "§"));
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "Your clan color has been updated to " + color.replace("&", "§") + color);
    }

    @Override
    public String getDescription() {
        return "Update your clan color";
    }

    private void loadColors() {
        for(int i = 1; i < 10; i++)
            colors.add("&" + i);

        colors.add("&e");
        colors.add("&f");
        colors.add("&d");
        colors.add("&g");
        colors.add("&b");
        colors.add("&a");
    }
}
