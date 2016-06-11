package ch.fhnw.oop2.project.service;

import ch.fhnw.oop2.project.model.Movie;

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
    private final String FILE_PATH = "/movies.csv";
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private List<Movie> list;

    private static MovieService instance = new MovieService();

    private MovieService() {}

    public static MovieService getInstance() {
        return instance;
    }

    @Override
    public List<Movie> getAll() {
        if (list != null) {
            return list;
        }

        try {
            InputStream stream = getClass().getResourceAsStream(FILE_PATH);
            BufferedReader buffer = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));

            list = buffer
                    .lines()
                    .skip(1)
                    .map(s -> s.split(";"))
                    .map(parts -> mapLineToMovie(parts))
                    .collect(Collectors.toList());

            return list;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public void save(File file, List<Movie> list) throws IOException {
        if (file == null) return;

        List<String> lines = new ArrayList<>();

        lines.add(createCSVHeaderLine());
        list.forEach(movie -> lines.add(createCSVMovieLine(movie)));

        Files.write(file.toPath(), lines, Charset.forName("UTF-8"));
    }

    public void save(File file) throws IOException {
        save(file, this.list);
    }

    @Override
    public Movie createItem() {
        int index = list.size();
        Movie last = list.get(index-1);

        Movie movie = new Movie(index, last.getYearOfAward()+1);
        list.add(movie);

        return movie;
    }

    public void removeItem(Movie movie) {
        list.remove(movie);
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
            LocalDate startDate = LocalDate.parse(parts[11], dateFormatter);
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

        final String separator = ";";

        builder
            .append(movie.getId()).append(separator)
            .append(movie.getTitle()).append(separator)
            .append(movie.getYearOfAward()).append(separator)
            .append(movie.getDirector()).append(separator)
            .append(movie.getMainActor()).append(separator)
            .append(movie.getTitleEnglish()).append(separator)
            .append(movie.getYearOfProduction()).append(separator)
            .append(String.join("/", movie.getCountry())).append(separator)
            .append(movie.getDuration()).append(separator)
            .append(movie.getFsk()).append(separator)
            .append(movie.getGenre()).append(separator)
            .append(movie.getStartDate().isPresent() ? dateFormatter.format(movie.getStartDate().get()): "-").append(separator)
            .append(movie.getNumberOfOscars());

        return builder.toString();
    }
}
