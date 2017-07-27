package me.oxlemonxo.lemonfreedommod.command;

import lombok.Getter;
import me.oxlemonxo.lemonfreedommod.LemonFreedomMod;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

/**
 * Created by Lemon on 7/26/2017.
 */
public abstract class FreedomCommand implements CommandExecutor
{
    CommandSpec spec;
    public static final Text YOU_ARE_OP = Text.of(TextColors.YELLOW, "You are now op!");
    public static final Text YOU_ARE_NOT_OP = Text.of(TextColors.YELLOW, "You are no longer op!");
    public static final Text NOT_FROM_CONSOLE = Text.of("This command may not be used from the console.");
    public static final Text PLAYER_NOT_FOUND = Text.of(TextColors.GRAY, "Player not found!");
    @Getter
    CommandParameters params;
    LemonFreedomMod pl = LemonFreedomMod.plugin();


    public FreedomCommand()
    {
        this.params = getClass().getAnnotation(CommandParameters.class);
        if(params == null)
        {
            pl.getLogger().warn("Ignoring command usage for command " + getClass().getSimpleName() + ". Command is not annotated!");
        }
        spec = CommandSpec.builder().description(Text.of(params.description())).executor(this).build();
    }
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        boolean result = run(src, args);
        if(result)
        {
            return CommandResult.success();
        }
        return CommandResult.empty();
    }
    protected abstract boolean run(final CommandSource src, final CommandContext args);


}
