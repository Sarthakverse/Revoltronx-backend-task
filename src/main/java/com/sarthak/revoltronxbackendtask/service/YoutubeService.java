package com.sarthak.revoltronxbackendtask.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sarthak.revoltronxbackendtask.payload.YouTubeVideo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeService {

    @Value("${youtube.api.key}")
    private String YOUTUBE_API_KEY;
    private final RestTemplate restTemplate;

    private static final String YOUTUBE_SEARCH_URL = "https://www.googleapis.com/youtube/v3/search";
    private static final String YOUTUBE_VIDEO_DETAILS_URL = "https://www.googleapis.com/youtube/v3/videos";

    public List<YouTubeVideo> searchVideos(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String searchUrl = YOUTUBE_SEARCH_URL + "?part=snippet&q=" + query + "&key=" + YOUTUBE_API_KEY + "&type=video";

        String searchResponse = restTemplate.getForObject(searchUrl, String.class);
        if (searchResponse == null) {
            return null;
        }
        JsonObject searchJson = JsonParser.parseString(searchResponse).getAsJsonObject();
        JsonArray items = searchJson.getAsJsonArray("items");

        List<String> videoIds = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            JsonObject item = items.get(i).getAsJsonObject();
            String videoId = item.getAsJsonObject("id").get("videoId").getAsString();
            videoIds.add(videoId);
        }


        return getVideoDetails(videoIds);
    }

    private List<YouTubeVideo> getVideoDetails(List<String> videoIds) {
        RestTemplate restTemplate = new RestTemplate();
        String videoIdParam = String.join(",", videoIds);
        String videoDetailsUrl = YOUTUBE_VIDEO_DETAILS_URL + "?part=snippet,statistics&id=" + videoIdParam + "&key=" + YOUTUBE_API_KEY;

        String videoDetailsResponse = restTemplate.getForObject(videoDetailsUrl, String.class);
        if (videoDetailsResponse == null) {
            return null;
        }
        JsonObject videoJson = JsonParser.parseString(videoDetailsResponse).getAsJsonObject();
        JsonArray items = videoJson.getAsJsonArray("items");

        List<YouTubeVideo> videos = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            JsonObject item = items.get(i).getAsJsonObject();
            JsonObject snippet = item.getAsJsonObject("snippet");
            JsonObject statistics = item.getAsJsonObject("statistics");

            YouTubeVideo video = new YouTubeVideo();
            video.setTitle(snippet.get("title").getAsString());
            video.setUrl("https://www.youtube.com/watch?v=" + item.get("id").getAsString());
            video.setThumbnailUrl(snippet.getAsJsonObject("thumbnails").getAsJsonObject("high").get("url").getAsString());
            video.setLikes(statistics.get("likeCount").getAsString());
            video.setViews(statistics.get("viewCount").getAsString());
            video.setUploadDate(snippet.get("publishedAt").getAsString());

            videos.add(video);
        }

        return videos;
    }
}
