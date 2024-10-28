package fr.feavy.mc;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Config {
    private static Config INSTANCE;

    public static void create(JavaPlugin plugin) {
        INSTANCE = new Config(plugin);
    }

    public static Config get() {
        return INSTANCE;
    }

    private final Map<Player, Date> sessionStarts = Collections.synchronizedMap(new HashMap<>());

    private final FileConfiguration configuration;
    private final File file;

    public Config(JavaPlugin plugin) {
        this.file = this.init(plugin);
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

    public File init(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), "uhc.yml");
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file;
    }

    public void setSessionStart(Player player, Date date) {
        sessionStarts.put(player, date);
        if(!getPlayerSection(player).isSet("total_time")) {
            getPlayerSection(player).set("total_time", 0);
        }
        save();
    }

    public Date getSessionStartDate(Player player) {
        return sessionStarts.get(player);
    }

    public void saveTotalTime(Player player) {
        // pb la session_start est pas supprimée
        long totalTime = getTotalTime(player).toMillis();
        sessionStarts.remove(player);
        getPlayerSection(player).set("total_time", totalTime);
        save();
    }

    public Duration getTotalTime(Player player) {
        long totalTime = getPlayerSection(player).getLong("total_time");

        if(sessionStarts.containsKey(player)) {
            long sessionStart = sessionStarts.get(player).getTime();
            long currentTime = new Date().getTime();
            totalTime += (currentTime - sessionStart);
        }

        return Duration.ofMillis(totalTime);
    }

    public void setNether(Player player) {
        getPlayerSection(player).set("nether", true);
        save();
    }

    public boolean getNether(Player player) {
        return getPlayerSection(player).getBoolean("nether");
    }

    public void setEnd(Player player) {
        getPlayerSection(player).set("end", true);
        save();
    }

    public boolean getEnd(Player player) {
        return getPlayerSection(player).getBoolean("end");
    }

    private ConfigurationSection getPlayerSection(Player player) {
        ConfigurationSection playersSection = configuration.getConfigurationSection("players");
        if(playersSection == null) {
            playersSection = configuration.createSection("players");
        }
        ConfigurationSection playerSection = playersSection.getConfigurationSection(player.getUniqueId().toString());
        if(playerSection == null) {
            playerSection = playersSection.createSection(player.getUniqueId().toString());
        }
        return playerSection;
    }

    public boolean delete() {
        return this.file.delete();
    }

    public void save() {
        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
