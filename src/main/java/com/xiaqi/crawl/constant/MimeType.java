package com.xiaqi.crawl.constant;

public enum MimeType {

    IMAGES("images"),
    VIDEO("video"),
    AUDIO("audio"),
    TEXT("text");

    private String name;

    MimeType(String name) {
        this.name = name;
    }
}
