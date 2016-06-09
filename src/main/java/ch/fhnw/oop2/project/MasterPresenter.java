package ch.fhnw.oop2.project;

import ch.fhnw.oop2.project.model.DataService;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.view.MovieView;
import ch.fhnw.oop2.project.view.master.MasterView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.List;

/**
 * Created by Kelvin on 07-May-16.
 */
public class MasterPresenter {
    private ObjectProperty<Movie> selectedMovie = new SimpleObjectProperty<>();
    private DataService<Movie> service;
    private MovieView view;

    public MasterPresenter(DataService service, MovieView view) {
        this.service = service;
        this.view = view;
    }

    public List<Movie> getMovies() {
        return service.getAll();
    }

    public void setSelectedMovie(Movie movie) {
        selectedMovie.setValue(movie);
        view.changedSelectedMovie(movie);
    }

    public void setYearOfAward(int year) {
        Movie movie = selectedMovie.get();

        movie.yearOfAwardProperty().setValue(year);
        view.changedYearOfAward(movie);
    }

    public void setTitle(String title) {
        Movie movie = selectedMovie.get();
        movie.titleProperty().set(title);
        view.changedTitle(movie);
    }

    public void setMainActor(String actors) {
        Movie movie = selectedMovie.get();
        movie.mainActorProperty().set(actors);
        view.changedMainActor(movie);
    }

    public void setDirector(String directors) {
        Movie movie = selectedMovie.get();
        movie.directorProperty().setValue(directors);
        view.changedDirector(movie);
    }

    public void save() {
        System.out.println("save");
    }

    public void add() {
        System.out.println("add");
    }

    public void remove() {
        System.out.println("remove");
    }

    public void filter(String text) {
        System.out.println("filter");
    }

    public void undo() {
        System.out.println("undo");
    }

    public void redo() {
        System.out.println("redo");
    }
}
