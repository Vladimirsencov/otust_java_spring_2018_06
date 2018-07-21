package ru.otus.hw06.shell;

import org.springframework.stereotype.Service;
import ru.otus.hw06.models.Author;
import ru.otus.hw06.models.Genre;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
class ShellParametersConverter {

    List<Author> authorsString2List(String authorsStr) {
        if (authorsStr == null) {
            return null;
        }
        return Arrays.stream(authorsStr.split(",")).filter(s -> !s.replaceAll(" ", "").isEmpty()).map(s -> new Author(null, s)).collect(Collectors.toList());
    }

    List<Genre> genresString2List(String genresStr) {
        if (genresStr == null) {
            return null;
        }
        return Arrays.stream(genresStr.split(",")).filter(s -> !s.replaceAll(" ", "").isEmpty()).map(s ->new Genre(null, s)).collect(Collectors.toList());
    }
}
