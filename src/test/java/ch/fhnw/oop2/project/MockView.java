package ch.fhnw.oop2.project;

import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.view.MovieView;

import java.io.File;

class MockView implements MovieView {
    @Override
    public void changedSelectedMovie(Movie movie) {

    }

    @Override
    public void changedYearOfAward(Movie movie) {

    }

    @Override
    public void changedTitle(Movie movie) {

    }

    @Override
    public void changedMainActor(Movie movie) {

    }

    @Override
    public void changedDirector(Movie movie) {

    }

    @Override
    public void addedMovie(Movie movie) {

    }

    @Override
    public void removedMovie(Movie movie) {

    }

    @Override
    public void filterMovies(String text) {

    }

    @Override
    public File chooseSaveFile() {
        return null;
    }

    @Override
    public void refreshView() {

    }
}
