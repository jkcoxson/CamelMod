package com.jkcoxson.camelmod;

import java.net.*;
import java.io.*;

public class CamelBotAPI {
    // Class attributes

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
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));

                    // Send the key
                    //writer.write(Config.key + "\n");

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
}

