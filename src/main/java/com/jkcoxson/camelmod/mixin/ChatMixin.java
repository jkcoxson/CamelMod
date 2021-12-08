package com.jkcoxson.camelmod.mixin;

import com.google.gson.JsonObject;
import com.jkcoxson.camelmod.CamelBotAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ChatMixin {

    @Shadow public ServerPlayerEntity player;

    @Shadow @Final private MinecraftServer server;

    @Inject(
            method = ("handleMessage"),
            at = {@At("HEAD")},
            cancellable = true
    )
    public void chatMixin(TextStream.Message packet, CallbackInfo ci) {
        String message = packet.getRaw();
        if (!message.startsWith("/")){

            JsonObject messageObject = new JsonObject();
            JsonObject content = new JsonObject();

            content.addProperty("content", message);
            content.addProperty("author", this.player.getEntityName());

            messageObject.add("message", content);
            CamelBotAPI.sendEvent("message", messageObject);
            ci.cancel();
        }

    }


}
