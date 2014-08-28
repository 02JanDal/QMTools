package org.multimc.qmlib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class QuickMod {
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
        if (!(other instanceof QuickMod)) {
            return false;
        }
        QuickMod mod = (QuickMod) other;
        return secureEquals(mod.formatVersion, formatVersion)
                && secureEquals(mod.uid, uid)
                && secureEquals(mod.repo, repo)
                && secureEquals(mod.modId, modId)
                && secureEquals(mod.name, name)
                && secureEquals(mod.nemName, nemName)
                && secureEquals(mod.description, description)
                && secureEquals(mod.license, license)
                && secureEquals(mod.urls, urls)
                && secureEquals(mod.updateUrl, updateUrl)
                && secureEquals(mod.tags, tags)
                && secureEquals(mod.categories, categories)
                && secureEquals(mod.authors, authors)
                && secureEquals(mod.references, references)
                && secureEquals(mod.versions, versions)
                && secureEquals(mod.mavenRepos, mavenRepos);
    }

    @Override
    public int hashCode() {
        return this.uid.hashCode() ^ this.repo.hashCode() ^ this.versions.hashCode();
    }

    private int formatVersion = -1;
    private String uid;
    private String repo;
    private String modId;
    private String name;
    private String nemName;
    private String description;
    private String license;
    private Map<String, Collection<String>> urls = new HashMap<>();
    private String updateUrl;
    private Collection<String> tags = new ArrayList<>();
    private Collection<String> categories = new ArrayList<>();
    private Map<String, Collection<String>> authors = new HashMap<>();
    private Map<String, String> references = new HashMap<>();
    private Collection<QuickModVersion> versions = new ArrayList<>();
    private Collection<URL> mavenRepos;

    private static Gson m_gson;
    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Interval.class, new IntervalAdapter());
        builder.registerTypeAdapter(QuickModVersion.InstallType.class, new InstallTypeAdapter());
        builder.registerTypeAdapter(QuickModDownload.DownloadType.class, new DownloadTypeAdapter());
        builder.enableComplexMapKeySerialization();
        builder.setPrettyPrinting();
        QuickMod.m_gson = builder.create();
    }

    public QuickMod(String uid, String repo, String name, String updateUrl) {
        this.uid = uid;
        this.repo = repo;
        this.name = name;
        this.updateUrl = updateUrl;
    }
    
    public QuickMod() {
    }

    public static QuickMod fromJson(String json) {
        return m_gson.fromJson(json, QuickMod.class);
    }

    public static QuickModIndex indexFromJson(String json) {
        return m_gson.fromJson(json, QuickModIndex.class);
    }

    public static String indexToJson(QuickModIndex index) {
        return m_gson.toJson(index);
    }

    public String toJson() {
        return m_gson.toJson(this);
    }

    public int getFormatVersion() {
        return this.formatVersion;
    }

    public void setFormatVersion(int formatVersion) {
        this.formatVersion = formatVersion;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNemName() {
        return nemName;
    }

    public void setNemName(String nemName) {
        this.nemName = nemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public Map<String, Collection<String>> getUrls() {
        return urls;
    }

    public void setUrls(Map<String, Collection<String>> urls) {
        this.urls = urls;
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }
    
    public Collection<String> getCategories() {
        return categories;
    }
    
    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    public Map<String, Collection<String>> getAuthors() {
        return authors;
    }

    public void setAuthors(Map<String, Collection<String>> authors) {
        this.authors = authors;
    }

    public Map<String, String> getReferences() {
        return references;
    }

    public void setReferences(Map<String, String> references) {
        this.references = references;
    }

    public Collection<QuickModVersion> getVersions() {
        return versions;
    }

    public void addVersion(QuickModVersion version) {
        this.versions.add(version);
    }
    
    public void removeVersion(QuickModVersion version) {
        this.versions.remove(version);
    }
    
    public void removeVersion(String name) {
        this.versions.removeIf(new Predicate<QuickModVersion>() {
            @Override
            public boolean test(QuickModVersion t) {
                return t.getName() == name || t.getVersion() == name;
            }
        });
    }
    
    public QuickModVersion findVersion(String name) {
        for (QuickModVersion v : this.versions) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }
    
    public boolean containsVersion(String name) {
        return this.findVersion(name) != null;
    }
    
    public void clearVersions() {
        this.versions.clear();
    }
    
    public Collection<URL> getMavenRepos() {
        return mavenRepos;
    }

    public void setMavenRepos(Collection<URL> mavenRepos) {
        this.mavenRepos = mavenRepos;
    }
}
