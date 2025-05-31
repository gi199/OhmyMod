package com.gi199.randomod.editmcc;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemGroups;
import net.minecraft.world.World;

public class BoomTNTEntity extends TntEntity implements BoomTNTEntityExplode {
    private final boolean damageBlocks;

    public BoomTNTEntity(EntityType<? extends TntEntity> type, World world) {
        super(type, world);
        this.damageBlocks = true;
    }

    public BoomTNTEntity(World world, double x, double y, double z, LivingEntity igniter, boolean damageBlocks) {
        super(EntityType.TNT, world);
        this.damageBlocks = damageBlocks;
    }

    @Override
    public void explode() {
        if (!this.getWorld().isClient) {
            this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 4.0F,
                    damageBlocks ? World.ExplosionSourceType.BLOCK : World.ExplosionSourceType.NONE);
        }
    }
}