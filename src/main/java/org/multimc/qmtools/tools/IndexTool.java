package org.multimc.qmtools.tools;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.multimc.qmlib.QuickMod;
import org.multimc.qmlib.QuickModIOAccess;
import org.multimc.qmlib.QuickModIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IndexTool extends AbstractTool {
    @Override
    public String getDescription() {
        return "Create and update QuickMod indexes";
    }

    @Override
    public void run(String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpec<URI> baseUrlOption = parser.accepts("baseUrl").withRequiredArg().ofType(URI.class).required();
        OptionSpec<String> repoOption = parser.accepts("repo").withRequiredArg().ofType(String.class).required();
        OptionSpec<String> removeOption = parser.accepts("remove").withRequiredArg().ofType(String.class).describedAs("Remove this from all updateUrls");
        OptionSpec<File> filesOption = parser.nonOptions("file").describedAs("QuickMod files to use. Leave empty to use all in current directory.").ofType(File.class);
        OptionSet options = parser.parse(args);

        Map<String, QuickMod> quickmods = AbstractTool.quickmods(filesOption, options);

        if (quickmods.isEmpty()) {
            System.out.println("No QuickMods selected, nothing available to create an index out of");
            return;
        }

        QuickModIndex index = new QuickModIndex();
        index.setBaseUrl(options.valueOf(baseUrlOption));
        index.setRepo(options.valueOf(repoOption));

        String removeString = options.valueOf(removeOption);

        List<QuickModIndex.Item> items = new LinkedList<>();
        for (QuickMod mod : quickmods.values()) {
            String url = mod.getUpdateUrl();
            if (removeString != null) {
                url = url.replace(removeString, "");
            }
            try {
                items.add(new QuickModIndex.Item(new URI(url), mod.getUid()));
            } catch (URISyntaxException e) {
                System.out.println("Cannot parse " + url + " as URI");
            }
        }

        index.setIndex(items);

        System.out.println("Writing index...");
        try {
            QuickModIOAccess.write("index.json", index);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IndexTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
