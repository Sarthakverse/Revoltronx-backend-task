package com.sarthak.revoltronxbackendtask.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sarthak.revoltronxbackendtask.payload.UnifiedSearchResult;
import com.sarthak.revoltronxbackendtask.service.SearchCombineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchCombineController {

    private final SearchCombineService searchCombineService;

    @GetMapping("/search")
    public List<UnifiedSearchResult> search(@RequestParam String query, @RequestParam(defaultValue = "10") int numResults) throws JsonProcessingException {
        return searchCombineService.getCombinedResults(query, numResults);
    }
}