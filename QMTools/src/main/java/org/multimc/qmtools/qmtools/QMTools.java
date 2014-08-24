package org.multimc.qmtools.qmtools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.multimc.qmtools.AbstractTool;
import org.multimc.qmtools.qmtool.QMTool;
import org.multimc.qmtools.versionappender.VersionAppender;

public class QMTools {
    public static void main(String[] args) {
        new QMTools().run(args);
    }
    
    private final Map<String, AbstractTool> tools;
    
    private QMTools() {
        this.tools = createTools();
    }
    
    private void run(String[] args) {
        if (args.length == 0) {
            printRootHelp();
            return;
        } else if (!this.tools.containsKey(args[0])) {
            System.err.println("Unknown tool '" + args[0] + "'");
            System.err.println();
            printRootHelp();
            return;
        }
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        this.tools.get(args[0]).run(newArgs);
    }
    
    private Map<String, AbstractTool> createTools() {
        Map<String, AbstractTool> map = new HashMap<>();
        map.put("help", new HelpTool(this));
        map.put("commands", new HelpTool(this));
        map.put("quickmod", new QMTool());
        map.put("append-version", new VersionAppender());
        map.put("format", new FormatterTool());
        map.put("graph", new GraphTool());
        return map;
    }
    
    private void printRootHelp() {
        System.out.println("Usage: java -jar QMTools.jar <tool> [<options>]");
        System.out.println();
        System.out.println("Available tool:");
        for (Map.Entry<String, AbstractTool> entry : tools.entrySet()) {
            System.out.println("\t" + entry.getKey() + "\t" + entry.getValue().getDescription());
        }
    }
    
    private class HelpTool extends AbstractTool {
        private final QMTools parent;
        
        public HelpTool(QMTools parent) {
            this.parent = parent;
        }
        
        @Override
        public String getDescription() {
            return "Lists all available commands and shows help for them";
        }
        
        @Override
        public void run(String[] args) {
            if (args.length == 0) {
                this.parent.printRootHelp();
            } else {
                if (parent.tools.containsKey(args[0])) {
                    parent.tools.get(args[0]).run(new String[]{"--help"});
                } else {
                    System.out.println("Unknown command '" + args[0] + "'");
                }
            }
        }
    }
}
