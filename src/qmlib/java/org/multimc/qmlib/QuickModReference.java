package org.multimc.qmlib;

public class QuickModReference {
    private String type;
    private String uid;
    private Interval version;

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
