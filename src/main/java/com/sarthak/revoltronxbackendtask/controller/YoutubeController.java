package com.sarthak.revoltronxbackendtask.controller;

import com.sarthak.revoltronxbackendtask.payload.YouTubeVideo;
import com.sarthak.revoltronxbackendtask.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YoutubeController {

    private final YoutubeService youtubeService;

    @GetMapping("/youtube/search")
    public List<YouTubeVideo> search(@RequestParam String query) {
        return youtubeService.searchVideos(query);
    }
}
