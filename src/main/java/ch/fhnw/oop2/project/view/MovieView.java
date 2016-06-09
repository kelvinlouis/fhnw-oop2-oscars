package ch.fhnw.oop2.project.view;

import ch.fhnw.oop2.project.model.Movie;

/**
 * Created by Kelvin on 09-Jun-16.
 */
public interface MovieView {
    void changedSelectedMovie(Movie movie);
    void changedYearOfAward(Movie movie);
    void changedTitle(Movie movie);
    void changedMainActor(Movie movie);
    void changedDirector(Movie movie);
    void addedMovie(Movie movie);
}
