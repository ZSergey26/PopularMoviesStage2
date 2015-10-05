package com.locdevelop.popularmovies.network.data;

/**
 * Created by Sergei Zarochentsev on 01.10.2015.
 */
public class Trailer {
    private String key;
    private String name;
    private String site;
    private String iso_639_1;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    private static final String YOUTUBE_BASE = "https://www.youtube.com/watch?v=";
    @Override
    public String toString() {
        return YOUTUBE_BASE + key;
    }
}
