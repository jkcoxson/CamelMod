package com.jkcoxson.camelmod.client;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class CamelmodClient {
    // Stop because this is a server side mod
    public CamelmodClient() {
        throw new RuntimeException("Tried to instantiate a client-only class on a server mod!");
    }
}
