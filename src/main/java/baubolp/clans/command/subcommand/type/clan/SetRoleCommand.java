package baubolp.clans.command.subcommand.type.clan;

import baubolp.clans.Clans;
import baubolp.clans.clan.Clan;
import baubolp.clans.command.subcommand.SubCommand;
import baubolp.clans.player.User;
import baubolp.clans.player.UserManager;
import baubolp.clans.role.Role;
import baubolp.clans.role.RoleManager;
import dev.waterdog.waterdogpe.command.Command;
import dev.waterdog.waterdogpe.command.CommandSender;
import dev.waterdog.waterdogpe.logger.Color;

public class SetRoleCommand extends SubCommand {

    public SetRoleCommand() {
        super("role");
    }

    @Override
    public String getDescription() {
        return "Give a member a role";
    }

    @Override
    public void execute(CommandSender sender, Command parent, String[] args) {
        UserManager userManager = Clans.getUserManager();
        RoleManager roleManager = Clans.getRoleManager();
        if(!userManager.isRegistered(sender.getName())) return;
        User user = userManager.getUser(sender.getName());

        if(!user.isInClan()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You are clanless");
            return;
        }

        if(!user.hasPermission("member.update.role")) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Your role can't do that!");
            return;
        }

        if(args.length != 3) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "Please use: " + Color.YELLOW + "/clan setrole <Name of Member> <Role>");
            return;
        }

        String playerName = args[1];
        String roleName = args[2];

        if(!userManager.isRegistered(sender.getName())) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The player " + Color.YELLOW + playerName + Color.RED + " isn't registered!");
            return;
        }

        User roleUser = userManager.getUser(playerName);
        Clan clan = roleUser.getClan();
        if(!clan.getName().equals(user.getClan().getName())) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The player isn't in your clan!");
            return;
        }

        if(!roleManager.existRole(roleName)) {
            StringBuilder builder = new StringBuilder();
            builder.append(Clans.PREFIX).append(Color.RED).append("The role does not exist!");
            builder.append(Clans.PREFIX).append(Color.YELLOW).append("List of roles:");
            for(Role role : roleManager.getRoleHashMap().values()) {
                builder.append("\n").append(Color.DARK_GRAY).append("=> ").append(Color.AQUA).append(role.getName());
            }
            sender.sendMessage(builder.toString());
            return;
        }

        if(user.getRole().getPriority() <= roleUser.getRole().getPriority()) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "You cannot give a role to a player who has the same or a higher role!");
            return;
        }

        if(roleUser.getRole().getName().equals(roleName)) {
            sender.sendMessage(Clans.PREFIX + Color.RED + "The member has the role already!");
            return;
        }

        roleUser.setRole(roleName, true);
        sender.sendMessage(Clans.PREFIX + Color.GREEN + "You gave the player " + Color.YELLOW + playerName + Color.GREEN + " the role " + Color.AQUA + roleName);
    }
}
