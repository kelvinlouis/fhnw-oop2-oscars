package ch.fhnw.oop2.project.model;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Kelvin on 07-May-16.
 */
public class MovieService implements DataService<Movie> {
    private final String FILE_PATH = "../resources/movies.csv";

    private static MovieService instance = new MovieService();

    private MovieService() {}

    public static MovieService getInstance() {
        return instance;
    }

    @Override
    public List<Movie> getAll() {
        try {
            InputStream stream = getClass().getResourceAsStream(FILE_PATH);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));

            return buffer
                    .lines()
                    .skip(1)
                    .map(s -> s.split(";"))
                    .map(parts -> mapLineToMovie(parts))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public void save(List<Movie> list) {
        List<String> lines = new ArrayList<>();
        File file = new File(FILE_PATH);

        lines.add(createCSVHeaderLine());
        list.forEach(movie -> lines.add(createCSVMovieLine(movie)));

        try {
            Files.write(file.toPath(), lines, Charset.forName("UTF-8"));
        } catch(IOException exception) {

        }
    }

    @Override
    public Movie createItem() {
        return null;
    }

    private Movie mapLineToMovie(String[] parts) {
        Movie movie = new Movie();

        movie.setId(Integer.parseInt(parts[0]));
        movie.setTitle(parts[1]);
        movie.setYearOfAward(Integer.parseInt(parts[2]));
        movie.setDirector(parts[3]);
        movie.setMainActor(parts[4]);
        movie.setTitleEnglish(parts[5]);
        movie.setYearOfProduction(Integer.parseInt(parts[6]));
        movie.getCountry().addAll(parts[7].split("/"));
        movie.setDuration(Integer.parseInt(parts[8]));
        movie.setFsk(Integer.parseInt(parts[9]));
        movie.setGenre(parts[10]);

        try {
            LocalDate startDate = LocalDate.parse(parts[11], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            movie.setStartDate(Optional.of(startDate));
        } catch (DateTimeParseException ex) {
            movie.setStartDate(Optional.empty());
        }

        movie.setNumberOfOscars(Integer.parseInt(parts[12]));

        return movie;
    }

    private String createCSVHeaderLine() {
        return "#id;Title;yearOfAward;director;mainActor;titleEnglish;yearOfProduction;country;duration;fsk;genre;startDate;numberOfOscars";
    }

    private String createCSVMovieLine(Movie movie) {
        StringBuilder builder = new StringBuilder();

        builder
                .append(movie.getId()).append(";")
                .append(movie.getTitle()).append(";")
                .append(movie.getYearOfAward()).append(";")
                .append(movie.getDirector()).append(";")
                .append(movie.getMainActor()).append(";")
                .append(movie.getTitleEnglish()).append(";")
                .append(movie.getYearOfProduction()).append(";")
                .append(String.join("/", movie.getCountry())).append(";")
                .append(movie.getDuration()).append(";")
                .append(movie.getFsk()).append(";")
                .append(movie.getGenre()).append(";")
                .append(movie.getStartDate().isPresent() ? movie.getStartDate().toString() : "-")
                .append(movie.getNumberOfOscars());

        return builder.toString();
    }
}
