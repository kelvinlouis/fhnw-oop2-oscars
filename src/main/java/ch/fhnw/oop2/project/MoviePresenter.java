package ch.fhnw.oop2.project;

import ch.fhnw.oop2.project.service.DataService;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.view.MovieView;
import ch.fhnw.oop2.project.view.master.MasterView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Created by Kelvin on 07-May-16.
 */
public class MoviePresenter {
    private ObjectProperty<Movie> selectedMovie = new SimpleObjectProperty<>();
    private DataService<Movie> service;
    private MovieView view;

    public MoviePresenter(DataService service, MovieView view) {
        this.service = service;
        this.view = view;
    }

    public List<Movie> getMovies() {
        return service.getAll();
    }

    public void setSelectedMovie(Movie movie, Movie oldMovie) {
        adjustStates(movie, oldMovie);

        selectedMovie.setValue(movie);
        view.changedSelectedMovie(movie);
    }

    public void setYearOfAward(int year) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.yearOfAwardProperty().setValue(year);
        view.changedYearOfAward(movie);
    }

    public void setTitle(String title) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.titleProperty().set(title);
        view.changedTitle(movie);
    }

    public void setTitleEnglish(String title) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.titleEnglishProperty().set(title);
    }

    public void setMainActor(String actors) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.mainActorProperty().set(actors);
        view.changedMainActor(movie);
    }

    public void setDirector(String directors) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.directorProperty().setValue(directors);
        view.changedDirector(movie);
    }

    public void setYearOfProduction(int year) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.yearOfProductionProperty().setValue(year);
    }

    public void setDuration(int duration) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.durationProperty().setValue(duration);
    }

    public void setStartDate(Optional<LocalDate> date) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.startDateProperty().setValue(date);
    }

    public void setFsk(int fsk) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.fskProperty().setValue(fsk);
    }

    public void setGenre(String genre) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.genreProperty().setValue(genre);
    }

    public void setCountries(List<String> countries) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.getCountry().setAll(countries);
    }

    public void setNumberOfOscars(int oscars) {
        Movie movie = selectedMovie.get();

        movie.setState(Movie.State.CHANGED);
        movie.numberOfOscarsProperty().setValue(oscars);
    }

    public void save() {
        try {
            service.save(view.chooseSaveFile());
        } catch (IOException exception) {
            // Show notification?
        }
    }

    public void add() {
        Movie movie = service.createItem();
        view.addedMovie(movie);
    }

    public void remove() {
        Movie movie = selectedMovie.get();

        if (movie != null) {
            service.removeItem(movie);
            view.removedMovie(movie);
        }
    }

    public void filter(String text) {
        view.filterMovies(text);
    }

    public void undo() {
        System.out.println("undo");
    }

    public void redo() {
        System.out.println("redo");
    }

    public void changeLanguage(Locale locale) {
        if (Locale.getDefault() != locale) {
            Locale.setDefault(locale);
        }

        // Draw the subviews and remove the currently selected movie from selection
        view.refreshView();
        adjustStates(null, selectedMovie.get());
    }

    private void adjustStates(Movie movie, Movie oldMovie) {
        if (oldMovie != null) {
            if (oldMovie.getState() == Movie.State.SELECTED) {
                oldMovie.setState(Movie.State.UNTOUCHED);
            } else if (oldMovie.getState() == Movie.State.SELECTED_CHANGED) {
                oldMovie.setState(Movie.State.CHANGED);
            }
        }

        if (movie != null) {
            if (movie.getState() == Movie.State.CHANGED) {
                movie.setState(Movie.State.SELECTED_CHANGED);
            } else {
                movie.setState(Movie.State.SELECTED);
            }
        }
    }
}
