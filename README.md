# Ranking Strategy Overview
In the system, the ranking strategy involves a combination of two key factors:
- relevance 
- popularity

# Relevance Scoring:

- The relevance of search results is determined based on how closely the search query matches with the result's title and snippet (description).
- For each result, the title and snippet are checked for the presence of the query. If the query is found in the title, it adds 10 points to the relevance score. If found in the snippet, it adds 5 points.
- This gives a higher weight to titles matching the query, as they are more likely to be relevant to the searcher's intent.

# Popularity Scoring:
- For YouTube video results, popularity is assessed based on the number of views and likes.
- The score is calculated by dividing the view count by 1000 and the like count by 100, ensuring that highly viewed and liked videos receive a higher score.

# Combined Score:

- The final ranking is determined by combining the relevance and popularity scores.
- A weighted average is used, where 70% weight is given to relevance and 30% to popularity.
- This ensures that search results are primarily ordered by relevance, but popular videos receive a boost in the ranking.
- The combined results are sorted in descending order based on their final score, ensuring that the most relevant and popular results appear at the top.

# SETUP INSTRUCTIONS
- clone the repo
```http
  git clone https://github.com/your-repo/revoltronx-backend-task.git
  cd revoltronx-backend-task
```

- Setup api keys
- ```http
  export GOOGLE_API_KEY=your-google-api-key
  export GOOGLE_CUSTOM_SEARCH_ENGINE_ID=your-search-engine-id
  export YOUTUBE_API_KEY=your-youtube-api-key
  ```
- install dependencies
 `mvn clean install`

- run the application on local host 8080
`mvn spring-boot:run`

- Access the endpoints 

  Google Search: `http://localhost:8080/google/search?query=example`
  YouTube Search: `http://localhost:8080/youtube/search?query=example`
  Combined Search: `http://localhost:8080/search?query=example`




