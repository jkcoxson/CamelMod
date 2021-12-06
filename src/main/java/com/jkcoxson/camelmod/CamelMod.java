// jkcoxson

package com.jkcoxson.camelmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.mutable.MutableObject;


public class CamelMod implements ModInitializer {

    public static Config config;
    public static CamelBotAPI api;
    public static MutableObject<MinecraftServer> serverReference = new MutableObject<>();

    @Override
    public void onInitialize() {
        System.out.println("CamelMod initialized, all hail camels o7");
        ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> serverReference.setValue(minecraftServer));
        config = new Config();
        api = new CamelBotAPI();
        CommandReg command = new CommandReg();
        command.RegisterCommands();

    }
}