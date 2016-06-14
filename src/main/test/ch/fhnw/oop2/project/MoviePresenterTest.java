package ch.fhnw.oop2.project;

import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.service.MovieService;
import ch.fhnw.oop2.project.view.master.MasterView;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Optional;

import static org.junit.Assert.*;

public class MoviePresenterTest {

    private static MoviePresenter presenter;

    @BeforeClass
    public static void init() {
        presenter = new MoviePresenter(MovieService.getInstance(), new MockView());
    }

    @Test
    public void testGetMovies() throws Exception {
        assertEquals(presenter.getMovies().size(), 83);
    }

    @Test
    public void testSetSelectedMovie() throws Exception {
        Movie movie = new Movie();
        Movie oldMovie = new Movie();

        presenter.setSelectedMovie(movie, oldMovie);
        assertEquals(movie.getState(), Movie.State.SELECTED);
        assertEquals(oldMovie.getState(), Movie.State.UNTOUCHED);
    }

    @Test
    public void testSetYearOfAward() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setYearOfAward(2099);

        assertEquals(movie.getYearOfAward(), 2099);
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetTitle() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setTitle("Test Title");

        assertEquals(movie.getTitle(), "Test Title");
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetTitleEnglish() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setTitleEnglish("Test Title English");

        assertEquals(movie.getTitleEnglish(), "Test Title English");
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetMainActor() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setMainActor("Actor 1, Actor 2");

        assertEquals(movie.getMainActor(), "Actor 1, Actor 2");
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetDirector() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setDirector("Director 1, Director 2");

        assertEquals(movie.getDirector(), "Director 1, Director 2");
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetYearOfProduction() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setYearOfProduction(2099);

        assertEquals(movie.getYearOfProduction(), 2099);
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetDuration() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setDuration(9999);

        assertEquals(movie.getDuration(), 9999);
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetStartDateEmpty() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setStartDate(Optional.empty());

        assertEquals(movie.getStartDate(), Optional.empty());
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetStartDate() throws Exception {
        LocalDate date = LocalDate.now();
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setStartDate(Optional.of(date));

        assertEquals(movie.getStartDate().get(), date);
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetFsk() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setFsk(4);

        assertEquals(movie.getFsk(), 4);
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetGenre() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setGenre("Drama, Action");

        assertEquals(movie.getGenre(), "Drama, Action");
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetCountries() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        ArrayList<String> countries = new ArrayList<>();
        countries.add("CH");
        countries.add("DE");

        presenter.setCountries(countries);

        assertEquals(movie.getCountry().size(), 2);
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testSetNumberOfOscars() throws Exception {
        Movie movie = new Movie();
        presenter.setSelectedMovie(movie, null);

        presenter.setNumberOfOscars(2);

        assertEquals(movie.getNumberOfOscars(), 2);
        assertEquals(movie.getState(), Movie.State.CHANGED);
    }

    @Test
    public void testChangeLanguage() throws Exception {
        presenter.changeLanguage(Locale.GERMAN);
        assertEquals(Locale.getDefault(), Locale.GERMAN);
    }
}