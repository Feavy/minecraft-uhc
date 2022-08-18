package fr.feavy.mc;

import java.awt.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Discord.discord().sendEmbed(new EmbedBuilder().setColor(Color.GREEN).setDescription(e.getPlayer().getName() + " vient de se connecter !"));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Discord.discord().sendEmbed(new EmbedBuilder().setColor(Color.RED).setDescription(e.getPlayer().getName() + " vient de se déconnecter !"));
    }
}

