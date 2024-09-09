package com.sarthak.revoltronxbackendtask.controller;

import com.sarthak.revoltronxbackendtask.payload.GoogleSearchResult;
import com.sarthak.revoltronxbackendtask.service.GoogleSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoogleSearchController {


    private final GoogleSearchService googleSearchService;

    @GetMapping("/google/search")
    public List<GoogleSearchResult> search(@RequestParam String query,
                                           @RequestParam(defaultValue = "7") int numResults) {
        return googleSearchService.search(query, numResults);
    }
}
