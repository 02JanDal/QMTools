package org.multimc.qmlib;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class BaseQMTest {
    protected static Collection<String> createStringList(String one) {
        Collection<String> strings = new ArrayList<>();
        strings.add(one);
        return strings;
    }

    protected static Collection<String> createStringList(String one, String two) {
        Collection<String> strings = new ArrayList<>();
        strings.add(one);
        strings.add(two);
        return strings;
    }

    protected static Collection<String> createStringList(String one, String two, String three) {
        Collection<String> strings = new ArrayList<>();
        strings.add(one);
        strings.add(two);
        strings.add(three);
        return strings;
    }

    protected static Map<String, Collection<String>> createAuthors() {
        Map<String, Collection<String>> authors = new HashMap<>();
        authors.put("Founders", createStringList("Ford Prefect", "Zaphod Beeblebrox", "Arthur Philip Dent"));
        authors.put("Pokémon", createStringList("Bulbasaur", "Ivysaur", "thường"));
        return authors;
    }

    protected static Collection<URL> createUrlList(String one, String two) {
        Collection<URL> urls = new LinkedList<>();
        try {
            urls.add(new URL(one));
            urls.add(new URL(two));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new AssertionError(e.getMessage(), e);
        }
        return urls;
    }

    protected static Map<String, Collection<String>> createUrls() {
        Map<String, Collection<String>> authors = new HashMap<>();
        authors.put("website", createStringList("https://iamboredofwritingunittests.com/", "ssh:iwonderifpeoplewillusethisscheme.nope/"));
        authors.put("issues", createStringList("github://Nope@Nope/issues", "nope"));
        return authors;
    }

    protected static Map<String, String> createReferences() {
        Map<String, String> references = new HashMap<>();
        references.put("i.am.bored", "github://Nope@Nope/IAMBORED.json");
        references.put("stuff", "http://morestuff.evenmorestuff/somefile.someextension");
        return references;
    }

    protected static QuickMod createQuickMod() {
        QuickMod qm = new QuickMod();

        qm.setFormatVersion(4);
        qm.setRepo("my.repo");
        qm.setUid("this.is.mod");
        qm.setName("This Is Mod");
        qm.setModId("ThisIsMod");
        qm.setNemName("ThisIiisMod");
        qm.setDescription("This is a description");
        qm.setUpdateUrl("http://stuff.com/this.is.mod.quickmod");
        qm.setAuthors(createAuthors());
        qm.setUrls(createUrls());
        qm.setCategories(createStringList("Tech", "Magic", "Everything else"));
        qm.setTags(createStringList("MJ", "EU", "Dogs"));
        qm.setLicense("This quickmod is free for your dog to use");
        qm.setMavenRepos(createUrlList("http://ihaveacatonmykejfaiedasfhv", "https://cat.miau.foodnoworiwillkillyou"));
        qm.setReferences(createReferences());

        return qm;
    }

    protected static QuickModVersion createQuickModVersion() {
        QuickModVersion version = new QuickModVersion();

        version.setName("1.42-beta");
        version.setVersion("1.42");
        version.setForgeCompat(Interval.fromString("[1.1.1.1,2.2.2)"));
        version.setLiteloaderCompat(Interval.fromString("5"));
        version.setInstallType(QuickModVersion.InstallType.liteloaderMod);
        version.setMcCompat(BaseQMTest.createStringList("1.4.6", "1.8.1"));
        version.setSha1("7283abd87d");
        version.setType("Beta");

        List<QuickModDownload> downloads = new LinkedList<>();
        downloads.add(new QuickModDownload("http://money.trees"));
        version.setUrls(downloads);

        Collection<QuickModReference> references = new LinkedList<>();
        QuickModReference ref = new QuickModReference();
        ref.setType("recommends");
        ref.setUid("other.mod");
        ref.setVersion("5.3.1");
        references.add(ref);
        version.setReferences(references);

        return version;
    }
}
