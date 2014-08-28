package org.multimc.qmlib;

import org.junit.Test;
import static org.junit.Assert.*;

public class IntervalTest {
    @Test
    public void testParse() {
        Interval interval = Interval.fromString("[1.2.3,4.5.6)");

        assertEquals(new Version("1.2.3"), interval.getLower());
        assertEquals(new Version("4.5.6"), interval.getUpper());
        assertEquals(true, interval.isLowerInclusive());
        assertEquals(false, interval.isUpperInclusive());
        assertEquals(false, interval.isPlain());

        interval = Interval.fromString("(1,]");

        assertEquals(new Version("1"), interval.getLower());
        assertEquals(new Version(""), interval.getUpper());
        assertEquals(false, interval.isLowerInclusive());
        assertEquals(true, interval.isUpperInclusive());
        assertEquals(false, interval.isPlain());

        interval = Interval.fromString("42.42");

        assertEquals(new Version("42.42"), interval.getPlain());
        assertEquals(true, interval.isPlain());
    }

    @Test
    public void testEquals() {
        assertTrue(Interval.fromString("[01.2.03,00004.04.4]").equals(Interval.fromString("[1.2.3,4.4.4]")));
        assertTrue(Interval.fromString("[1.1.1.1,2.2.2)").equals(Interval.fromString("[1.1.1.1,2.2.2)")));
    }
}
