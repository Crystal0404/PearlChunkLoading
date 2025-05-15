package com.github.crystal0404.mods.pearl.mixin;

import com.github.crystal0404.mods.pearl.ChunkUtils;
import com.github.crystal0404.mods.pearl.PearlChunkLoadingMod;
import com.github.crystal0404.mods.pearl.config.PearlSettings;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderPearlEntity.class)
public abstract class EnderPearlEntityMixin extends ThrownItemEntity {
    public EnderPearlEntityMixin(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private long chunkTicketExpiryTicks = 0L;

    // The default value is -1, which means that the pearl has not entered a high-speed motion state
    // Otherwise, it represents the age when the pearl enters a state of high-speed motion
    // If the pearl exits high-speed motion, this value is reset to -1
    @Unique
    private int highSpeedAge = -1;

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    private void getVector(CallbackInfo ci, @Share("i") LocalIntRef i, @Share("j") LocalIntRef j) {
        i.set(ChunkUtils.getSectionCoordFloored(this.getPos().getX()));
        j.set(ChunkUtils.getSectionCoordFloored(this.getPos().getZ()));
    }

    @Inject(
            method = "tick",
            at = @At("TAIL")
    )
    private void loadingChunks(
            CallbackInfo ci,
            @Local(ordinal = 0) Entity entity,
            @Share("i") LocalIntRef i,
            @Share("j") LocalIntRef j
    ) {
        if (!PearlSettings.isEnable()) return;

        boolean bl = this.isHighSpeed();
        if (this.shouldRemoveHighSpeedPearl() && this.highSpeedAge == -1 && bl) {
            this.highSpeedAge = this.age;
        } else if (this.shouldRemoveHighSpeedPearl() && !bl) {
            this.highSpeedAge = -1;
        }
        if (
                this.shouldRemoveHighSpeedPearl()
                        && this.highSpeedAge != -1
                        && this.age - this.highSpeedAge > 40
        ) {
            PearlChunkLoadingMod.LOGGER.warn(
                    "The pearl(own: {}) has been in high speed for a long time and has been removed",
                    entity instanceof ServerPlayerEntity ? entity.getName().getString() : "unknown"
            );
            this.discard();
        }

        if (this.isAlive()) {
            BlockPos blockPos = BlockPos.ofFloored(this.getPos());
            if (
                    (
                            --this.chunkTicketExpiryTicks <= 0L
                                    || i.get() != ChunkUtils.getSectionCoord(blockPos.getX())
                                    || j.get() != ChunkUtils.getSectionCoord(blockPos.getZ())
                    )
                            && entity instanceof ServerPlayerEntity serverPlayerEntity
            ) {
                this.chunkTicketExpiryTicks = serverPlayerEntity.pearl$handleThrownEnderPearl(
                        (EnderPearlEntity) ((Object) this)
                );
                if (!serverPlayerEntity.getServerWorld().entityList.has(this)) {
                    serverPlayerEntity.getServerWorld().entityList.add(this);
                }
            }
        }
    }

    @Unique
    private boolean isHighSpeed() {
        return Math.abs(this.getVelocity().getX()) >= 20d || Math.abs(this.getVelocity().getZ()) >= 20d;
    }

    @Unique
    private boolean shouldRemoveHighSpeedPearl() {
        return PearlSettings.getTime() != -1;
    }
}
