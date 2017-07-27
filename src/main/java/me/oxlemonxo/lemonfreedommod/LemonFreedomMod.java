package me.oxlemonxo.lemonfreedommod;


/**
 * Created by Lemon on 7/24/2017.
 */

import com.google.inject.Inject;
import lombok.Getter;
import me.oxlemonxo.lemonfreedommod.config.MainConfig;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;

@Plugin(id = "lemonfreedommod", name = "LemonFreedomMod", version = "1.0")
public class LemonFreedomMod
{
    @Inject @Getter
    private Logger logger;
    @Inject  @DefaultConfig(sharedRoot = false) @Getter
    private Path configDir;
    @Inject @Getter
    private PluginContainer container;
    public MainConfig config;

    @Listener
    public void onServerPrestart(GameStartingServerEvent event) {
        config = new MainConfig(this);
        config.load();

    }
    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        
    }


    public static LemonFreedomMod plugin()
    {
        for(PluginContainer plugin : Sponge.getPluginManager().getPlugins())
        {
            if(plugin.getName().equals("LemonFreedomMod"))
            {
                return (LemonFreedomMod) plugin.getInstance().get();
            }
        }
        return null;
    }
}
