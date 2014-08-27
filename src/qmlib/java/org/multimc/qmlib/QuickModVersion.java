package org.multimc.qmlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class QuickModVersion {

    public static int compareVersion(String v1, String v2) {
        Version version1 = new Version(v1);
        Version version2 = new Version(v2);
        
        Version.Section dummy = new Version.Section("0", Integer.valueOf(0));
        for (int i = 0; i < Math.max(version1.getSections().size(), version2.getSections().size()); ++i) {
            Version.Section section1 = (i >= version1.getSections().size()) ? dummy : version1.getSections().get(i);
            Version.Section section2 = (i >= version2.getSections().size()) ? dummy : version2.getSections().get(i);
            int result = section1.compare(section2);
            if (result != 0) {
                return result;
            }
        }
        
        return 0;
    }
    
    private static class Version {
        public Version(String str) {
            String[] parts = str.split(".");
            for (String part : parts) {
                try {
                    Integer integer = Integer.parseInt(part);
                    sections.add(new Section(part, integer));
                } catch (NumberFormatException ex) {
                    sections.add(new Section(part));
                }
            }
        }
        
        private List<Section> sections = new LinkedList<Section>();
        
        public List<Section> getSections() {
            return sections;
        }
        
        public static class Section {
            public Section(String str, Integer num) {
                this.string = str;
                this.number = num;
                this.numValid = true;
            }
            public Section(String str) {
                this.string = str;
                this.numValid = false;
            }
            
            public String string;
            public Integer number;
            public boolean numValid;
            
            public int compare(Section other) {
                return numValid && other.numValid ? Integer.compare(number, other.number) : string.compareTo(other.string);
            }
        }
    }

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
        return version == null ? name : version;
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
