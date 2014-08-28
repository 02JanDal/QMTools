package org.multimc.qmlib;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

public class QuickModVersion {
    private boolean secureEquals(Object first, Object second) {
        if (first != null) {
            return first.equals(second);
        } else if (second != null) {
            return second.equals(first);
        } else {
            return first == second;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof QuickModVersion)) {
            return false;
        }
        QuickModVersion version = (QuickModVersion) other;
        return secureEquals(this.mcCompat, version.mcCompat) && secureEquals(this.forgeCompat, version.forgeCompat)
                && secureEquals(this.liteloaderCompat, version.liteloaderCompat) && secureEquals(this.name, version.name)
                && secureEquals(this.version, version.version) && secureEquals(this.type, version.type)
                && secureEquals(this.sha1, version.sha1) && secureEquals(this.references, version.references)
                && secureEquals(this.urls, version.urls) && secureEquals(this.installType, version.installType);
    }

    @Override
    public int hashCode() {
        return this.mcCompat.hashCode() ^ this.name.hashCode();
    }

    @Override
    public QuickModVersion clone() {
        QuickModVersion other;
        try {
            other = (QuickModVersion) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
        other.mcCompat = mcCompat;
        other.forgeCompat = forgeCompat;
        other.liteloaderCompat = liteloaderCompat;
        other.name = name;
        other.version = version;
        other.type = type;
        other.sha1 = sha1;
        other.references = references;
        other.urls = urls;
        other.installType = installType;
        return other;
    }

    @Override
    public String toString() {
        return "QuickModVersion(name=" + name + ", mcCompat=" + StringUtils.join(mcCompat, " ") + ", forgeCompat=" + forgeCompat.toString() + ")";
    }

    private Collection<String> mcCompat = new ArrayList<>();
    private Interval forgeCompat;
    private Interval liteloaderCompat;
    private String name;
    private String version;
    private String type = "Release";
    private String sha1;
    private Collection<QuickModReference> references = new ArrayList<>();
    private Collection<QuickModDownload> urls = new ArrayList<>();
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
        return version == null ? name : version;
    }

    public String getVersionRaw() {
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
