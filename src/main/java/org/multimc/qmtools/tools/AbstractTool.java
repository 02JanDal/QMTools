package org.multimc.qmtools.tools;

import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.multimc.qmlib.QuickMod;
import org.multimc.qmlib.QuickModIOAccess;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractTool {
    public abstract String getDescription();
    public abstract void run(String[] args);

    protected static Map<String, QuickMod> quickmods(OptionSpec<File> option, OptionSet options) {
        List<File> files = options.valuesOf(option);
        if (files == null || files.isEmpty()) {
            files = Arrays.asList(new File(System.getProperty("user.dir")).listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File file, String string) {
                    return string.endsWith(".qm") || string.endsWith(".quickmod") || string.endsWith(".json");
                }
            }));
        }

        Map<String, QuickMod> quickmods = new HashMap<>();
        for (File file : files) {
            try {
                System.out.println("Loading " + file);
                quickmods.put(file.getName(), QuickModIOAccess.read(file));
            } catch (IOException ex) {
                Logger.getLogger(AbstractTool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return quickmods;
    }
}
