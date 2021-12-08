package com.jkcoxson.camelmod.mixin;

import com.google.gson.JsonObject;
import com.jkcoxson.camelmod.CamelBotAPI;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class DeathMixin {
    @Shadow @Nullable public abstract Text getPlayerListName();

    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(DamageSource source, CallbackInfo ci) {
        String name = this.getPlayerListName().getString();
        JsonObject json = new JsonObject();
        json.addProperty("player", this.getPlayerListName().getString());
        json.addProperty("cause", source.getName());
        CamelBotAPI.sendEvent("playerDeath", json);
    }
}
