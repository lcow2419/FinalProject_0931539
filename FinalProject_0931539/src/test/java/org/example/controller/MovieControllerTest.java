package org.example.controller;

import org.example.model.FavoriteMovie;
import org.example.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @Mock
    private Model model;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIndex_ReturnsIndexView() {
        when(movieService.getTrendingMovies()).thenReturn(List.of());

        String viewName = movieController.index(model);

        verify(movieService).getTrendingMovies();
        verify(model).addAttribute(eq("movies"), any());
        assertEquals("index", viewName);
    }

    @Test
    public void testMovieDetails_ReturnsDetailsView() {
        Long movieId = 1L;
        when(movieService.getMovieDetails(movieId)).thenReturn(Map.of());
        when(movieService.isFavorite(movieId)).thenReturn(true);

        String viewName = movieController.movieDetails(movieId, model);

        verify(movieService).getMovieDetails(movieId);
        verify(movieService).isFavorite(movieId);
        verify(model).addAttribute(eq("movie"), any());
        verify(model).addAttribute(eq("isFavorite"), eq(true));
        assertEquals("movie-details", viewName);
    }

    @Test
    public void testAddToFavorites_RedirectsToFavorites() {
        String viewName = movieController.addToFavorites(1L, "Movie Title", "/path", "Overview");

        verify(movieService).addToFavorites(any(FavoriteMovie.class));
        assertEquals("redirect:/favorites", viewName);
    }

    @Test
    public void testFavorites_ReturnsFavoritesView() {
        when(movieService.getFavorites()).thenReturn(List.of());

        String viewName = movieController.favorites(model);

        verify(movieService).getFavorites();
        verify(model).addAttribute(eq("favorites"), any());
        assertEquals("favorites", viewName);
    }

    @Test
    public void testRemoveFavorite_RedirectsToFavorites() {
        String viewName = movieController.removeFavorite(1L);

        verify(movieService).removeFromFavorites(1L);
        assertEquals("redirect:/favorites", viewName);
    }
}
