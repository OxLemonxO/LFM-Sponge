package me.oxlemonxo.lemonfreedommod;

/**
 * Created by Lemon on 7/26/2017.
 */
import net.minecraft.server.MinecraftServer;
import org.spongepowered.api.Sponge;

public class ServerInterface
{
    public static void setOnlineMode(boolean state)
  {
      final MinecraftServer server = (MinecraftServer) Sponge.getServer();
      server.setOnlineMode(state);
  }

}
