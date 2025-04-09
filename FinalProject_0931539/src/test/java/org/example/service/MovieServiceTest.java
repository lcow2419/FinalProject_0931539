package org.example.service;

import org.example.model.FavoriteMovie;
import org.example.repository.FavoriteMovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class MovieServiceTest {

    @Mock
    private FavoriteMovieRepository repository;

    @InjectMocks
    private MovieService movieService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Inject mock RestTemplate and config values using reflection
        ReflectionTestUtils.setField(movieService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(movieService, "apiKey", "fakeApiKey");
        ReflectionTestUtils.setField(movieService, "apiUrl", "https://api.themoviedb.org/3");
    }

    @Test
    public void testGetFavorites_ReturnsList() {
        when(repository.findAll()).thenReturn(List.of());

        List<FavoriteMovie> favorites = movieService.getFavorites();

        verify(repository).findAll();
        assertNotNull(favorites);
    }

    @Test
    public void testAddToFavorites_SavesMovie() {
        FavoriteMovie movie = new FavoriteMovie();
        movieService.addToFavorites(movie);

        verify(repository).save(movie);
    }

    @Test
    public void testRemoveFromFavorites_DeletesById() {
        movieService.removeFromFavorites(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    public void testIsFavorite_WhenMovieExists() {
        when(repository.findByTmdbId(1L)).thenReturn(Optional.of(new FavoriteMovie()));

        boolean result = movieService.isFavorite(1L);

        assertTrue(result);
    }

    @Test
    public void testIsFavorite_WhenMovieDoesNotExist() {
        when(repository.findByTmdbId(1L)).thenReturn(Optional.empty());

        boolean result = movieService.isFavorite(1L);

        assertFalse(result);
    }

    @Test
    public void testGetTrendingMovies_CallsApi() {
        Map<String, Object> mockMovie = Map.of("id", 1, "title", "Sherin Movie");
        Map<String, Object> mockResponse = Map.of("results", List.of(mockMovie));

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockResponse);

        List<Map<String, Object>> results = movieService.getTrendingMovies();

        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("Sherin Movie", results.get(0).get("title"));
    }

    @Test
    public void testGetMovieDetails_ReturnsMovie() {
        Map<String, Object> mockMovie = Map.of("id", 123L, "title", "Sherin's Movie");

        when(restTemplate.getForObject(anyString(), eq(Map.class))).thenReturn(mockMovie);

        Map result = movieService.getMovieDetails(123L);

        assertNotNull(result);
        assertEquals("Sherin's Movie", result.get("title"));
    }
}

