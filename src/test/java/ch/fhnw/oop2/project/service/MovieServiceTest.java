package ch.fhnw.oop2.project.service;

import ch.fhnw.oop2.project.model.Movie;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class MovieServiceTest {

    private static DataService<Movie> service;

    @BeforeClass
    public static void init() {
        service = MovieService.getInstance();
        service.getAll();
    }

    @Test
    public void testCreateItem() throws Exception {
        Movie movie = service.createItem();

        assertEquals(movie.getId(), 83);
        assertEquals(movie.getYearOfAward(), 2013);

        movie = service.createItem();
        assertEquals(movie.getId(), 84);
        assertEquals(movie.getYearOfAward(), 2014);
    }

    @Test
    public void testRemoveItem() throws Exception {
        Movie mov = service.createItem();
        int count = service.getAll().size();
        service.removeItem(mov);
        assertEquals(count-1, service.getAll().size());
    }
}