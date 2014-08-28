package org.multimc.qmtools.tools;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDOT;
import org.multimc.qmlib.*;

public class GraphTool extends AbstractTool {
    
    public static void main(String[] args) {
        new GraphTool().run(args);
    }

    @Override
    public String getDescription() {
        return "Generates a graph over the dependencies of a list of QuickMods";
    }

    @Override
    public void run(String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpecBuilder displayOption = parser.accepts("display", "Pass this option to show the graph");
        OptionSpec<String> outOption = parser.accepts("out", "Pass this option to write the resulting image to the given file").withRequiredArg().ofType(String.class);
        OptionSpec<String> mcVersionOption = parser.accepts("mcVersion", "Choose the Minecraft version to use").withRequiredArg().ofType(String.class).defaultsTo("1.7.10");
        OptionSpec<File> filesOption = parser.nonOptions("file").describedAs("QuickMod files to use. Leave empty to use all in current directory.").ofType(File.class);
        OptionSet options = parser.parse(args);

        Collection<QuickMod> quickmods = AbstractTool.quickmods(filesOption, options).values();
        
        if (quickmods.isEmpty()) {
            System.out.println("No QuickMods selected, nothing available to display");
            return;
        }
        
        // build graph structure
        Graph graph = new SingleGraph("QuickMod Dependencies");
        
        Map<QuickMod, QuickModVersion> versions = new HashMap<>();
        Map<String, Node> nodes = new HashMap<>();
        for (QuickMod quickmod : quickmods) {
            QuickModVersion version = selectVersion(quickmod, options.valueOf(mcVersionOption));
            if (version == null) {
                continue;
            }
            Node node = graph.addNode(quickmod.getUid());
            node.setAttribute("ui.label", quickmod.getUid());
            nodes.put(quickmod.getUid(), node);
            versions.put(quickmod, version);
        }
        
        for (Map.Entry<QuickMod, QuickModVersion> entry : versions.entrySet()) {
            for (QuickModReference ref : entry.getValue().getReferences()) {
                if (!nodes.containsKey(ref.getUid())) {
                    Node node = graph.addNode(ref.getUid());
                    node.setAttribute("fill-color", "lightgrey");
                    node.setAttribute("ui.label", ref.getUid());
                    nodes.put(ref.getUid(), node);
                }
                Edge edge = graph.addEdge(entry.getKey().getUid() + "->" + ref.getUid(), nodes.get(entry.getKey().getUid()), nodes.get(ref.getUid()), true);
                edge.setAttribute("ui.class", "type_" + ref.getType());
                edge.setAttribute("arrow-shape", "arrow");
                edge.setAttribute("ui.label", ref.getVersion().toString());
            }
        }
        
        graph.addAttribute("ui.stylesheet", "edge { stroke-mode: dots; }");
        graph.addAttribute("ui.stylesheet", "edge.type_depends { stroke-mode: plain; }");
        graph.addAttribute("ui.stylesheet", "edge.type_recommends { stroke-mode: dashes; }");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        
        if (options.has(outOption)) {
            String outFilename = options.valueOf(outOption);
            try {
                if (outFilename.endsWith(".dot")) {
                    graph.write(new FileSinkDOT(true), outFilename);
                } else {
                    System.err.println("Unsupported file format");
                    // TODO support at least png
                }
            } catch (IOException ex) {
                Logger.getLogger(GraphTool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (options.has(displayOption) || !options.has(outOption)) {
            System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
            graph.display();
        }
    }

    private QuickModVersion selectVersion(QuickMod quickmod, String mcVersion) {
        Collection<QuickModVersion> versions = quickmod.getVersions();
        Collections.sort((List<QuickModVersion>)versions, (QuickModVersion t1, QuickModVersion t2) -> Version.compare(t1.getVersion(), t2.getVersion()));
        for (QuickModVersion version : versions) {
            if (mcVersion == null || version.getMcCompat().contains(mcVersion)) {
                return version;
            }
        }
        return null;
    }
}
