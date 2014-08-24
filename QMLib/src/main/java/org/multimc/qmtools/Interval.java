package org.multimc.qmtools;

public class Interval {
    
    private String lower = "";
    private String upper = "";
    private boolean lowerInclusive = true;
    private boolean upperInclusive = false;
    
    public static Interval fromString(String str) throws IntervalParseException {
        Interval interval = new Interval();
        if (str.length() < 5) {
            throw new IntervalParseException("Interval needs to be at least 5 characters long (" + str + ")");
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
        str = str.substring(1, str.length() - 1);
        int midPos = str.indexOf(',');
        interval.setLower(str.substring(0, midPos));
        interval.setUpper(str.substring(midPos + 1));
        return interval;
    }
    
    public Interval(String lower, String upper) {
        this.lower = lower;
        this.upper = upper;
    }
    public Interval() {
    }

    public String getLower() {
        return lower;
    }

    public void setLower(String lower) {
        this.lower = lower;
    }

    public String getUpper() {
        return upper;
    }

    public void setUpper(String upper) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.lowerInclusive ? '[' : '(');
        builder.append(this.lower);
        builder.append(',');
        builder.append(this.upper);
        builder.append(this.upperInclusive ? ']' : ')');
        return builder.toString();
    }
}
