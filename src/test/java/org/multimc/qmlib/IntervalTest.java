package org.multimc.qmlib;

import org.junit.Test;
import static org.junit.Assert.*;

public class IntervalTest {
    @Test
    public void testParse() {
        Interval interval = Interval.fromString("[1.2.3,4.5.6)");

        assertEquals(new Version("1.2.3"), interval.getLower());
        assertEquals(new Version("4.5.6"), interval.getUpper());
        assertTrue(interval.isLowerInclusive());
        assertFalse(interval.isUpperInclusive());
        assertFalse(interval.isPlain());

        interval = Interval.fromString("(1,]");

        assertEquals(new Version("1"), interval.getLower());
        assertEquals(new Version(""), interval.getUpper());
        assertFalse(interval.isLowerInclusive());
        assertTrue(interval.isUpperInclusive());
        assertFalse(interval.isPlain());

        interval = Interval.fromString("42.42");

        assertEquals(new Version("42.42"), interval.getPlain());
        assertTrue(interval.isPlain());

        interval = Interval.fromString("(1.2.3]");

        assertEquals(new Version("(1.2.3]"), interval.getPlain());
        assertTrue(interval.isPlain());

        interval = Interval.fromString("1.2.3,1.3.4]");

        assertEquals(new Version("1.2.3,1.3.4]"), interval.getPlain());
        assertTrue(interval.isPlain());

        interval = Interval.fromString("(1.2.3,4.2.5");

        assertEquals(new Version("(1.2.3,4.2.5"), interval.getPlain());
        assertTrue(interval.isPlain());
    }

    @Test
    public void testEqualsAndHashCode() {
        assertTrue(Interval.fromString("[01.2.03,00004.04.4]").equals(Interval.fromString("[1.2.3,4.4.4]")));
        assertTrue(Interval.fromString("[1.1.1.1,2.2.2)").equals(Interval.fromString("[1.1.1.1,2.2.2)")));
        assertTrue(Interval.fromString("1.4.32").equals(Interval.fromString("1.04.32")));

        Interval interval = Interval.fromString("[1.1.1.1,2.2.2)");
        assertTrue(interval.equals(interval));
        assertFalse(interval.equals(new String()));
        assertFalse(interval.equals(Interval.fromString("4.12.4")));
        assertFalse(interval.equals(Interval.fromString("(1.1.1.1,2.2.2)")));
        assertFalse(interval.equals(Interval.fromString("[1.1.2.1,2.2.2]")));
        assertFalse(interval.equals(Interval.fromString("[1.1.1.1,2.2.3)")));

        assertEquals(Interval.fromString("[1.1.1.1,2.2.2)").hashCode(), Interval.fromString("[1.1.1.1,2.2.2)").hashCode());
        assertFalse(Interval.fromString("[1.1.1.1,2.2.2)").hashCode() == Interval.fromString("1.3.54.3.33.34").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(Interval.fromString("(1.1.1.1,2.2.2]").toString(), "(1.1.1.1,2.2.2]");
        assertEquals(new Interval(new Version("3.4"), new Version("5.5")).toString(), "[3.4,5.5)");
        assertEquals(Interval.fromString("1.5.3").toString(), "1.5.3");
    }
}
