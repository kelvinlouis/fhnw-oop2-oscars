package ch.fhnw.oop2.project.view;

import ch.fhnw.oop2.project.model.Movie;

/**
 * Created by Kelvin on 09-Jun-16.
 */
public interface MovieView {
    public void changedSelectedMovie(Movie movie);
    public void changedYearOfAward(Movie movie);
    public void changedTitle(Movie movie);
    public void changedMainActor(Movie movie);
    public void changedDirector(Movie movie);
}
