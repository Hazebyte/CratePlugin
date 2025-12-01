package com.hazebyte.crate.cratereloaded.cmd;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Flags;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import co.aikar.commands.contexts.OnlinePlayer;
import com.google.common.base.Strings;
import com.hazebyte.crate.api.CrateAPI;
import com.hazebyte.crate.api.crate.BlockCrateRegistrar;
import com.hazebyte.crate.api.crate.Crate;
import com.hazebyte.crate.api.util.Messages;
import com.hazebyte.crate.api.util.Messenger;
import com.hazebyte.crate.cratereloaded.CorePlugin;
import com.hazebyte.crate.cratereloaded.component.ClaimCrateComponent;
import com.hazebyte.crate.cratereloaded.component.GiveCrateComponent;
import com.hazebyte.crate.cratereloaded.component.impl.ClaimCrateComponentImpl;
import com.hazebyte.crate.cratereloaded.component.impl.GiveCrateComponentImpl;
import com.hazebyte.crate.cratereloaded.util.Camera;
import com.hazebyte.crate.cratereloaded.util.LocationUtil;
import com.hazebyte.crate.cratereloaded.util.format.CustomFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("crate|cr")
public class CrateCommand extends BaseCommand {

    private final CorePlugin plugin;

    public CrateCommand(CorePlugin plugin) {
        this.plugin = plugin;
    }

    @Subcommand("version")
    @Description("Tells you the plugin's attributes")
    public void onVersion(CommandSender sender) {
        TextComponent versionMessage = new TextComponent("CrateReloaded Version "
                + plugin.getDescription().getVersion());
        TextComponent authorMessage = new TextComponent("by Will (bart7567) and ToXiicalhebest");
        TextComponent supportLink = new TextComponent("[Support / Need Help]");
        TextComponent bugReportLink = new TextComponent("[Report an issue]");
        TextComponent wikiLink = new TextComponent("[Wiki]");

        versionMessage.setClickEvent(
                new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/x.3663/"));
        versionMessage.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to go to the plugin page.").create()));

        supportLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/0srgnnU1nbB8wMML"));
        supportLink.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to open the support page.").create()));

        bugReportLink.setClickEvent(
                new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/Hazebyte/CrateReloaded"));
        bugReportLink.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to report an issue.").create()));

        wikiLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://crates.hazebyte.com/"));
        wikiLink.setHoverEvent(new HoverEvent(
                HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to find information.").create()));

        BaseComponent[] versionComponents = new ComponentBuilder(versionMessage)
                .append(" ")
                .append(authorMessage)
                .create();

        BaseComponent[] linkComponents = new ComponentBuilder(supportLink)
                .color(ChatColor.GREEN)
                .append(" ")
                .append(bugReportLink)
                .color(ChatColor.RED)
                .append(" ")
                .append(wikiLink)
                .color(ChatColor.BLUE)
                .create();

        sender.spigot().sendMessage(versionComponents);
        sender.spigot().sendMessage(linkComponents);
    }

    @Subcommand("help")
    @HelpCommand
    @Description("Tells you the commands and help message")
    @CommandPermission("cr.base")
    public void onHelp(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("reload")
    @CommandPermission("cr.reload")
    @Description("Reloads the plugin")
    public void onReload(CommandSender sender) {
        plugin.reloadAll();
        Messenger.tell(sender, Messages.RELOAD);
    }

    @Subcommand("claim")
    public class ClaimCommand extends BaseCommand {

        @Default
        @Subcommand("menu")
        @CommandPermission("cr.claim")
        @Description("Claim rewards/crates that have been given to you")
        public void onClaim(Player player) {
            ClaimCrateComponent component =
                    CorePlugin.getJavaPluginComponent().getClaimCrateComponent();
            component.openClaim(player);
        }

        @Subcommand("all")
        @CommandPermission("cr.claim.all")
        @CommandCompletion("all")
        @Description("Claim all rewards/crates that have been given to you")
        public void onClaimAll(Player player) {
            ClaimCrateComponent component =
                    CorePlugin.getJavaPluginComponent().getClaimCrateComponent();
            component.claimAllRewards(player);
        }
    }

    @Subcommand("util")
    public class UtilCommand extends BaseCommand {
        @Subcommand("item")
        @CommandPermission("cr.util.item")
        @Description("Get the crate reloaded string of the item in hand")
        public void onItem(Player player) {
            CorePlugin.getJavaPluginComponent().getExportComponent().exportItem(player);
        }

        @Subcommand("export")
        @CommandCompletion("-sort")
        @CommandPermission("cr.util.export")
        @Description("Exports the CrateReloaded strings inside a container to a file")
        public void onExport(Player player, @Optional String fileName, @Optional String sort) {
            CorePlugin.getJavaPluginComponent().getExportComponent().exportCrate(player, fileName, sort);
        }
    }

    @Subcommand("preview")
    @CommandPermission("cr.preview")
    @CommandCompletion("@crates")
    @Syntax("<crate>")
    @Description("Previews a crate")
    public void onPreview(Player player, Crate crate) {
        plugin.getCrateRegistrar().preview(crate, player);
    }

    @Subcommand("list")
    @CommandPermission("cr.list")
    @Description("Returns the list of crates")
    public void onList(CommandSender sender) {
        String message = CrateAPI.getMessage("core.list_crates");
        Messenger.tell(sender, CustomFormat.format(message));
    }

    @Subcommand("info")
    @CommandPermission("cr.info")
    @CommandCompletion("@crates")
    @Syntax("<crate>")
    @Description("Returns information on the crate that you provided by name or by the block you are looking" + " at")
    public void onInfo(CommandSender sender, @Optional String crateName) {
        if (Strings.isNullOrEmpty(crateName)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                List<String> crates = new ArrayList<>();
                List<Crate> locationCrates = plugin.getBlockCrateRegistrar()
                        .getCrates(Camera.getTargetBlockLocation(player));
                if (locationCrates != null) {
                    locationCrates.forEach(crate -> crates.add(crate.getCrateName()));
                }
                if (crates.isEmpty()) {
                    Messenger.tell(sender, "There are no crates associated with that block.");
                } else {
                    Messenger.tell(sender, String.format("Crates found: %s", String.join(", ", crates)));
                }
            }
        } else {
            Crate crate = plugin.getCrateRegistrar().getCrate(crateName);
            if (crate != null) {
                Messenger.tell(
                        sender,
                        String.format(
                                "Crate: %s - $%.2f (%d rewards)",
                                crate.getCrateName(),
                                crate.getCost(),
                                crate.getRewards().size()));
                Messenger.tell(
                        sender,
                        String.format(
                                "Upon opening, yields %d to %d rewards.",
                                crate.getMinimumRewards(), crate.getMaximumRewards()));
            } else {
                Messenger.tell(sender, String.format("%s is not a valid crate name!", crateName));
            }
        }
    }

    @Subcommand("buy")
    @CommandPermission("cr.buy")
    @CommandCompletion("@crates @range:64")
    @Syntax("<crate> <number>")
    @Description("Purchase a crate for money")
    public void onBuy(Player player, Crate crate, @Default("1") Integer amount) {
        if (amount > 64) {
            Messenger.tell(player, "You can only purchase a maximum of 64 crates.");
            return;
        }

        if (!(crate.isBuyable())) {
            String message = CrateAPI.getMessage("core.player_crate_is_not_for_sale");
            Messenger.tell(player, CustomFormat.format(message, crate));
            return;
        }

        boolean success = plugin.getCrateRegistrar().purchase(crate, player, amount);
        if (success) {
            String message = CrateAPI.getMessage("core.player_successful_transaction");
            Messenger.tell(player, CustomFormat.format(message, crate));
        } else {
            String message = CrateAPI.getMessage("core.player_insufficient_balance");
            Messenger.tell(player, CustomFormat.format(message, crate));
        }
    }

    @Subcommand("open")
    @CommandPermission("cr.open")
    @CommandCompletion("@players @crates")
    @Syntax("<player> <crate>")
    @Description("Force opens a crate for a certain player")
    public void onForceOpenCommand(CommandSender sender, OnlinePlayer playerName, Crate crate) {
        Player player = playerName.getPlayer();
        Map<String, Object> settings = new HashMap<String, Object>() {
            {
                put("shouldRemoveItem", false);
            }
        };
        plugin.getCrateRegistrar().open(crate, player, player.getLocation(), settings);
    }

    @Subcommand("give to")
    @CommandPermission("cr.give.to")
    @CommandCompletion("@players @crates @range:64 @status")
    @Syntax("<player> <crate> [number] [online/offline]")
    @Description("Give a certain number of crates to a player")
    public void onGive(
            CommandSender sender,
            @Flags("uuid") OfflinePlayer offlinePlayer,
            Crate crate,
            @Default("1") Integer amount,
            @Default("online") Boolean isOnline) {
        GiveCrateComponent component =
                CorePlugin.getJavaPluginComponent().getGiveCrateComponent();
        boolean sendToClaim = !isOnline;
        component.giveCrate(sender, offlinePlayer, crate, amount, sendToClaim);
    }

    @Subcommand("give all")
    @CommandPermission("cr.give.all")
    @CommandCompletion("@crates @range:64")
    @Syntax("<crate> [number]")
    @Description("Give a specific number of crates to all players")
    public void onAll(CommandSender sender, Crate crate, @Default("1") Integer amount) {
        GiveCrateComponent component =
                CorePlugin.getJavaPluginComponent().getGiveCrateComponent();
        component.giveCrateToAllOnlinePlayers(sender, crate, amount);
    }

    @Subcommand("set")
    @CommandPermission("cr.block.set")
    @CommandCompletion("@keys")
    @Syntax("<crate> [world:x,y,z]")
    @Description("Sets a block to register crate actions")
    public void onSetBlock(Player player, Crate crate, @Optional Location location) {
        if (location == null) {
            location = Camera.getTargetBlockLocation(player);
        }

        boolean success = plugin.getBlockCrateRegistrar().setCrate(location, crate);
        String message = CrateAPI.getMessage(success ? "core.successful_block_set" : "core.invalid_block_set");
        Messenger.tell(player, CustomFormat.format(message, crate, player));

        if (LocationUtil.isInSpawnRegion(location)) {
            Messenger.tell(
                    player,
                    String.format(
                            Messages.WARNING_S,
                            "You have set a crate inside a region "
                                    + "with spawn protection! Crates may not work properly within here."));
        }
    }

    @Subcommand("remove all")
    @CommandPermission("cr.remove.block.all")
    @CommandCompletion("@target:x @target:y @target:z @worlds")
    @Description("Removes a block so it doesn't register crate actions")
    public void onRemoveAllBlock(Player player, @Optional Location location) {
        if (location == null) {
            location = Camera.getTargetBlockLocation(player);
        }

        BlockCrateRegistrar registrar = plugin.getBlockCrateRegistrar();

        List<Crate> crates =
                java.util.Optional.ofNullable(registrar.getCrates(location)).orElse(Collections.emptyList());

        if (!crates.isEmpty()) {
            for (Crate c : crates) {
                boolean success = registrar.removeCrate(location, c);
                String message =
                        CrateAPI.getMessage(success ? "core.successful_block_remove" : "core.invalid_block_remove");
                Messenger.tell(player, CustomFormat.format(message, c, player));
            }
        } else {
            Messenger.tell(player, CustomFormat.format(CrateAPI.getMessage("core.invalid_block_location")));
        }
    }

    @Subcommand("remove")
    @CommandPermission("cr.remove.block")
    @CommandCompletion("@AvailableCrateAtLocation @target:x @target:y @target:z @worlds")
    @Syntax("<crate> [world:x,y,z]")
    @Description("Removes a block so it doesn't register crate actions")
    public void onRemoveBlock(Player player, Crate crate, @Optional Location location) {
        if (location == null) {
            location = Camera.getTargetBlockLocation(player);
        }

        boolean success = plugin.getBlockCrateRegistrar().removeCrate(location, crate);
        String message = CrateAPI.getMessage(success ? "core.successful_block_remove" : "core.invalid_block_remove");
        Messenger.tell(player, CustomFormat.format(message, crate, player));
    }

    @Subcommand("admin")
    public class AdminCommand extends BaseCommand {

        @Subcommand("menu")
        @CommandPermission("cr.admin.menu")
        @Description("Open the admin menu for crates")
        public void onAdminMenu(Player player) {
            CorePlugin.getJavaPluginComponent().provideOpenCrateAdminMenu().openListCrateAdminMenu(player);
        }
    }
}
