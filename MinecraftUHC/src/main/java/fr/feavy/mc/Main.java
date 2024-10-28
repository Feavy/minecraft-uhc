package fr.feavy.mc;

import java.awt.Color;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import static fr.feavy.mc.Discord.discord;

public class Main extends JavaPlugin {
    public void onEnable() {
        super.onEnable();
        Config.create(this);
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        for (World world : Bukkit.getServer().getWorlds()) {
            world.setGameRuleValue("naturalRegeneration", "false");
        }

        discord().sendEmbed(
                new EmbedBuilder()
                        .setTitle("Le serveur est ON")
                        .setFooter("uhc.feavy.fr — 1.8.9")
                        .setColor(Color.GREEN)
        );
    }

    public void onDisable() {
        super.onDisable();
        discord().sendEmbed(
                new EmbedBuilder()
                        .setTitle("Le serveur est OFF")
                        .setFooter("uhc.feavy.fr — 1.8.9")
                        .setColor(Color.RED)
        );
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp() && label.equals("reset")) {
            discord().sendEmbed(new EmbedBuilder()
                    .setTitle("Réinitialisation du monde...")
                    .setColor(Color.BLACK)
            );
            Bukkit.getServer().setWhitelist(true);
            Bukkit.getServer().getOnlinePlayers().forEach(p -> p.kickPlayer(""));
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
                Config.get().delete();
                for (World world : Bukkit.getWorlds()) {
                    boolean b = Bukkit.unloadWorld(world, false);
                    System.out.println("Unload " + world.getName() + " : " + b);
                    try {
                        FileUtils.deleteDirectory(world.getWorldFolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Bukkit.getServer().setWhitelist(false);
                Bukkit.getServer().shutdown();
            }, 20*5);
            return true;
        }
        return super.onCommand(sender, command, label, args);
    }
}

