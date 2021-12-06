package com.jkcoxson.camelmod;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.net.*;
import java.io.*;

public class CamelBotAPI {
    // Class attributes
    static BufferedWriter writer;

    // Constructor
    public CamelBotAPI() {
        // Spawn a new thread to run the connection
        Thread t = new Thread(() -> {
            // Keep trying to connect to the server
            while (true) {
                System.out.println("Connecting to CamelBot...");
                try {
                    // Create a new socket
                    Socket socket = new Socket(Config.host, Config.port); // someone pls explain why this works in Java
                    System.out.println("Connected to CamelBot!");
                    // Create a new input stream
                    InputStream is = socket.getInputStream();
                    // Create a new output stream
                    OutputStream os = socket.getOutputStream();
                    // Create a new buffered reader
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    // Create a new buffered writer
                    writer = new BufferedWriter(new OutputStreamWriter(os));

                    // Send the key
                    writer.write(Config.key + "\n");
                    writer.flush();

                    // Loop while connected
                    while (true) {
                        // Create a new string
                        String line;
                        // Read the next line
                        line = reader.readLine();
                        // If the line is null
                        if (line == null) {
                            // Break out of the loop
                            break;
                        }
                        // If the line is not null
                        else {
                            // Print the line
                            System.out.println(line);
                            // Parse the line as JSON
                            Gson gson = new Gson();
                            JsonObject packet = gson.fromJson(line, JsonObject.class);

                            // Switch the packet type
                            switch (packet.get("type").getAsString()) {
                                case "send": {
                                    String stringData = packet.get("data").getAsString();
                                    JsonObject data = gson.fromJson(stringData, JsonObject.class);
                                    switch (data.get("type").getAsString()) {
                                        case "sendMessage": {
                                            String message = data.get("message").getAsString();
                                            try {
                                                CamelMod.serverReference.getValue().getPlayerManager().getPlayerList().forEach(player -> {
                                                    player.sendSystemMessage(Text.of(message), Util.NIL_UUID);
                                                });
                                            }catch (Exception e) {}
                                        }
                                    }
                                }
                                case "commands": {
                                    // TODO
                                }
                            }
                        }
                    }
                    // Close the buffered reader
                    reader.close();
                    // Close the buffered writer
                    writer.close();
                    // Close the socket
                    socket.close();
                }
                // Catch any exceptions
                catch (Exception e) {
                    // Print the exception
                    e.printStackTrace();
                }

                // Sleep for a second
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        // Start the thread
        t.start();
    }

    public static void sendEvent(String event, JsonObject data) {
        Gson gson = new Gson();
        JsonObject packet = new JsonObject();
        packet.addProperty("type", "event");
        packet.addProperty("event", event);
        packet.add("data", data);

        String packetString = gson.toJson(packet);
        try {
            writer.write(packet.toString() + "\n");
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendSend(String target, JsonObject data) {
        Gson gson = new Gson();
        JsonObject packet = new JsonObject();
        packet.addProperty("type", "send");
        packet.addProperty("target", target);
        packet.add("data", data);

        String packetString = gson.toJson(packet);
        try {
            writer.write(packetString + "\n");
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendId(String id) {
        Gson gson = new Gson();
        JsonObject packet = new JsonObject();
        packet.addProperty("type", "id");
        packet.addProperty("id", id);

        String packetString = gson.toJson(packet);
        try {
            writer.write(packetString + "\n");
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendDebug(String debug) {
        Gson gson = new Gson();
        JsonObject packet = new JsonObject();
        packet.addProperty("type", "debug");
        packet.addProperty("message", debug);

        String packetString = gson.toJson(packet);
        try {
            writer.write(packetString + "\n");
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendError(String error) {
        Gson gson = new Gson();
        JsonObject packet = new JsonObject();
        packet.addProperty("type", "error");
        packet.addProperty("message", error);

        String packetString = gson.toJson(packet);
        try {
            writer.write(packetString + "\n");
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendWarning(String warning) {
        Gson gson = new Gson();
        JsonObject packet = new JsonObject();
        packet.addProperty("type", "warning");
        packet.addProperty("message", warning);

        String packetString = gson.toJson(packet);
        try {
            writer.write(packetString + "\n");
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendInfo(String info) {
        Gson gson = new Gson();
        JsonObject packet = new JsonObject();
        packet.addProperty("type", "info");
        packet.addProperty("message", info);

        String packetString = gson.toJson(packet);
        try {
            writer.write(packetString + "\n");
            writer.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}

