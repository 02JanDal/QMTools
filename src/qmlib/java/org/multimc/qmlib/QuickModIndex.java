package org.multimc.qmlib;

import java.net.URI;
import java.util.List;

public class QuickModIndex {
    private URI baseUrl;
    private String repo;
    private List<Item> index;

    public URI getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(URI baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public List<Item> getIndex() {
        return index;
    }

    public void setIndex(List<Item> index) {
        this.index = index;
    }

    public static class Item {
        private URI url;
        private String uid;

        public Item(URI url, String uid) {
            this.url = url;
            this.uid = uid;
        }

        public URI getUrl() {
            return url;
        }

        public void setUrl(URI url) {
            this.url = url;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
