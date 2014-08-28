package org.multimc.qmlib;

import org.junit.Test;
import static org.junit.Assert.*;

public class VersionTest {
    @Test
    public void testParse() {
        Version version = new Version("1.02.3.04");

        assertEquals(4, version.getSections().size());

        assertEquals(true, version.getSections().get(0).numValid);
        assertEquals(1, version.getSections().get(0).number);
        assertEquals("1", version.getSections().get(0).string);

        assertEquals(true, version.getSections().get(1).numValid);
        assertEquals(2, version.getSections().get(1).number);
        assertEquals("02", version.getSections().get(1).string);

        assertEquals(true, version.getSections().get(2).numValid);
        assertEquals(3, version.getSections().get(2).number);
        assertEquals("3", version.getSections().get(2).string);

        assertEquals(true, version.getSections().get(3).numValid);
        assertEquals(4, version.getSections().get(3).number);
        assertEquals("04", version.getSections().get(3).string);
    }

    @Test
    public void testEquals() {
        assertEquals(new Version("1.2.3.4"), new Version("1.2.3.4"));
        assertEquals(new Version("01.2.03.4"), new Version("1.02.3.04"));
        Version version = new Version("01.2.03.4");
        assertEquals(version, version);
        assertFalse(version.equals(new String()));
        assertFalse(version.equals(new Version("3.4.5.544.2")));
    }

    @Test
    public void testCompare() {
        Version lowest = new Version("1.2");
        Version middle = new Version("3.4.1");
        Version highest = new Version("3.4.2");

        assertEquals(-1, Version.compare(lowest, middle));
        assertEquals(1, Version.compare(middle, lowest));
        assertEquals(-1, Version.compare(lowest, highest));
        assertEquals(1, Version.compare(highest, lowest));
        assertEquals(0, Version.compare(middle, middle));
        assertEquals(0, Version.compare(highest, highest));
        assertEquals(0, Version.compare(new Version("03.04.02"), highest));

        assertEquals(-1, Version.compare("1.2", "3.4.1"));
        assertEquals(1, Version.compare("3.4.1", "1.2"));
        assertEquals(-1, Version.compare("1.2", "3.4.2"));
        assertEquals(1, Version.compare("3.4.2", "1.2"));
        assertEquals(0, Version.compare("3.4.1", "3.4.1"));
        assertEquals(0, Version.compare("3.4.2", "3.4.2"));
        assertEquals(0, Version.compare("03.04.02", "3.4.2"));

        assertEquals(-1, Version.compare("1.a", "1.b"));
        assertEquals(1, Version.compare("1.b", "1.a"));
        assertEquals(-1, Version.compare("1.a.99999", "1.b"));
        assertEquals(0, Version.compare("1.a.99999", "1.a.099999"));
        assertTrue(Version.compare("1.0.99999", "1.a.099999") < 0);

        assertEquals(0, Version.compare("1.0", "1.0.0.0"));
        assertEquals(0, Version.compare("1.0.0.0", "1.0"));
    }

    @Test
    public void testToString() {
        assertEquals("1.2.3", new Version("01.2.03").toString());
        assertEquals("1.b.3", new Version("01.b.03").toString());
    }
}
