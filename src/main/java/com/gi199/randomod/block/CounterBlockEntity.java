package com.gi199.randomod.block;

import com.gi199.randomod.RandoMod;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CounterBlockEntity extends BlockEntity {
    public static final BlockEntityType<CounterBlockEntity> COUNTER_BLOCK_ENTITY =
            register("counter", CounterBlockEntity::new, BlockClasses.COUNTER_BLOCK);
    public CounterBlockEntity(BlockPos pos, BlockState state) {
        super(CounterBlockEntity.COUNTER_BLOCK_ENTITY, pos, state);
    }

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.of(RandoMod.MOD_ID, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }
    private int clicks = 0;
    private int ticksSinceLast = 0;
    public int getClicks() {
        return clicks;
    }

    public void incrementClicks() {
        clicks++;
        markDirty();
        if (ticksSinceLast < 10) return;
        ticksSinceLast = 0;
    }
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        nbt.putInt("clicks", clicks);

        super.writeNbt(nbt, registryLookup);
    }
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        clicks = nbt.getInt("clicks");
    }
    public static void tick(World world, BlockPos blockPos, BlockState lockState, CounterBlockEntity entity) {
        entity.ticksSinceLast++;
    }

}