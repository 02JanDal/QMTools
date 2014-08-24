package org.multimc.qmtools.versionappender;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import joptsimple.OptionSpecBuilder;
import org.multimc.qmtools.Interval;
import org.multimc.qmtools.QuickMod;
import org.multimc.qmtools.QuickModDownload;
import org.multimc.qmtools.QuickModIOAccess;
import org.multimc.qmtools.QuickModReference;
import org.multimc.qmtools.QuickModVersion;

public class VersionAppender {

    public static void main(String[] args) {
        OptionParser parser = new OptionParser();
        OptionSpec<String> outOption = parser.accepts("out", "If set, outputs the result to a separate file")
                .withRequiredArg().ofType(String.class);
        OptionSpec<String> nameOption = parser.accepts("name", "Name of the version. Needs to be a semver if no --version is given").withRequiredArg().ofType(String.class);
        OptionSpec<String> versionOption = parser.accepts("version", "Required if --name isn't a semver. Must be a semver.").withRequiredArg().ofType(String.class);
        OptionSpec<String> mcCompatOption = parser.accepts("mcCompat", "List of supported Minecraft versions. Separated by ','").withRequiredArg().withValuesSeparatedBy(',').ofType(String.class);
        OptionSpec<String> forgeCompatOption = parser.accepts("forgeCompat", "Interval of supported Forge versions").withRequiredArg().ofType(String.class);
        OptionSpec<String> liteloaderCompatOption = parser.accepts("liteloaderCompat", "Interval of supported LiteLoader versions").withRequiredArg().ofType(String.class);
        OptionSpec<String> typeOption = parser.accepts("type", "Type of the version (for example Release, Beta, Alpha etc.)").withRequiredArg().ofType(String.class);
        OptionSpec<String> installTypeOption = parser.accepts("installType", "Possible options: forgeMod, forgeCoreMod, liteloaderMod, extract, configPack, group")
                .withRequiredArg().ofType(String.class).defaultsTo("forgeMod");
        OptionSpec<String> sha1Option = parser.accepts("sha1", "Either a real SHA1, or a file from which a SHA1 will be computed")
                .withRequiredArg().ofType(String.class);
        OptionSpec<String> referencesOption = parser.accepts("references", "Multiple accepted, separate by ','. <type>:<uid>:<version>").withRequiredArg().withValuesSeparatedBy(',');
        OptionSpec<String> urlsOption = parser.accepts("urls", "Multiple accepted, separate by ','. <url>:<downloadType>[:<priority>]").withRequiredArg().withValuesSeparatedBy(',');
        OptionSpecBuilder overwriteOption = parser.accepts("overwrite", "Use to overwrite an existing version, as determined by --name");
        OptionSpec<String> fileOption = parser.nonOptions("file").describedAs("QuickMod file to operate on").ofType(String.class);
        OptionSpecBuilder helpOption = parser.accepts("help");
        OptionSet options = parser.parse(args);
        
        if (options.has(helpOption) || !options.has(fileOption) || !options.has(nameOption)) {
            try {
                parser.printHelpOn(System.out);
            } catch (IOException ex) {
                VersionAppender.getLogger().log(Level.SEVERE, null, ex);
            }
            return;
        }
        
        QuickMod quickmod;
        try {
            quickmod = QuickModIOAccess.read(options.valueOf(fileOption));
        } catch (IOException ex) {
            Logger.getLogger(VersionAppender.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        boolean versionAlreadyExists = quickmod.containsVersion(options.valueOf(nameOption));
        if (!options.has(overwriteOption)
                && versionAlreadyExists) {
            Logger.getLogger(VersionAppender.class.getName()).log(Level.WARNING, "There already exists a version with the given name. Use --overwrite if you want to force overwriting");
            return;
        }
        
        QuickModVersion version;
        if (versionAlreadyExists) {
            version = quickmod.findVersion(options.valueOf(nameOption));
        } else {
            version = new QuickModVersion();
        }
        
        if (options.has(nameOption)) {
            version.setName(options.valueOf(nameOption));
        }
        if (options.has(versionOption)) {
            version.setVersion(options.valueOf(versionOption));
        }
        if (options.has(mcCompatOption)) {
            version.setMcCompat(options.valuesOf(mcCompatOption));
        }
        if (options.has(forgeCompatOption)) {
            version.setForgeCompat(Interval.fromString(options.valueOf(forgeCompatOption)));
        }
        if (options.has(liteloaderCompatOption)) {
            version.setLiteloaderCompat(Interval.fromString(options.valueOf(liteloaderCompatOption)));
        }
        if (options.has(typeOption)) {
            version.setType(options.valueOf(typeOption));
        }
        if (options.has(installTypeOption)) {
            version.setInstallType(QuickModVersion.installTypeFromString(options.valueOf(installTypeOption)));
        }
        if (options.has(sha1Option)) {
            if (new File(options.valueOf(sha1Option)).exists()) {
                try {
                    version.setSha1(sha1OfFile(options.valueOf(sha1Option)));
                } catch (NoSuchAlgorithmException | IOException ex) {
                    Logger.getLogger(VersionAppender.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                version.setSha1(options.valueOf(sha1Option));
            }
        }
        Collection<QuickModReference> references = version.getReferences();
        for (String reference : options.valuesOf(referencesOption)) {
            QuickModReference ref = new QuickModReference();
            String[] parts = reference.split(":");
            if (parts.length != 3) {
                VersionAppender.getLogger().log(Level.SEVERE, "Reference item needs to be in the format <type>:<uid>:<version>");
                return;
            }
            ref.setType(parts[0]);
            ref.setUid(parts[1]);
            ref.setVersion(parts[2]);
            references.add(ref);
        }
        version.setReferences(references);
        Collection<QuickModDownload> urls = version.getUrls();
        for (String url : options.valuesOf(urlsOption)) {
            QuickModDownload download = new QuickModDownload();
            String[] parts = url.split(":");
            if (parts.length < 2 || parts.length > 3) {
                VersionAppender.getLogger().log(Level.SEVERE, "Reference item needs to be in the format <url>:<downloadType>[:<priority>]");
                return;
            }
            download.setUrl(parts[0]);
            download.setDownloadType(QuickModDownload.downloadTypeFromString(parts[1]));
            if (parts.length == 3) {
                download.setPriority(Integer.valueOf(parts[2]));
            }
            urls.add(download);
        }
        
        try {
            String filename = options.valueOf(fileOption);
            if (options.has(outOption)) {
                filename = options.valueOf(outOption);
            }
            QuickModIOAccess.write(filename, quickmod);
        } catch (FileNotFoundException ex) {
            VersionAppender.getLogger().log(Level.SEVERE, null, ex);
        }
    }
    
    private static String sha1OfFile(String filename) throws FileNotFoundException, NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        FileInputStream in = new FileInputStream(filename);
        byte[] buf = new byte[1024];
        int readSize;
        while ((readSize = in.read(buf)) != -1) {
            md.update(buf, 0, readSize);
        }
        
        byte[] sha1Bytes = md.digest();
        
        StringBuilder outBuf = new StringBuilder("");
        for (int i = 0; i < sha1Bytes.length; ++i) {
            outBuf.append(Integer.toString((sha1Bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return outBuf.toString();
    }
    
    private static Logger getLogger() {
        return Logger.getLogger(VersionAppender.class.getName());
    }
}
