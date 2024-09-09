package com.sarthak.revoltronxbackendtask.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sarthak.revoltronxbackendtask.payload.GoogleSearchResult;
import com.sarthak.revoltronxbackendtask.payload.UnifiedSearchResult;
import com.sarthak.revoltronxbackendtask.payload.YouTubeVideo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchCombineService {

    private final GoogleSearchService googleSearchService;
    private final YoutubeService youtubeService;
//  private final PubMedService pubMedService;

    public List<UnifiedSearchResult> getCombinedResults(String query, int numResults) throws JsonProcessingException {

        List<GoogleSearchResult> googleResults = googleSearchService.search(query, numResults);


        List<YouTubeVideo> youtubeResults = youtubeService.searchVideos(query);


//     List<PubMedResult> pubMedResults = pubMedService.searchArticles(query, numResults);


        List<UnifiedSearchResult> unifiedResults = new ArrayList<>();
        for (GoogleSearchResult googleResult : googleResults) {
            UnifiedSearchResult unifiedResult = new UnifiedSearchResult();
            unifiedResult.setTitle(googleResult.getTitle());
            unifiedResult.setUrl(googleResult.getUrl());
            unifiedResult.setSnippet(googleResult.getSnippet());
            unifiedResult.setThumbnailUrl(googleResult.getThumbnailUrl());
            unifiedResult.setSource("Google");
            unifiedResults.add(unifiedResult);
        }


        for (YouTubeVideo youtubeVideo : youtubeResults) {
            UnifiedSearchResult unifiedResult = new UnifiedSearchResult();
            unifiedResult.setTitle(youtubeVideo.getTitle());
            unifiedResult.setUrl(youtubeVideo.getUrl());
            unifiedResult.setSnippet(youtubeVideo.getTitle());
            unifiedResult.setThumbnailUrl(youtubeVideo.getThumbnailUrl());
            unifiedResult.setLikes(youtubeVideo.getLikes());
            unifiedResult.setViews(youtubeVideo.getViews());
            unifiedResult.setUploadDate(youtubeVideo.getUploadDate());
            unifiedResult.setSource("YouTube");
            unifiedResults.add(unifiedResult);
        }

//        // Convert PubMed results to UnifiedSearchResult
//        for (PubMedResult pubMedResult : pubMedResults) {
//            UnifiedSearchResult unifiedResult = new UnifiedSearchResult();
//            unifiedResult.setTitle(pubMedResult.getTitle());
//            unifiedResult.setUrl(pubMedResult.getUrl());
//            unifiedResult.setSnippet(pubMedResult.getSnippet());
//            unifiedResult.setSource("PubMed");
//            unifiedResults.add(unifiedResult);
//        }

        unifiedResults.sort((r1, r2) -> {
            int score1 = calculateScore(r1);
            int score2 = calculateScore(r2);
            return Integer.compare(score2, score1);
        });

        return unifiedResults;
    }

    private int calculateScore(UnifiedSearchResult result) {
        int relevanceScore = calculateRelevanceScore(result.getTitle(), result.getSnippet());
        int popularityScore = calculatePopularityScore(result.getViews(), result.getLikes());
        return (int) (0.7 * relevanceScore + 0.3 * popularityScore); // Adjust weights as needed
    }

    private int calculateRelevanceScore(String title, String snippet) {

        int score = 0;
        String query = "";
        if (title != null && title.toLowerCase().contains(query.toLowerCase())) {
            score += 10;
        }
        if (snippet != null && snippet.toLowerCase().contains(query.toLowerCase())) {
            score += 5;
        }
        return score;
    }

    private int calculatePopularityScore(String views, String likes) {

        int viewCount = parseIntOrDefault(views);
        int likeCount = parseIntOrDefault(likes);

        return viewCount / 1000 + likeCount / 100;
    }

    private int parseIntOrDefault(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
