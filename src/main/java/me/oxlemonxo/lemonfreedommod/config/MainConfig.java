package me.oxlemonxo.lemonfreedommod.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.List;

import me.oxlemonxo.lemonfreedommod.LemonFreedomMod;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.apache.commons.io.FileUtils;



public class MainConfig
{

    public static final String CONFIG_FILENAME = "config.conf";
    //
    private final EnumMap<MainConfigEntry, Object> entries;
    private LemonFreedomMod pl;


    public MainConfig(LemonFreedomMod plugin)
    {

        entries = new EnumMap<>(MainConfigEntry.class);
        pl = plugin;


        try
        {
            try
            {

                    ConfigurationLoader<CommentedConfigurationNode> loader =
                            HoconConfigurationLoader.builder().setPath(new File(pl.getContainer().getAsset(CONFIG_FILENAME).get().getUrl().getPath()).toPath()).build();
                    ConfigurationNode rootNode = loader.load();
                    for (MainConfigEntry entry : MainConfigEntry.values())
                    {
                        entries.put(entry, rootNode.getValue(entry.getConfigName()));
                    }
            }
            catch (IOException ex)
            {
                pl.getLogger().error(ex.getMessage());
            }

            copyDefaultConfig(getConfigFile());

            load();
        }
        catch (Exception ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
    }

    public void load()
    {
        try
        {

            for (MainConfigEntry entry : MainConfigEntry.values())
            {
                String path = entry.getConfigName();
                ConfigurationLoader<CommentedConfigurationNode> loader =
                        HoconConfigurationLoader.builder().setPath(getConfigFile().toPath()).build();
                ConfigurationNode rootNode = loader.load();
                if (!(rootNode.getNode(path) == null))
                {
                    Object value = rootNode.getNode(path);
                    if (value == null || entry.getType().isAssignableFrom(value.getClass()))
                    {
                        entries.put(entry, value);
                    }
                    else
                    {
                        pl.getLogger().warn("Value for " + entry.getConfigName() + " is of type "
                                + value.getClass().getSimpleName() + ". Needs to be " + entry.getType().getSimpleName() + ". Using default value.");
                    }
                }
                else
                {
                    pl.getLogger().warn("Missing configuration entry " + entry.getConfigName() + ". Using default value.");
                }
            }
        }
        catch (IOException ex)
        {
            pl.getLogger().error("Failed to load configuration!" + ex.getMessage());
        }
    }

    private File getConfigFile()
    {
        return new File(pl.getConfigDir().toAbsolutePath().toString() + "/" + CONFIG_FILENAME);
    }

    public String getString(MainConfigEntry entry)
    {
        try
        {
            return get(entry, String.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
        return null;
    }

    public void setString(MainConfigEntry entry, String value)
    {
        try
        {
            set(entry, value, String.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
    }

    public Double getDouble(MainConfigEntry entry)
    {
        try
        {
            return get(entry, Double.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
        return null;
    }

    public void setDouble(MainConfigEntry entry, Double value)
    {
        try
        {
            set(entry, value, Double.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
    }

    public Boolean getBoolean(MainConfigEntry entry)
    {
        try
        {
            return get(entry, Boolean.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
        return null;
    }

    public void setBoolean(MainConfigEntry entry, Boolean value)
    {
        try
        {
            set(entry, value, Boolean.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
    }

    public Integer getInteger(MainConfigEntry entry)
    {
        try
        {
            return get(entry, Integer.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
        return null;
    }

    public void setInteger(MainConfigEntry entry, Integer value)
    {
        try
        {
            set(entry, value, Integer.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
    }

    public List getList(MainConfigEntry entry)
    {
        try
        {
            return get(entry, List.class);
        }
        catch (IllegalArgumentException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
        return null;
    }

    public <T> T get(MainConfigEntry entry, Class<T> type) throws IllegalArgumentException
    {
        Object value = entries.get(entry);
        try
        {
            return type.cast(value);
        }
        catch (ClassCastException ex)
        {
            throw new IllegalArgumentException(entry.name() + " is not of type " + type.getSimpleName());
        }
    }

    public <T> void set(MainConfigEntry entry, T value, Class<T> type) throws IllegalArgumentException
    {
        if (!type.isAssignableFrom(entry.getType()))
        {
            throw new IllegalArgumentException(entry.name() + " is not of type " + type.getSimpleName());
        }
        if (value != null && !type.isAssignableFrom(value.getClass()))
        {
            throw new IllegalArgumentException("Value is not of type " + type.getSimpleName());
        }
        entries.put(entry, value);
    }

    private void copyDefaultConfig(File targetFile)
    {
        if (targetFile.exists())
        {
            return;
        }

        pl.getLogger().info("Installing default configuration file template: " + targetFile.getPath());

        try
        {
            try (InputStream defaultConfig = getDefaultConfig())
            {
                FileUtils.copyInputStreamToFile(defaultConfig, targetFile);
            }
        }
        catch (IOException ex)
        {
            pl.getLogger().error(ex.getMessage());
        }
    }

    private InputStream getDefaultConfig() throws IOException
    {
        return pl.getContainer().getAsset(CONFIG_FILENAME).get().getUrl().openStream();
    }

}
