package com.github.crystal0404.mods.pearl.interfaces;

import net.minecraft.entity.projectile.thrown.EnderPearlEntity;

import java.util.Set;

public interface ServerPlayerEntityInterface {
    default long pearl$handleThrownEnderPearl(EnderPearlEntity enderPearl) {
        throw new AssertionError();
    }

    default void pearl$addEnderPearl(EnderPearlEntity enderPearl) {
        throw new AssertionError();
    }

    default void pearl$removeEnderPearl(EnderPearlEntity enderPearl) {
        throw new AssertionError();
    }

    default Set<EnderPearlEntity> pearl$getEnderPearls() {
        throw new AssertionError();
    }
}
