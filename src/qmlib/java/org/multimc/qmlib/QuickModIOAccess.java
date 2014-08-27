package org.multimc.qmlib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QuickModIOAccess {
    
    public static QuickMod read(String filename) throws IOException {
        return QuickMod.fromJson(readFile(filename));
    }
    
    public static QuickMod read(File file) throws IOException {
        return read(file.getAbsolutePath());
    }
    
    public static void write(String filename, QuickMod quickmod) throws FileNotFoundException {
        writeFile(filename, quickmod.toJson());
    }

    public static void write(String filename, QuickModIndex index) throws FileNotFoundException {
        writeFile(filename, QuickMod.indexToJson(index));
    }

    private static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
    }
    
    private static void writeFile(String filename, String data) throws FileNotFoundException {
        try (PrintWriter out = new PrintWriter(filename)) {
            out.print(data);
            out.close();
        }
    }
}
