package com.jkcoxson.camelmod.mixin;

import com.google.gson.JsonObject;
import com.jkcoxson.camelmod.CamelBotAPI;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(MinecraftServer.class)
public class LogMixin {

    @Inject(at = @At("HEAD"), method = "sendSystemMessage")
    public void sendMessage(Text text, UUID uUID, CallbackInfo ci) {
        JsonObject messageObject = new JsonObject();
        JsonObject content = new JsonObject();
        content.addProperty("log", text.getString());


        messageObject.add("data", content);
        CamelBotAPI.sendEvent("log", messageObject);
    }


}