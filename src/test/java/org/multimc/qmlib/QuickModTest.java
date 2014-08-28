package org.multimc.qmlib;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class QuickModTest extends BaseQMTest {

    @Test
    public void testGet() {
        QuickMod qm = createQuickMod();

        assertEquals(qm.getFormatVersion(), 4);
        assertEquals(qm.getRepo(), "my.repo");
        assertEquals(qm.getName(), "This Is Mod");
        assertEquals(qm.getModId(), "ThisIsMod");
        assertEquals(qm.getNemName(), "ThisIiisMod");
        assertEquals(qm.getDescription(), "This is a description");
        assertEquals(qm.getAuthors(), createAuthors());
        assertEquals(qm.getUrls(), createUrls());
        assertEquals(qm.getCategories(), createStringList("Tech", "Magic", "Everything else"));
        assertEquals(qm.getTags(), createStringList("MJ", "EU", "Dogs"));
        assertEquals(qm.getLicense(), "This quickmod is free for your dog to use");
        assertEquals(qm.getMavenRepos(), createUrlList("http://ihaveacatonmykejfaiedasfhv", "https://cat.miau.foodnoworiwillkillyou"));
        assertEquals(qm.getReferences(), createReferences());
    }

    @Test
    public void testEqualsAndHashCode() {
        QuickMod qm = createQuickMod();

        assertEquals("Testing hashCode", qm.hashCode(), createQuickMod().hashCode());
        assertEquals("Testing equals", qm, createQuickMod());
    }

    @Test
    public void testAddRemoveClearVersions() {
        QuickMod qm = createQuickMod();

        assertEquals(qm.getVersions(), new LinkedList<QuickModVersion>());
        assertEquals(qm.getVersions().size(), 0);
        qm.addVersion(new QuickModVersion("1.42.0", createStringList("1.7.10")));
        assertEquals(qm.getVersions().size(), 1);
        assertEquals(qm.getVersions().iterator().next(), new QuickModVersion("1.42.0", createStringList("1.7.10")));
        qm.addVersion(new QuickModVersion("1.42.1", createStringList("1.7.10")));
        qm.addVersion(new QuickModVersion("1.42.2", createStringList("1.7.10")));
        qm.addVersion(new QuickModVersion("1.43.0", createStringList("1.7.11")));
        assertEquals(qm.getVersions().size(), 4);
        qm.removeVersion(new QuickModVersion("1.42.1", createStringList("1.7.10")));
        assertEquals(qm.getVersions().size(), 3);
        assertNull(qm.findVersion("1.42.1"));
        qm.removeVersion("1.42.2");
        assertEquals(qm.getVersions().size(), 2);
        assertNull(qm.findVersion("1.42.2"));
        qm.clearVersions();
        assertEquals(qm.getVersions(), new LinkedList<QuickModVersion>());
        assertEquals(qm.getVersions().size(), 0);
    }

    @Test
    public void testFindContainsVersions() {
        QuickMod qm = createQuickMod();

        assertFalse(qm.containsVersion("12.55"));
        assertNull(qm.findVersion("12.55"));
        qm.addVersion(new QuickModVersion("12.55", createStringList("1.4.7")));
        assertTrue(qm.containsVersion("12.55"));
        assertNotNull(qm.findVersion("12.55"));
        qm.clearVersions();
        assertFalse(qm.containsVersion("12.55"));
        assertNull(qm.findVersion("12.55"));
    }
}
