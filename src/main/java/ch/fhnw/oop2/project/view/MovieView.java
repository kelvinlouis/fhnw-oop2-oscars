package ch.fhnw.oop2.project.view;

import ch.fhnw.oop2.project.model.Movie;

/**
 * Created by Kelvin on 09-Jun-16.
 */
public interface MovieView {
    public void changedSelectedMovie(Movie movie);
    public void changedYearOfAward(int year);
    public void changedTitle(String title);
    public void changedMainActor(String actor);
    public void changedDirector(String director);
}
