package br.com.pedro.sfmovies.service;

import br.com.pedro.sfmovies.model.MovieLocation;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieLocationService {

    private final WebClient webClient;

    public MovieLocationService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://data.sfgov.org/resource/yitu-d5am.json").build();
    }

    public List<MovieLocation> getAllMovies() {
        return webClient.get()
                .retrieve()
                .bodyToFlux(MovieLocation.class)
                .collectList()
                .block();
    }

    public List<MovieLocation> filterByTitle(String query) {
        return getAllMovies().stream()
                .filter(m -> m.getTitle() != null && m.getTitle().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
