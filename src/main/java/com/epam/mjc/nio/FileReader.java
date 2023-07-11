package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;


public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder data = new StringBuilder();

        try (RandomAccessFile aFile = new RandomAccessFile(file, "r");
             FileChannel inChannel = aFile.getChannel()) {

            long fileSize = inChannel.size();

            //Create buffer of the file size
            ByteBuffer buffer = ByteBuffer.allocate((int) fileSize);
            inChannel.read(buffer);
            buffer.flip();

            // Verify the file content
            for (int i = 0; i < fileSize; i++) {
                data.append((char) buffer.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] lines = data.toString().split("\n");

        HashMap<String, String> parsedData = new HashMap<>();

        for (String line : lines) {
            String[] parts = line.split(":", 2);
            parsedData.put(parts[0].trim(), parts[1].trim());
        }

        return new Profile(
                parsedData.get("Name"),
                Integer.parseInt(parsedData.get("Age")),
                parsedData.get("Email"),
                Long.parseLong(parsedData.get("Phone"))
        );
    }
}
