package org.multimc.qmlib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class QuickModVersionTest extends BaseQMTest {
    @Test
    public void testGet() {
        QuickModVersion version = BaseQMTest.createQuickModVersion();

        assertEquals(version.getName(), "1.42-beta");
        assertEquals(version.getVersionRaw(), "1.42");
        assertEquals(version.getForgeCompat(), Interval.fromString("[1.1.1.1,2.2.2)"));
        assertEquals(version.getLiteloaderCompat(), Interval.fromString("5"));
        assertEquals(version.getInstallType(), QuickModVersion.InstallType.liteloaderMod);
        assertEquals(version.getMcCompat(), BaseQMTest.createStringList("1.4.6", "1.8.1"));
        assertEquals(version.getSha1(), "7283abd87d");
        assertEquals(version.getType(), "Beta");
    }

    @Test
    public void testVersionRaw() {
        QuickModVersion version = BaseQMTest.createQuickModVersion();

        assertEquals(version.getName(), "1.42-beta");
        assertEquals(version.getVersion(), "1.42");
        assertEquals(version.getVersionRaw(), "1.42");
        version.setVersion(null);
        assertNull(version.getVersionRaw());
        assertEquals(version.getVersion(), version.getName());
    }

    @Test
    public void testEquals() {
        QuickModVersion modified = BaseQMTest.createQuickModVersion();
        modified.setType("hi");

        assertEquals(BaseQMTest.createQuickModVersion(), BaseQMTest.createQuickModVersion());
        assertFalse(BaseQMTest.createQuickModVersion().equals(modified));
    }
}
