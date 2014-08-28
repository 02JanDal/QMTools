package org.multimc.qmlib;

public class Interval {

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Interval)) {
            return false;
        }
        Interval interval = (Interval) other;
        if (interval.isPlain() != isPlain()) {
            return false;
        }
        if (isPlain()) {
            return interval.plain.equals(plain);
        } else {
            return interval.lower.equals(lower) && interval.upper.equals(upper) && interval.lowerInclusive == lowerInclusive && interval.upperInclusive == upperInclusive;
        }
    }

    @Override
    public int hashCode() {
        if (this.plain != null) {
            return this.plain.hashCode();
        } else {
            return lower.hashCode() ^ upper.hashCode();
        }
    }

    private Version lower = null;
    private Version upper = null;
    private boolean lowerInclusive = true;
    private boolean upperInclusive = false;
    private Version plain = null;
    
    public static Interval fromString(String str) {
        Interval interval = new Interval();
        try {
        if (str.length() < 3) {
            throw new IntervalParseException("Interval needs to be at least 3 characters long (" + str + ")");
        }
        if (str.startsWith("(")) {
            interval.setLowerInclusive(false);
        } else if (str.startsWith("[")) {
            interval.setLowerInclusive(true);
        } else {
            throw new IntervalParseException("Interval needs to start with either '(' or '[' (" + str + ")");
        }
        if (str.endsWith(")")) {
            interval.setUpperInclusive(false);
        } else if (str.endsWith("]")) {
            interval.setUpperInclusive(true);
        } else {
            throw new IntervalParseException("Interval needs to end with either ')' or ']' (" + str + ")");
        }
        if (!str.contains(",")) {
            throw new IntervalParseException("Interval needs to contain a ',' (" + str + ")");
        }
        } catch (IntervalParseException ex) {
            return new Interval(new Version(str));
        }
        str = str.substring(1, str.length() - 1);
        int midPos = str.indexOf(',');
        interval.setLower(new Version(str.substring(0, midPos)));
        interval.setUpper(new Version(str.substring(midPos + 1)));
        return interval;
    }
    
    public Interval(Version lower, Version upper) {
        this.lower = lower;
        this.upper = upper;
    }
    public Interval(Version plain) {
        this.plain = plain;
    }
    public Interval() {
    }

    public Version getLower() {
        return lower;
    }

    public void setLower(Version lower) {
        this.lower = lower;
    }

    public Version getUpper() {
        return upper;
    }

    public void setUpper(Version upper) {
        this.upper = upper;
    }

    public boolean isLowerInclusive() {
        return lowerInclusive;
    }

    public void setLowerInclusive(boolean lowerInclusive) {
        this.lowerInclusive = lowerInclusive;
    }

    public boolean isUpperInclusive() {
        return upperInclusive;
    }

    public void setUpperInclusive(boolean upperInclusive) {
        this.upperInclusive = upperInclusive;
    }

    public boolean isPlain() {
        return this.plain != null;
    }

    public Version getPlain() {
        return this.plain;
    }

    @Override
    public String toString() {
        if (this.plain != null) {
            return this.plain.toString();
        }
        StringBuilder builder = new StringBuilder();
        builder.append(this.lowerInclusive ? '[' : '(');
        builder.append(this.lower.toString());
        builder.append(',');
        builder.append(this.upper.toString());
        builder.append(this.upperInclusive ? ']' : ')');
        return builder.toString();
    }
}
