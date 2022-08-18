package fr.feavy.mc;

import java.awt.Color;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Main extends JavaPlugin {
    public void onEnable() {
        super.onEnable();
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        Discord.discord().sendEmbed(
                new EmbedBuilder()
                        .setTitle("Le serveur est ON")
                        .setFooter("uhc.feavy.fr")
                        .setColor(Color.GREEN)
        );
    }

    public void onDisable() {
        super.onDisable();
        Discord.discord().sendEmbed(
                new EmbedBuilder()
                        .setTitle("Le serveur est OFF")
                        .setFooter("uhc.feavy.fr")
                        .setColor(Color.RED)
        );
    }
}

