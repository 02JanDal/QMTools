package org.multimc.qmtools.tools;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import org.multimc.qmlib.QuickMod;
import org.multimc.qmlib.QuickModVersion;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

public class VerificationTool extends AbstractTool {
    @Override
    public String getDescription() {
        return "Verify that a QuickMod file is valid";
    }

    @Override
    public void run(String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpecBuilder noWarnings = parser.accepts("no-warnings");
        OptionSpec<File> filesOption = parser.nonOptions("file").describedAs("QuickMod files to use. Leave empty to use all in current directory.").ofType(File.class);
        OptionSet options = parser.parse(args);

        Map<String, QuickMod> quickmods = AbstractTool.quickmods(filesOption, options);

        if (quickmods.isEmpty()) {
            System.out.println("No QuickMods selected, nothing available to verify");
            return;
        }

        Pattern containsLettersPattern = Pattern.compile("[0-9\\.]*");

        for (Map.Entry<String, QuickMod> entry : quickmods.entrySet()) {
            QuickMod mod = entry.getValue();
            List<String> errors = new LinkedList<>();
            List<String> warnings = new LinkedList<>();
            if (mod.getFormatVersion() == -1) {
                errors.add("Invalid or no 'formatVersion' given");
            }
            if (mod.getUid() == null || mod.getUid().isEmpty()) {
                errors.add("Invalid or no 'uid' given");
            } else if (!mod.getUid().contains(".")) {
                warnings.add("'uid' does not contain a dot");
            }
            if (mod.getRepo() == null || mod.getRepo().isEmpty()) {
                errors.add("Invalid or no 'repo' given");
            } else if (!mod.getRepo().contains(".")) {
                warnings.add("'repo' does not contain a dot");
            }
            if (mod.getUpdateUrl() == null || mod.getUpdateUrl().isEmpty()) {
                errors.add("Invalid or no 'updateUrl' given");
            } else if (!VerificationTool.canParseUrl(mod.getUpdateUrl())) {
                errors.add("String in 'updateUrl' can't be parsed as URL");
            }
            if (mod.getVersions() == null) {
                errors.add("Invalid or no 'versions' array given");
            } else if (mod.getVersions().isEmpty()) {
                warnings.add("No items in 'versions'");
            } else {
                List<QuickModVersion> versions = (List<QuickModVersion>) mod.getVersions();
                for (int i = 0; i < versions.size(); ++i) {
                    QuickModVersion version = versions.get(i);
                    String specifier = "version number " + Integer.toString(i);
                    if (version.getMcCompat() == null || version.getMcCompat().isEmpty()) {
                        errors.add("Invalid or no 'mcCompat' in " + specifier);
                    } else {
                        for (String mcVersion : version.getMcCompat()) {
                            if (mcVersion == null || mcVersion.isEmpty()) {
                                errors.add("Invalid 'mcCompat' item in " + specifier);
                            } else if (!mcVersion.contains(".")) {
                                warnings.add("'mcCompat' item in " + specifier + " doesn't contain a dot");
                            }
                        }
                    }
                    if (version.getName() == null || version.getName().isEmpty()) {
                        errors.add("Invalid or no 'name' in " + specifier);
                    } else if (!version.getName().contains(".")) {
                        warnings.add("'name' in " + specifier + " doesn't contain a dot");
                    } else if (!containsLettersPattern.matcher(version.getName()).matches()) {
                        warnings.add("Severe: Version 'name' contains non-numbers or non-dots in " + specifier);
                    }
                    if (version.getVersionRaw() != null) {
                        if (version.getVersionRaw().isEmpty()) {
                            errors.add("Empty 'version' given in " + specifier);
                        } else if (!version.getVersionRaw().contains(".")) {
                            warnings.add("'version' in " + specifier + " doesn't contain a dot");
                        } else if (!containsLettersPattern.matcher(version.getVersionRaw()).matches()) {
                            warnings.add("Severe: Version 'version' contains non-numbers or non-dots in " + specifier);
                        }
                        if (version.getName().equals(version.getVersion())) {
                            warnings.add("'name' and 'version' equal in " + specifier);
                        }
                    }
                }
            }

            if (!errors.isEmpty()) {
                System.out.println("Errors found in " + entry.getKey() + ":");
                for (String error : errors) {
                    System.out.println("\t" + error);
                }
            } else {
                System.out.println("All clear, no errors found for " + mod.getName());
            }
            if (!warnings.isEmpty() && !options.has(noWarnings)) {
                System.out.println("Warnings found in " + entry.getKey() + ":");
                for (String warning : warnings) {
                    System.out.println("\t" + warning);
                }
            }
        }
    }

    private static boolean canParseUrl(String in) {
        try {
            new URI(in);
        } catch (URISyntaxException e) {
            return false;
        }
        return true;
    }
}
