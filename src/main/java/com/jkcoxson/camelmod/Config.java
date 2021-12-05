package com.jkcoxson.camelmod;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Config {
    // Attributes
    public static String host = "localhost";
    public static int port = 8888;
    public static String key = "";

    public static Path configPath;

    // Constructor
    public Config() {
        // Load the config.json file from fabric config folder
        configPath = FabricLoader.getInstance().getConfigDir().resolve("CamelMod");
        // Create the folder if it doesn't exist
        if (!configPath.toFile().exists()) {
            boolean success = configPath.toFile().mkdir();
            if (!success) {
                throw new RuntimeException("Failed to create config folder");
            }
        }
        // Load the config.json file
        Config.load( configPath.resolve("config.json"));

    }

    private static void load(Path path) {
        // Determine if the file exists
        if (path.toFile().exists()) {
            // Read the file in as a string
            StringBuilder file = new StringBuilder();

            // Read the file
            try {
                Scanner scanner = new Scanner(path.toFile());
                while (scanner.hasNextLine()) {
                    file.append(scanner.nextLine());
                }
                scanner.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Parse the file
            Gson gson = new Gson();
            ConfigTemplate readConfig = gson.fromJson(file.toString(), ConfigTemplate.class);
            host = readConfig.getHost();
            port = readConfig.getPort();
            key = readConfig.getKey();
            // Dab on all the haters

        } else {
            System.out.println("Config file not found, creating new one");
            ConfigTemplate configTemplate = new ConfigTemplate();
            configTemplate.setHost(host);
            configTemplate.setPort(port);
            configTemplate.setKey(key);
            save(configTemplate, path);
        }
    }

    private static void save(ConfigTemplate config, Path path) {
        Gson gson = new Gson();
        String json = gson.toJson(config);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path.toFile()));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
