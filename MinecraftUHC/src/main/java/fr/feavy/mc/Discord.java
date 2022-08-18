package fr.feavy.mc;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Discord {
    public static final Discord INSTANCE = new Discord();
    private final String TOKEN = System.getenv("MINECRAFT_TOKEN");
    private final String CHANNELD_ID = System.getenv("MINECRAFT_CHANNEL");
    private final DiscordApi discord = new DiscordApiBuilder().setToken(this.TOKEN).login().join();
    private final TextChannel textChannel = this.discord.getTextChannelById(this.CHANNELD_ID).get();

    public static Discord get() {
        return INSTANCE;
    }

    public static Discord discord() {
        return INSTANCE;
    }

    private Discord() {
    }

    public void sendEmbed(EmbedBuilder embed) {
        this.textChannel.sendMessage(embed);
    }
}

