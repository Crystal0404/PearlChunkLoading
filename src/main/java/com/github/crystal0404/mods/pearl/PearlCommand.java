package com.github.crystal0404.mods.pearl;

import com.github.crystal0404.mods.pearl.config.PearlSettings;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class PearlCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                    CommandManager.literal("pearl")
                            .requires(source -> source.hasPermissionLevel(1))
                            .then(
                                    CommandManager.argument("enable", BoolArgumentType.bool())
                                            .executes(PearlCommand::enable)
                            )
                            .then(
                                    CommandManager.literal("time")
                                            .then(
                                                    CommandManager.argument("time", IntegerArgumentType.integer(-1))
                                                            .suggests((context, builder) -> {
                                                                builder.suggest(-1);
                                                                builder.suggest(40);
                                                                return builder.buildFuture();
                                                            })
                                                            .executes(PearlCommand::time)
                                            )
                            )
                            .then(
                                    CommandManager.literal("expiryTicks")
                                            .then(
                                                    CommandManager.argument("expiryTicks", IntegerArgumentType.integer(1))
                                                            .suggests((contest, builder) -> {
                                                                builder.suggest(40);
                                                                return builder.buildFuture();
                                                            })
                                                            .executes(PearlCommand::expiryTicks)
                                            )
                            )
                            .then(
                                    CommandManager.literal("save")
                                            .then(
                                                    CommandManager.argument("save", BoolArgumentType.bool())
                                                            .executes(PearlCommand::save)
                                            )
                            )
            );
        });
    }

    private static int enable(CommandContext<ServerCommandSource> context) {
        boolean enable = BoolArgumentType.getBool(context, "enable");
        PearlSettings.setEnable(enable);
        context.getSource().sendFeedback(
                () -> Text.literal(enable ? "Enable mod PearlChunkLoading" : "Disable mod PearlChunkLoading"),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int time(CommandContext<ServerCommandSource> context) {
        int time = IntegerArgumentType.getInteger(context, "time");
        PearlSettings.setTime(time);
        context.getSource().sendFeedback(
                () -> Text.literal(
                        time == -1 ? "Disables the deletion of pearls that have been moving at high speed for a long time" : String.format(
                                "Successfully set the pearl will disappear after %d ticks of high speed movement",
                                time
                        )
                ),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int expiryTicks(CommandContext<ServerCommandSource> context) {
        int expiryTicks = IntegerArgumentType.getInteger(context, "expiryTicks");
        PearlSettings.setExpiryTicks(expiryTicks);
        context.getSource().sendFeedback(
                () -> Text.literal(
                        String.format(
                                "Successfully set the pearl loading chunks time to %d tick",
                                expiryTicks
                        )
                ),
                true
        );
        return Command.SINGLE_SUCCESS;
    }

    private static int save(CommandContext<ServerCommandSource> context) {
        boolean save = BoolArgumentType.getBool(context, "save");
        PearlSettings.setSave(save);
        context.getSource().sendFeedback(
                () -> Text.literal(save ? "Enable pearl save" : "Disable pearl save"),
                true
        );
        return Command.SINGLE_SUCCESS;
    }
}
