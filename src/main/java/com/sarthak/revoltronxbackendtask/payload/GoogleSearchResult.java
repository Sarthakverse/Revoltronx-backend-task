package com.sarthak.revoltronxbackendtask.payload;

import lombok.Data;

@Data
public class GoogleSearchResult {
    private String title;
    private String url;
    private String snippet;
    private String thumbnailUrl;
}
