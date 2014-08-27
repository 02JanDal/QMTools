package org.multimc.qmtools.tools;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.multimc.qmlib.QuickMod;
import org.multimc.qmlib.QuickModIOAccess;

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
