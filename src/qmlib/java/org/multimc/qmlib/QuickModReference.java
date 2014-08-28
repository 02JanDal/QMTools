package org.multimc.qmlib;

public class QuickModReference {
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof QuickModReference)) {
            return false;
        }
        QuickModReference ref = (QuickModReference) other;
        return ref.type.equals(type) && ref.uid.equals(uid) && ref.version.equals(version);
    }

    private String type;
    private String uid;
    private Interval version;

    public QuickModReference() {
    }

    public QuickModReference(String type, String uid, Interval version) {
        this.type = type;
        this.uid = uid;
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Interval getVersion() {
        return version;
    }

    public void setVersion(Interval version) {
        this.version = version;
    }
    
    public void setVersion(String version) {
        this.version = Interval.fromString(version);
    }
    
}
