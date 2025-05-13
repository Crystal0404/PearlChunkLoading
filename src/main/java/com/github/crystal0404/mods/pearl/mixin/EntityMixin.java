package com.github.crystal0404.mods.pearl.mixin;

import com.github.crystal0404.mods.pearl.interfaces.EnderPearlEntityInterface;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(
            method = "setRemoved",
            at = @At("TAIL")
    )
    private void setRemovedMixin(Entity.RemovalReason reason, CallbackInfo ci) {
        if (
                (Entity) ((Object) this) instanceof EnderPearlEntity enderPearlEntity
                        && reason != Entity.RemovalReason.UNLOADED_WITH_PLAYER
        ) {
            ((EnderPearlEntityInterface) enderPearlEntity).pearl$removeFromOwner();
        }
    }
}
