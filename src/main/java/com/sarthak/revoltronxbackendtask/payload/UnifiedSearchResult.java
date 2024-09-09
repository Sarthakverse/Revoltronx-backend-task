package com.sarthak.revoltronxbackendtask.payload;

import lombok.Data;

@Data
public class UnifiedSearchResult {
    private String title;
    private String url;
    private String snippet;
    private String thumbnailUrl;
    private String likes;
    private String views;
    private String uploadDate;
    private String source;

}