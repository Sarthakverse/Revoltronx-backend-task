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

- Setup application.properties file
- ```http
  youtube.api.key = your-youtube-api-key
  google.custom.search.engine.id = your-search-engine-id
  google.api.key = google-api-key
  ```
- install dependencies
 `mvn clean install`

- run the application on local host 8080
`mvn spring-boot:run`

- Access the endpoints 

  - Google Search: `http://localhost:8080/google/search?query=example`
  - YouTube Search: `http://localhost:8080/youtube/search?query=example`
  - Combined Search: `http://localhost:8080/search?query=example`

# Screenshots
![Screenshot 2024-09-12 011340](https://github.com/user-attachments/assets/cb5e3b2b-d48d-43f4-9452-3ed6ea7a1729)
![Screenshot 2024-09-12 011404](https://github.com/user-attachments/assets/8c5cdafe-f06d-4ed5-a5d7-39b1203713f8)
![Screenshot 2024-09-12 011414](https://github.com/user-attachments/assets/3f3a4013-8aaa-499f-a70b-10ed7c0da037)
![Screenshot 2024-09-12 011424](https://github.com/user-attachments/assets/9d92cb19-73a4-46c7-94ad-533bd6da9e6b)




