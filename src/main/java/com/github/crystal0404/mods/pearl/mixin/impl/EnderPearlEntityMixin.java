package com.github.crystal0404.mods.pearl.mixin.impl;

import com.github.crystal0404.mods.pearl.interfaces.EnderPearlEntityInterface;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownItemEntity implements EnderPearlEntityInterface {
    public EnderPearlEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void pearl$removeFromOwner() {
        if (this.getOwner() instanceof ServerPlayerEntity serverPlayerEntity) {
            serverPlayerEntity.pearl$removeEnderPearl(
                    (EnderPearlEntity) ((Object) this)
            );
        }
    }
}
