package org.multimc.qmtools.qmtool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import org.multimc.qmtools.QuickMod;
import org.multimc.qmtools.QuickModIOAccess;
import org.multimc.qmtools.AbstractTool;

public class QMTool extends AbstractTool {

    public QMTool() {
    }

    @Override
    public String getDescription() {
        return "Modify the root QuickMod object (metadata)";
    }

    public static void main(String[] args) {
        new QMTool().run(args);
    }

    @Override
    public void run(String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpec<String> outOption = parser.accepts("out", "If set, outputs the result to a separate file")
                .withRequiredArg().ofType(String.class);
        OptionSpec<Integer> formatVersionOption = parser.accepts("formatVersion", "Set the formatVersion field").withRequiredArg().ofType(Integer.class);
        OptionSpec<String> uidOption = parser.accepts("uid", "Sets the uid field").withRequiredArg().ofType(String.class);
        OptionSpec<String> repoOption = parser.accepts("repo", "Sets the repo field").withRequiredArg().ofType(String.class);
        OptionSpec<String> modIdOption = parser.accepts("modId", "Sets the modId field").withRequiredArg().ofType(String.class);
        OptionSpec<String> nameOption = parser.accepts("name", "Sets the name field").withRequiredArg().ofType(String.class);
        OptionSpec<String> nemNameOption = parser.accepts("nemName", "Sets the nemName field").withRequiredArg().ofType(String.class);
        OptionSpec<String> descriptionOption = parser.accepts("description", "Sets the description field").withRequiredArg().ofType(String.class);
        OptionSpec<String> licenseOption = parser.accepts("license", "Sets the license field").withRequiredArg().ofType(String.class);
        OptionSpec<String> urlsOption = parser.accepts("urls", "Sets the urls field. <type1>:<url1>,<type2>:<url2> etc.").withRequiredArg().withValuesSeparatedBy(',').ofType(String.class);
        OptionSpec<String> updateUrlOption = parser.accepts("updateUrl", "Sets the updateUrl option").withRequiredArg().ofType(String.class);
        OptionSpec<String> tagsOption = parser.accepts("tags", "Sets the tags field. Comma separated list.").withRequiredArg().ofType(String.class).withValuesSeparatedBy(',');
        OptionSpec<String> categoriesOption = parser.accepts("categories", "Sets the categories field. Comman separated list.").withRequiredArg().ofType(String.class).withValuesSeparatedBy(',');
        OptionSpec<String> authorsOption = parser.accepts("authors", "Sets the authors field. <type1>:<name2>,<type2>:<name2> etc.").withRequiredArg().ofType(String.class).withValuesSeparatedBy(',');
        OptionSpec<String> referencesOption = parser.accepts("references", "Sets the references field. <uid1>:<url1>,<uid2>:<url2> etc.").withRequiredArg().ofType(String.class).withValuesSeparatedBy(',');
        OptionSpec<URL> mavenReposOption = parser.accepts("mavenRepos", "Sets the mavenRepos field. Comma separated list of URLs").withRequiredArg().ofType(URL.class).withValuesSeparatedBy(',');
        OptionSpec<String> fileOption = parser.nonOptions("file").describedAs("QuickMod file to operate on").ofType(String.class);
        OptionSpecBuilder helpOption = parser.accepts("help");
        OptionSet options = parser.parse(args);
        
        if (options.has(helpOption) || !options.has(fileOption) || options.valueOf(fileOption) == null) {
            try {
                parser.printHelpOn(System.out);
            } catch (IOException ex) {
                QMTool.getLogger().log(Level.SEVERE, null, ex);
            }
            return;
        }
        
        QuickMod quickmod;
        if (new File(options.valueOf(fileOption)).exists()) {
            try {
                quickmod = QuickModIOAccess.read(options.valueOf(fileOption));
            } catch (IOException ex) {
                QMTool.getLogger().log(Level.SEVERE, null, ex);
                return;
            }
        } else {
            quickmod = new QuickMod();
        }
        
        if (options.has(formatVersionOption)) {
            quickmod.setFormatVersion(options.valueOf(formatVersionOption));
        }
        if (options.has(uidOption)) {
            quickmod.setUid(options.valueOf(uidOption));
        }
        if (options.has(repoOption)) {
            quickmod.setRepo(options.valueOf(repoOption));
        }
        if (options.has(modIdOption)) {
            quickmod.setModId(options.valueOf(modIdOption));
        }
        if (options.has(nameOption)) {
            quickmod.setName(options.valueOf(nameOption));
        }
        if (options.has(nemNameOption)) {
            quickmod.setNemName(options.valueOf(nemNameOption));
        }
        if (options.has(descriptionOption)) {
            quickmod.setDescription(options.valueOf(descriptionOption));
        }
        if (options.has(licenseOption)) {
            quickmod.setLicense(options.valueOf(licenseOption));
        }
        if (options.has(urlsOption)) {
            Map<String, Collection<String>> urls = new HashMap<>();
            for (String url : options.valuesOf(urlsOption)) {
                String[] parts = url.split(":");
                if (parts.length != 2) {
                    QMTool.getLogger().log(Level.SEVERE, "URL item needs to be in the format <type>:<url>");
                    return;
                }
                if (!urls.containsKey(parts[0])) {
                    urls.put(parts[0], new ArrayList<>());
                }
                Collection old = urls.get(parts[0]);
                old.add(parts[1]);
                urls.put(parts[0], old);
            }
            quickmod.setUrls(urls);
        }
        if (options.has(updateUrlOption)) {
            quickmod.setUpdateUrl(options.valueOf(updateUrlOption));
        }
        if (options.has(tagsOption)) {
            quickmod.setTags(options.valuesOf(tagsOption));
        }
        if (options.has(categoriesOption)) {
            quickmod.setCategories(options.valuesOf(categoriesOption));
        }
        if (options.has(authorsOption)) {
            Map<String, Collection<String>> authors = new HashMap<>();
            for (String author : options.valuesOf(authorsOption)) {
                String[] parts = author.split(":");
                if (parts.length != 2) {
                    QMTool.getLogger().log(Level.SEVERE, "Author item needs to be in the format <type>:<name>");
                    return;
                }
                if (!authors.containsKey(parts[0])) {
                    authors.put(parts[0], new ArrayList<>());
                }
                Collection old = authors.get(parts[0]);
                old.add(parts[1]);
                authors.put(parts[0], old);
            }
            quickmod.setAuthors(authors);
        }
        if (options.has(referencesOption)) {
            Map<String, String> references = new HashMap<>();
            for (String reference : options.valuesOf(referencesOption)) {
                String[] parts = reference.split(":");
                if (parts.length != 2) {
                    QMTool.getLogger().log(Level.SEVERE, "Reference item needs to be in the format <uid>:<url>");
                }
                references.put(parts[0], parts[1]);
            }
            quickmod.setReferences(references);
        }
        if (options.has(mavenReposOption)) {
            quickmod.setMavenRepos(options.valuesOf(mavenReposOption));
        }
        
        try {
            String filename = options.valueOf(fileOption);
            if (options.has(outOption)) {
                filename = options.valueOf(outOption);
            }
            QuickModIOAccess.write(filename, quickmod);
        } catch (FileNotFoundException ex) {
            QMTool.getLogger().log(Level.SEVERE, null, ex);
        }
    }

    private static Logger getLogger() {
        return Logger.getLogger(QMTool.class.getName());
    }
}
