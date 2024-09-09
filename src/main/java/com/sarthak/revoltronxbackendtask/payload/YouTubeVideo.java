package com.sarthak.revoltronxbackendtask.payload;

import lombok.Data;

@Data
public class YouTubeVideo {
    private String title;
    private String url;
    private String thumbnailUrl;
    private String likes;
    private String views;
    private String uploadDate;
}
