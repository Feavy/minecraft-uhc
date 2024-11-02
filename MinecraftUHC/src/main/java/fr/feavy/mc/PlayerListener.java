package fr.feavy.mc;

import java.awt.Color;
import java.util.Date;

import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import static fr.feavy.mc.Discord.discord;

public class PlayerListener implements Listener {
    private final Config config = Config.get();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        config.setSessionStart(e.getPlayer(), new Date());

        discord().sendTimedEmbed(new EmbedBuilder()
                .setColor(Color.GREEN)
                .setDescription(e.getPlayer().getName() + " s'est connecté !"),
                e.getPlayer()
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        config.saveTotalTime(e.getPlayer());

        discord().sendTimedEmbed(new EmbedBuilder()
                .setColor(Color.RED)
                .setDescription(e.getPlayer().getName() + " s'est déconnecté !"),
                e.getPlayer()
        );
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        discord().sendTimedEmbed(new EmbedBuilder()
                .setColor(Color.PINK)
                .setDescription(e.getPlayer().getDisplayName() + " : " + e.getMessage()),
                e.getPlayer()
        );
    }

    @EventHandler
    public void onPlayerGetAchievement(PlayerAchievementAwardedEvent e) {
        discord().sendTimedEmbed(new EmbedBuilder()
                .setColor(new Color(0x37B4CD))
                .setDescription(e.getPlayer().getDisplayName() + " a obtenu le succès " + e.getAchievement().toString() + " !"),
                e.getPlayer()
        );
    }

    @EventHandler
    public void onPlayerDie(PlayerDeathEvent e) {
        discord().sendTimedEmbed(new EmbedBuilder()
                .setColor(Color.BLACK)
                .setDescription(e.getEntity().getDisplayName()+" est mort :cry:")
                .setFooter(e.getDeathMessage()),
                e.getEntity()
        );
    }

    @EventHandler
    public void onPlayerEnterPortal(PlayerPortalEvent e) {
        World world = e.getTo().getWorld();

        if(world.getEnvironment() == World.Environment.THE_END) {
            generateEndSpawnPlatform(world);
        }
        if(world.getEnvironment() == World.Environment.NETHER && !Config.get().getNether(e.getPlayer())) {
            Config.get().setNether(e.getPlayer());
            discord().sendTimedEmbed(new EmbedBuilder()
                    .setColor(Color.RED)
                    .setDescription(e.getPlayer().getDisplayName()+" est entré dans le Nether !"),
                    e.getPlayer()
            );
        } else if(world.getEnvironment() == World.Environment.THE_END && !Config.get().getEnd(e.getPlayer())) {
            Config.get().setEnd(e.getPlayer());
            discord().sendTimedEmbed(new EmbedBuilder()
                    .setColor(new Color(0x0D0518))
                    .setDescription(e.getPlayer().getDisplayName()+" est entré dans l'End !"),
                    e.getPlayer()
            );
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Player player = e.getEntity().getKiller();
        if(player == null || !(e.getEntity() instanceof EnderDragon)) {
            return;
        }

        discord().sendTimedEmbed(new EmbedBuilder()
                .setColor(new Color(0x7A37CD))
                .setDescription(player.getDisplayName() + " a vaincu l'Ender Dragon ! :dragon_face:"),
                player
        );
    }

    private void generateEndSpawnPlatform(World world) {
        // fill from 98 48 2 to 102 48 -2 with obsidian
        for (int x = 98; x <= 102; x++) {
            for (int z = -2; z <= 2; z++) {
                world.getBlockAt(x, 48, z).setType(org.bukkit.Material.OBSIDIAN);
            }
        }

        // fill from 102 49 -2 to 98 51 2 with air
        for (int x = 98; x <= 102; x++) {
            for (int y = 49; y <= 51; y++) {
                for (int z = -2; z <= 2; z++) {
                    world.getBlockAt(x, y, z).setType(org.bukkit.Material.AIR);
                }
            }
        }
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

