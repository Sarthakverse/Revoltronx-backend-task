package com.sarthak.revoltronxbackendtask.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sarthak.revoltronxbackendtask.payload.GoogleSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleSearchService {

    private final RestTemplate restTemplate;

    @Value("${google.api.key}")
    private String GOOGLE_API_KEY;

    @Value("${google.custom.search.engine.id}")
    private String GOOGLE_CUSTOM_SEARCH_ENGINE_ID;

    private static final String GOOGLE_SEARCH_URL = "https://www.googleapis.com/customsearch/v1";

    public List<GoogleSearchResult> search(String query, int numResults) {
        String searchUrl = GOOGLE_SEARCH_URL + "?key=" + GOOGLE_API_KEY
                + "&cx=" + GOOGLE_CUSTOM_SEARCH_ENGINE_ID
                + "&q=" + query
                + "&num=" + numResults;

        String searchResponse = restTemplate.getForObject(searchUrl, String.class);
        if (searchResponse == null) {
            throw new RuntimeException("Failed to get response from Google Custom Search API");
        }

        JsonObject searchJson = JsonParser.parseString(searchResponse).getAsJsonObject();

        JsonArray items = searchJson.has("items") ? searchJson.getAsJsonArray("items") : new JsonArray();

        List<GoogleSearchResult> results = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            JsonObject item = items.get(i).getAsJsonObject();
            GoogleSearchResult result = new GoogleSearchResult();
            result.setTitle(item.has("title") ? item.get("title").getAsString() : "No Title");
            result.setUrl(item.has("link") ? item.get("link").getAsString() : "No URL");
            result.setSnippet(item.has("snippet") ? item.get("snippet").getAsString() : "No Snippet");

            if (item.has("pagemap")) {
                JsonObject pagemap = item.getAsJsonObject("pagemap");
                if (pagemap.has("cse_thumbnail")) {
                    JsonArray thumbnails = pagemap.getAsJsonArray("cse_thumbnail");
                    if (!thumbnails.isEmpty()) {
                        JsonObject thumbnail = thumbnails.get(0).getAsJsonObject();
                        result.setThumbnailUrl(thumbnail.has("src") ? thumbnail.get("src").getAsString() : null);
                    }
                }
            }
            results.add(result);
        }

        results.sort((r1, r2) -> {
            int score1 = calculateScore(r1, query);
            int score2 = calculateScore(r2, query);
            return Integer.compare(score2, score1);
        });

        return results;
    }

    private int calculateScore(GoogleSearchResult result, String query) {
        return calculateRelevanceScore(result.getTitle(), result.getSnippet(), query);
    }

    private int calculateRelevanceScore(String title, String snippet, String query) {
        int score = 0;
        if (title != null && title.toLowerCase().contains(query.toLowerCase())) {
            score += 10;
        }
        if (snippet != null && snippet.toLowerCase().contains(query.toLowerCase())) {
            score += 5;
        }
        return score;
    }


}
