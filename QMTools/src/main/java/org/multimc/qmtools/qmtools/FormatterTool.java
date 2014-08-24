package org.multimc.qmtools.qmtools;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.multimc.qmtools.AbstractTool;
import org.multimc.qmtools.QuickMod;
import org.multimc.qmtools.QuickModIOAccess;

public class FormatterTool extends AbstractTool {

    @Override
    public String getDescription() {
        return "Formats a QuickMod file";
    }

    @Override
    public void run(String[] args) {
        if (args.length < 1) {
            System.err.println("Expecting at least one argument");
            return;
        }
        try {
            for (String arg : args) {
                System.out.println("Formatting " + arg);
                QuickMod mod = QuickModIOAccess.read(arg);
                QuickModIOAccess.write(arg, mod);
            }
        } catch (IOException ex) {
            Logger.getLogger(FormatterTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
