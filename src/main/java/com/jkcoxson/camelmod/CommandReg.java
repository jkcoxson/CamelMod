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
            dispatcher.register(CommandManager.literal("uncamel").executes(context -> {
                tcamelp.Disconnect();
                return 1;

            }));
            dispatcher.register(CommandManager.literal("register").executes(context -> {
                String toParse = """
                        {
                          "name": "test",
                          "argument" : {
                            "type": "brigadier:literal"
                          },
                          "children": [
                            {
                              "name": "value",
                              "argument": {
                                "type": "brigadier:integer",
                                "min": 0,
                                "max": 1
                              },
                              "executes": "com.jkcoxson.camelmod.CommandReg::camelCommand"
                            }
                          ]
                        }""";
                LiteralArgumentBuilder<ServerCommandSource> builder = (LiteralArgumentBuilder<ServerCommandSource>)JsonToBrigadier.parse(toParse,ServerCommandSource.class);
                dispatcher.register(builder);
                serverReference.getValue().getPlayerManager().getPlayerList().forEach(serverPlayerEntity -> serverReference.getValue().getCommandManager().sendCommandTree(serverPlayerEntity));
                return 1;
            }));
            dispatcher.register(CommandManager.literal("unregister").executes(context -> {
                CommandRemoval.removeCommand(serverReference.getValue(),"test");
                serverReference.getValue().getPlayerManager().getPlayerList().forEach(serverPlayerEntity -> serverReference.getValue().getCommandManager().sendCommandTree(serverPlayerEntity));
                return 1;
            }));
            String toParse = """
                    {
                        "name": "camelkey",
                        "argument" : {
                          "type": "brigadier:literal"
                        },
                        "requires":"com.jkcoxson.camelmod.CommandReg::hasAdmin",
                        "children": [
                          {
                            "name": "value",
                            "argument": {
                              "type": "brigadier:string"
                            },
                            "executes": "com.jkcoxson.camelmod.CommandReg::saveKey"
                          }
                        ]
                    }""";
            LiteralArgumentBuilder<ServerCommandSource> builder = (LiteralArgumentBuilder<ServerCommandSource>)JsonToBrigadier.parse(toParse,ServerCommandSource.class);
            dispatcher.register(builder);

        });
    }
    public static int camelCommand(CommandContext<ServerCommandSource> ctx){
        JsonObject toSend = new JsonObject();
        toSend.addProperty("packet","command");
        toSend.addProperty("command",ctx.getInput());
        try{
            toSend.addProperty("sender",ctx.getSource().getPlayer().getDisplayName().getString());
        }catch (Exception e) {
            // This means the console ran it
            toSend.addProperty("sender","console");
        }
        tcamelp.Yeet(toSend.toString());
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
    public static int saveKey(CommandContext ctx){
        Path dataFolder = FabricLoader.getInstance().getConfigDir().resolve("CamelMod");

        // Read the key
        File keyfile = dataFolder.resolve("key.txt").toFile();

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(keyfile));
            bw.write(ctx.getInput().split(" ")[1]);
            bw.close();
            tcamelp.Disconnect();
        }catch(Exception e){
            System.out.println(e);
        }
        return 1;
    }
}
