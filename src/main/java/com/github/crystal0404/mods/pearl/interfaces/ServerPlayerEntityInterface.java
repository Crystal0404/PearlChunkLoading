package com.github.crystal0404.mods.pearl.interfaces;

import net.minecraft.entity.projectile.thrown.EnderPearlEntity;

import java.util.Set;

public interface ServerPlayerEntityInterface {
    long pearl$handleThrownEnderPearl(EnderPearlEntity enderPearl);

    void pearl$addEnderPearl(EnderPearlEntity enderPearl);

    void pearl$removeEnderPearl(EnderPearlEntity enderPearl);

    Set<EnderPearlEntity> pearl$getEnderPearls();
}
