package com.github.crystal0404.mods.pearl.mixin;

import com.github.crystal0404.mods.pearl.config.PearlSave;
import com.github.crystal0404.mods.pearl.interfaces.ServerPlayerEntityInterface;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(
            method = "writeCustomDataToNbt",
            at = @At("HEAD")
    )
    private void writeCustomDataToNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) ((Object) this);
        try {
            PearlSave.save(serverPlayerEntity, (ServerPlayerEntityInterface) serverPlayerEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Inject(
            method = "readCustomDataFromNbt",
            at = @At("TAIL")
    )
    private void readCustomDataFromNbtMixin(NbtCompound nbt, CallbackInfo ci) {
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) ((Object) this);
        try {
            PearlSave.loadEnderPearls(serverPlayerEntity, (ServerPlayerEntityInterface) serverPlayerEntity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
