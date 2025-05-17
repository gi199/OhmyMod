package com.gi199.randomod.command;

import com.mojang.brigadier.Command;
import net.minecraft.server.command.ServerCommandSource;

public class CommandClasses {
    Command<ServerCommandSource> command = context -> {
        ServerCommandSource source = context.getSource();
        return 0;
    };
}
