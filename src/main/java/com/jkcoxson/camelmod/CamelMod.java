// jkcoxson

package com.jkcoxson.camelmod;

import net.fabricmc.api.ModInitializer;


public class CamelMod implements ModInitializer {

    public static Config config;
    public static CamelBotAPI api;

    @Override
    public void onInitialize() {
        System.out.println("CamelMod initialized, all hail camels o7");
        config = new Config();
        api = new CamelBotAPI();
        CommandReg command = new CommandReg();
        command.RegisterCommands();
    }
}