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
    }

    public void setYearOfAward(int year) {
        // yearOfAwardSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, year));
        selectedMovie.get().yearOfAwardProperty().setValue(year);
        view.changedYearOfAward(year);
    }

    public void setTitle(String title) {
        selectedMovie.get().titleProperty().set(title);
        view.changedTitle(title);
        // titleTextField.textProperty().set(title);
    }

    public void setMainActor(String actors) {
        selectedMovie.get().mainActorProperty().set(actors);
        view.changedMainActor(actors);
        // titleTextField.textProperty().set(actors);
    }

    public void setDirector(String directors) {
        selectedMovie.get().directorProperty().setValue(directors);
        view.changedDirector(directors);
        // titleTextField.textProperty().set(directors);
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
