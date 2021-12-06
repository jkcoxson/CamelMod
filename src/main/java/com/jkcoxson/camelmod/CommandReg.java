// jkcoxson

package com.jkcoxson.camelmod;
import com.google.gson.JsonObject;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.oroarmor.json.brigadier.JsonToBrigadier;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.apache.commons.lang3.mutable.MutableObject;

import java.io.*;
import java.nio.file.Path;

public class CommandReg {
    static final MutableObject<MinecraftServer> serverReference = new MutableObject<>();
    public void RegisterCommands(){
        ServerLifecycleEvents.SERVER_STARTED.register(serverReference::setValue);
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {


        });
    }
    public static int camelCommand(CommandContext<ServerCommandSource> ctx){
//        JsonObject toSend = new JsonObject();
//        toSend.addProperty("packet","command");
//        toSend.addProperty("command",ctx.getInput());
//        try{
//            toSend.addProperty("sender",ctx.getSource().getPlayer().getDisplayName().getString());
//        }catch (Exception e) {
//            // This means the console ran it
//            toSend.addProperty("sender","console");
//        }
//        tcamelp.Yeet(toSend.toString());
          return 1;
    }
    public static boolean hasAdmin(ServerCommandSource source){
        try{
            source.getPlayer();
            return false;
        } catch (Exception e){
            return true;
        }

    }
}
