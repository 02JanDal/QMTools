package org.multimc.qmtools;

import java.util.ArrayList;
import java.util.Collection;

public class QuickModVersion {
    private Collection<String> mcCompat = new ArrayList<String>();
    private Interval forgeCompat;
    private Interval liteloaderCompat;
    private String name;
    private String version;
    private String type = new String("Release");
    private String sha1;
    private Collection<QuickModReference> references = new ArrayList<QuickModReference>();
    private Collection<QuickModDownload> urls = new ArrayList<QuickModDownload>();
    private InstallType installType;
    
    public enum InstallType {
        forgeMod, forgeCoreMod, liteloaderMod,
        extract, configPack, group
    }
    
    public static InstallType installTypeFromString(String str) throws InvalidInstallTypeException {
        switch (str) {
            case "forgeMod":
                return InstallType.forgeMod;
            case "forgeCoreMod":
                return InstallType.forgeCoreMod;
            case "liteloaderMod":
                return InstallType.liteloaderMod;
            case "extract":
                return InstallType.extract;
            case "configPack":
                return InstallType.configPack;
            case "group":
                return InstallType.group;
        }
        throw new InvalidInstallTypeException("Invalid installType: " + str);
    }
    
    public QuickModVersion(String name, Collection<String> mcCompat) {
        this.name = name;
        this.mcCompat = mcCompat;
    }
    
    public QuickModVersion() {
    }

    public Collection<String> getMcCompat() {
        return mcCompat;
    }

    public void setMcCompat(Collection<String> mcCompat) {
        this.mcCompat = mcCompat;
    }

    public Interval getForgeCompat() {
        return forgeCompat;
    }

    public void setForgeCompat(Interval forgeCompat) {
        this.forgeCompat = forgeCompat;
    }

    public Interval getLiteloaderCompat() {
        return liteloaderCompat;
    }

    public void setLiteloaderCompat(Interval liteloaderCompat) {
        this.liteloaderCompat = liteloaderCompat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSha1() {
        return sha1;
    }

    public void setSha1(String sha1) {
        this.sha1 = sha1;
    }

    public Collection<QuickModReference> getReferences() {
        return references;
    }

    public void setReferences(Collection<QuickModReference> references) {
        this.references = references;
    }

    public Collection<QuickModDownload> getUrls() {
        return urls;
    }

    public void setUrls(Collection<QuickModDownload> urls) {
        this.urls = urls;
    }
    
    public InstallType getInstallType() {
        return installType;
    }
    
    public void setInstallType(InstallType installType) {
        this.installType = installType;
    }
}
