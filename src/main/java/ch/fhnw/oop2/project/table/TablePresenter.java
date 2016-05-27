package ch.fhnw.oop2.project.table;

import ch.fhnw.oop2.project.Movie;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by Kelvin on 07-May-16.
 */
public class TablePresenter implements Initializable {
    private ObservableList<Movie> list = FXCollections.observableArrayList();

    @FXML
    javafx.scene.control.TableView<Movie> table;

    @FXML
    private TableColumn<Movie, Number> yearColumn;

    @FXML
    private TableColumn<Movie, String> titleColumn;

    @FXML
    private TableColumn<Movie, String> directorColumn;

    @FXML
    private TableColumn<Movie, String> mainActorColumn;

    private ObjectProperty<Movie> selectedMovie = new SimpleObjectProperty<>();

    public void initialize(URL location, ResourceBundle resources) {
        initializeListeners();
        setCellValueFactories();
    }

    public void setList(List<Movie> items) {
        list.addAll(items);
        table.setItems(list);
    }

    public ObjectProperty<Movie> selectedMovieProperty() {
        return selectedMovie;
    }

    private void initializeListeners() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMovie.set(newValue);
        });
    }

    private void setCellValueFactories() {
        yearColumn.setCellValueFactory(data -> data.getValue().yearOfAwardProperty());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());

        // Create joined string properties for directors
        directorColumn.setCellValueFactory(data -> {
            StringProperty directors = new SimpleStringProperty();
            ObservableList<String> list = data.getValue().getDirector();

            list.addListener((ListChangeListener<? super String>) c ->
                    directors.set(String.join(", ", list)));

            directors.set(String.join(", ", list));

            return directors;
        });

        // Create joined string properties for main actors
        mainActorColumn.setCellValueFactory(data -> {
            StringProperty actors = new SimpleStringProperty();
            ObservableList<String> list = data.getValue().getMainActor();

            list.addListener((ListChangeListener<? super String>) c ->
                    actors.set(String.join(", ", list)));

            actors.set(String.join(", ", list));

            return actors;
        });
    }
}
