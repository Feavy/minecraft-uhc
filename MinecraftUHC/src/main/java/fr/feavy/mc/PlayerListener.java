package fr.feavy.mc;

import java.awt.Color;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import static fr.feavy.mc.Discord.discord;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        discord().sendEmbed(new EmbedBuilder()
                .setColor(Color.GREEN)
                .setDescription(e.getPlayer().getName() + " s'est connecté !")
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        discord().sendEmbed(new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription(e.getPlayer().getName() + " s'est déconnecté !")
        );
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        discord().sendEmbed(new EmbedBuilder()
                .setColor(Color.PINK)
                .setDescription(e.getPlayer().getDisplayName() + " : " + e.getMessage())
        );
    }

    @EventHandler
    public void onPlayerGetAchievement(PlayerAchievementAwardedEvent e) {
        discord().sendEmbed(new EmbedBuilder()
                .setColor(new Color(0x37B4CD))
                .setDescription(e.getPlayer().getDisplayName() + " a obtenu le succès " + e.getAchievement().toString() + " !")
        );
    }

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent e) {
        discord().sendEmbed(new EmbedBuilder()
                .setColor(Color.BLACK)
                .setDescription(e.getEntity().getDisplayName()+" est mort :cry:")
                .setFooter(e.getDeathMessage())
        );
    }

//    @EventHandler
//    public void onPlayerBreakBlock(BlockBreakEvent e) {
//        discord().sendEmbed(new EmbedBuilder()
//                .setColor(Color.LIGHT_GRAY)
//                .setDescription(e.getPlayer().getDisplayName()+" a cassé un block de "+e.getBlock().getType().toString()+" !")
//        );
//    }
//
//    @EventHandler
//    public void onPlayerKillEntity(EntityDeathEvent e) {
//        Player killer = e.getEntity().getKiller();
//        if(killer == null) {
//            return;
//        }
//        discord().sendEmbed(new EmbedBuilder()
//                .setColor(Color.LIGHT_GRAY)
//                .setDescription(killer.getDisplayName()+" a tué un "+e.getEntity().getName()+" !")
//        );
//    }
}

