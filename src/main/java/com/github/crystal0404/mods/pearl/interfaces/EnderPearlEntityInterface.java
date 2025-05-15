package com.github.crystal0404.mods.pearl.interfaces;

public interface EnderPearlEntityInterface {
    default void pearl$removeFromOwner() {
        throw new AssertionError();
    }
}
