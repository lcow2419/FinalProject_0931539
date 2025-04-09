package org.example.service;

import org.example.model.FavoriteMovie;
import org.example.repository.FavoriteMovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Service layer: handles TMDb API calls and favorite movie logic.
 */
@Service
public class MovieService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final FavoriteMovieRepository repository;

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    public MovieService(FavoriteMovieRepository repository) {
        this.repository = repository;
    }

    // Fetch trending movies from TMDb
    public List<Map<String, Object>> getTrendingMovies() {
        String url = apiUrl + "/trending/movie/week?api_key=" + apiKey;
        Map response = restTemplate.getForObject(url, Map.class);
        return (List<Map<String, Object>>) response.get("results");
    }

    // Get movie details from TMDb
    public Map getMovieDetails(Long movieId) {
        String url = apiUrl + "/movie/" + movieId + "?api_key=" + apiKey;
        return restTemplate.getForObject(url, Map.class);
    }

    // Favorites operations
    public List<FavoriteMovie> getFavorites() {
        return repository.findAll();
    }

    public void addToFavorites(FavoriteMovie movie) {
        repository.save(movie);
    }

    public void removeFromFavorites(Long id) {
        repository.deleteById(id);
    }

    public boolean isFavorite(Long tmdbId) {
        return repository.findByTmdbId(tmdbId).isPresent();
    }
}
