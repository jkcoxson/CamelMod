package com.jkcoxson.camelmod.mixin;

import com.google.gson.JsonObject;
import com.jkcoxson.camelmod.CamelBotAPI;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class JoinMixin {
    @Inject(at=@At("TAIL"), method="onPlayerConnect")
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        JsonObject json = new JsonObject();
        json.addProperty("player", player.getName().getString());
        CamelBotAPI.sendEvent("playerJoin", json);
    }


    @Inject(at=@At("TAIL"), method="remove")
    public void onPlayerLeave(ServerPlayerEntity player, CallbackInfo ci) {
        JsonObject json = new JsonObject();
        json.addProperty("player", player.getName().getString());
        CamelBotAPI.sendEvent("playerLeave", json);
    }

}
