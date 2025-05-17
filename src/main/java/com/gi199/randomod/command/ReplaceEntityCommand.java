package com.gi199.randomod.command;

import com.mojang.brigadier.arguments.FloatArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.Vec3ArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class ReplaceEntityCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("replaceentity")
                .requires(src -> src.hasPermissionLevel(2))
                .then(CommandManager.argument("pos", Vec3ArgumentType.vec3())
                        .then(CommandManager.argument("entity", EntitySummonArgumentType.entitySummon(registryAccess))
                                .then(CommandManager.argument("radius", FloatArgumentType.floatArg(0.0f))
                                        .executes(context -> {
                                            // 参数解析
                                            final Vec3d center = Vec3ArgumentType.getVec3(context, "pos");
                                            final EntityType<?> entityType = EntitySummonArgumentType.getEntitySummon(context, "entity").value();
                                            final float radius = FloatArgumentType.getFloat(context, "radius");

                                            // 执行替换
                                            final ServerCommandSource source = context.getSource();
                                            final ServerWorld world = source.getWorld();
                                            int replaced = 0;
                                            final double radiusSq = radius * radius;

                                            // 优化实体遍历逻辑
                                            for (Entity entity : world.getEntitiesByClass(Entity.class,
                                                    world.getWorldBorder().asBox, e -> true)) {

                                                if (entity.getPos().squaredDistanceTo(center) > radiusSq) continue;

                                                // 保存原位置和角度
                                                final Vec3d pos = entity.getPos();
                                                final float yaw = entity.getYaw();
                                                final float pitch = entity.getPitch();

                                                // 移除旧实体
                                                entity.discard();

                                                // 生成新实体
                                                Entity newEntity = entityType.create(world);
                                                if (newEntity != null) {
                                                    newEntity.setPosition(pos);
                                                    newEntity.setYaw(yaw);
                                                    newEntity.setPitch(pitch);
                                                    if (world.spawnEntity(newEntity)) {
                                                        replaced++;
                                                    }
                                                }
                                            }

                                            // 反馈结果
                                            source.sendMessage(Text.literal("成功替换 " + replaced + " 个实体"));
                                            return replaced;
                                        })
                                )))));
    }
}