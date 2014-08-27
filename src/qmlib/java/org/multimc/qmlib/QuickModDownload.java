package org.multimc.qmlib;

public class QuickModDownload {
    private String url;
    private DownloadType downloadType = DownloadType.parallel;
    private int priority;
    private String hint;
    private String group;

    public QuickModDownload() {
    }
    
    public enum DownloadType {
        direct, parallel, sequential, encoded, maven
    }
    
    public static DownloadType downloadTypeFromString(String str) {
        switch (str) {
            case "direct":
                return DownloadType.direct;
            case "parallel":
                return DownloadType.parallel;
            case "sequential":
                return DownloadType.sequential;
            case "encoded":
                return DownloadType.encoded;
            case "maven":
                return DownloadType.maven;
        }
        throw new InvalidDownloadTypeException("Invalid downloadType: " + str);
    }
    
    public QuickModDownload(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DownloadType getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(DownloadType downloadType) {
        this.downloadType = downloadType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    
}
