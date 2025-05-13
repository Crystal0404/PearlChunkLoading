package com.github.crystal0404.mods.pearl.mixin.tis;

import carpettisaddition.logging.loggers.ticket.TicketLogger;
import com.github.crystal0404.mods.pearl.ChunkUtils;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(TicketLogger.class)
public abstract class TicketLoggerMixin {
    @Inject(
            method = "getLoggingSuggestions",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
                    ordinal = 1,
                    shift = At.Shift.AFTER,
                    remap = false
            ),
            remap = false
    )
    private void addSuggestions(CallbackInfoReturnable<String[]> cir, @Local(ordinal = 0) List<String> suggestions) {
        suggestions.add(ChunkUtils.ENDER_PEARL.toString());
    }
}
