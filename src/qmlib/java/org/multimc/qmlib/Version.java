package org.multimc.qmlib;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class Version {

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Version)) {
            return false;
        }
        return Version.compare(this, (Version) other) == 0;
    }

    public Version(String str) {
        String[] parts = str.split("\\.");
        for (String part : parts) {
            try {
                Integer integer = Integer.parseInt(part);
                sections.add(new Section(part, integer));
            } catch (NumberFormatException ex) {
                sections.add(new Section(part));
            }
        }
    }

    private List<Section> sections = new LinkedList<>();

    public static int compare(Version version1, Version version2) {
        Section dummy = new Section("0", 0);
        for (int i = 0; i < Math.max(version1.sections.size(), version2.sections.size()); ++i) {
            Section section1 = (i >= version1.sections.size()) ? dummy : version1.sections.get(i);
            Section section2 = (i >= version2.sections.size()) ? dummy : version2.sections.get(i);
            int result = section1.compare(section2);
            if (result != 0) {
                return result;
            }
        }

        return 0;
    }

    public static int compare(String v1, String v2) {
        return compare(new Version(v1), new Version(v2));
    }

    public List<Section> getSections() {
        return sections;
    }

    public static class Section {
        public Section(String str, int num) {
            this.string = str;
            this.number = num;
            this.numValid = true;
        }
        public Section(String str) {
            this.string = str;
            this.numValid = false;
        }

        public String string;
        public int number;
        public boolean numValid;

        public int compare(Section other) {
            return numValid && other.numValid ? Integer.compare(number, other.number) : string.compareTo(other.string);
        }
    }

    @Override
    public String toString() {
        List<String> parts = new LinkedList<>();
        for (Section section : this.sections) {
            parts.add(section.numValid ? Integer.toString(section.number) : section.string);
        }
        return StringUtils.join(parts, ".");
    }
}
