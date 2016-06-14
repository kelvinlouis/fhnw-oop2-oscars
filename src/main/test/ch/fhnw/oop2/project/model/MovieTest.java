package ch.fhnw.oop2.project.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MovieTest {

    @Test
    public void testToString() throws Exception {
        Movie movie = new Movie(1, 2016);
        movie.setTitle("Title");
        movie.setDirector("Director1,Director2");
        movie.setMainActor("Actor1,Actor2");
        movie.setTitleEnglish("TitleEn");
        movie.setGenre("Genre");
        movie.setFsk(12);

        assertEquals(movie.toString(), "Title,2016,Director1,Director2,Actor1,Actor2,TitleEn,Genre");
    }
}