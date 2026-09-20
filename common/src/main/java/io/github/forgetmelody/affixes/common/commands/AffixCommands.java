package io.github.forgetmelody.affixes.common.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import io.github.forgetmelody.affixes.common.api.Affix;
import io.github.forgetmelody.affixes.common.api.AffixHolder;
import io.github.forgetmelody.affixes.common.registries.Registries;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

import java.util.Collection;

public final class AffixCommands {
    private AffixCommands() {
    }

    public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext buildContext) {
        return Commands.literal("affix").requires(source -> source.hasPermission(2))
                .then(Commands.literal("set")
                        .then(Commands.argument("affix", ResourceArgument.resource(buildContext, Registries.AFFIX))
                                .then(Commands.argument("level", IntegerArgumentType.integer(0, 255))
                                        .then(Commands.argument("entities", EntityArgument.entities())
                                                .executes(context -> set(context.getSource(), ResourceArgument.getResource(context, "affix", Registries.AFFIX), IntegerArgumentType.getInteger(context, "level"), EntityArgument.getEntities(context, "entities")))
                                        )
                                )
                        )
                )
                .then(Commands.literal("remove")
                        .then(Commands.argument("affix", ResourceArgument.resource(buildContext, Registries.AFFIX))
                                .then(Commands.argument("entities", EntityArgument.entities())
                                        .executes(context -> remove(context.getSource(), ResourceArgument.getResource(context, "affix", Registries.AFFIX), EntityArgument.getEntities(context, "entities")))
                                )
                        )
                );
    }

    public static int remove(CommandSourceStack source, Holder<Affix> affix, Collection<? extends Entity> entities) {
        int i = entities.size();
        for (Entity entity : entities) {
            AffixHolder.get(entity).update(mutable -> mutable.remove(affix));
        }
        if (i == 0) {
            source.sendFailure(Component.literal("未找到实体"));
        } else if (i == 1) {
            source.sendSuccess(() -> Component.literal("已经从")
                    .append(entities.stream().findFirst().orElseThrow().getDisplayName())
                    .append(Component.literal("移除"))
                    .append(affix.value().description()), false);
        } else {
            source.sendSuccess(() -> Component.literal("已经移除" + i + "个实体的").append(affix.value().description()), false);
        }
        return i;
    }

    public static int set(CommandSourceStack source, Holder<Affix> affix, int level, Collection<? extends Entity> entities) {
        int i = entities.size();
        for (Entity entity : entities) {
            AffixHolder.get(entity).update(mutable -> mutable.set(affix, level));
        }

        if (i == 0) {
            source.sendFailure(Component.literal("未找到实体"));
        } else if (i == 1) {
            source.sendSuccess(() -> Component.literal("已经为")
                    .append(entities.stream().findFirst().orElseThrow().getDisplayName())
                    .append(Component.literal("添加"))
                    .append(affix.value().description()), false);
        } else {
            source.sendSuccess(() -> Component.literal("已经为" + i + "个实体添加了").append(affix.value().description()), false);
        }
        return i;
    }
}
