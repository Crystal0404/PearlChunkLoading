package com.github.crystal0404.mods.pearl.mixin;

import com.github.crystal0404.mods.pearl.config.PearlSettings;
import net.minecraft.entity.Entity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {
    @Inject(
            method = "remove",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/world/ServerWorld;" +
                            "removePlayer(Lnet/minecraft/server/network/ServerPlayerEntity;" +
                            "Lnet/minecraft/entity/Entity$RemovalReason;)V"
            )
    )
    private void removeMixin(ServerPlayerEntity player, CallbackInfo ci) {
        if (PearlSettings.isSave()) {
            player.pearl$getEnderPearls().forEach(
                    enderPearlEntity -> enderPearlEntity.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER)
            );
        }
    }
}
