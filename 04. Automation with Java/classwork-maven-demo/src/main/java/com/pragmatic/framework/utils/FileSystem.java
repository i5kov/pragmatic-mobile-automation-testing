package com.pragmatic.framework.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class FileSystem {
    /**
     * Create folder (if it do not exists).
     *
     * @param folderPath path to folder.
     */
    public static void createFolder(String folderPath) {
        java.io.File directory = new java.io.File(folderPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Write string in file.
     *
     * @param text     text to be written in file.
     * @param filePath path to file.
     * @throws FileNotFoundException when fail to find file.
     */
    public static void writeStringToFile(String text, String filePath) throws FileNotFoundException {
        try (PrintStream out = new PrintStream(new FileOutputStream(filePath))) {
            out.print(text);
        }
    }
}
