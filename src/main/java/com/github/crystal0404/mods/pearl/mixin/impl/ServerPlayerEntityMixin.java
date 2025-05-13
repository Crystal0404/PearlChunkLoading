package com.github.crystal0404.mods.pearl.mixin.impl;

import com.github.crystal0404.mods.pearl.ChunkUtils;
import com.github.crystal0404.mods.pearl.interfaces.ServerPlayerEntityInterface;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.HashSet;
import java.util.Set;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements ServerPlayerEntityInterface {
    @Unique
    private final Set<EnderPearlEntity> enderPearls = new HashSet<>();


    @Override
    public long pearl$handleThrownEnderPearl(EnderPearlEntity enderPearl) {
        if (enderPearl.getWorld() instanceof ServerWorld serverWorld) {
            ChunkPos chunkPos = enderPearl.getChunkPos();
            this.pearl$addEnderPearl(enderPearl);
            serverWorld.resetIdleTimeout();
            return ChunkUtils.addEnderPearlTicket(serverWorld, chunkPos) - 1L;
        } else {
            return 0L;
        }
    }

    @Override
    public void pearl$addEnderPearl(EnderPearlEntity enderPearl) {
        this.enderPearls.add(enderPearl);
    }

    @Override
    public void pearl$removeEnderPearl(EnderPearlEntity enderPearl) {
        this.enderPearls.remove(enderPearl);
    }

    @Override
    public Set<EnderPearlEntity> pearl$getEnderPearls() {
        return this.enderPearls;
    }
}
