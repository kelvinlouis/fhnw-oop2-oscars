package ch.fhnw.oop2.project.table;

import ch.fhnw.oop2.project.Movie;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

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

    private TableActionListener listener;

    public void initialize(URL location, ResourceBundle resources) {
        table.setEditable(true);

        initializeListeners();
        setCellValueFactories();
        setCellFactories();
    }

    public void setList(List<Movie> items) {
        list.addAll(items);
        table.setItems(list);
    }

    public void setListener(TableActionListener listener) {
        if (list != null) {
            this.listener = listener;
        }
    }

    public ObjectProperty<Movie> selectedMovieProperty() {
        return selectedMovie;
    }

    private void initializeListeners() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMovie.set(newValue);
        });
    }

    private void setCellFactories() {
        yearColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return Integer.toString((int) object);
            }

            @Override
            public Number fromString(String string) {
                return Integer.parseInt(string);
            }
        }));

        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        mainActorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        directorColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        titleColumn.setOnEditCommit(event -> listener.onTitleChange(event.getNewValue()));
        mainActorColumn.setOnEditCommit(event -> listener.onMainActorChange(event.getNewValue()));
        directorColumn.setOnEditCommit(event -> listener.onDirectorChange(event.getNewValue()));
    }

    private void setCellValueFactories() {
        yearColumn.setCellValueFactory(data -> data.getValue().yearOfAwardProperty());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        mainActorColumn.setCellValueFactory(data -> data.getValue().mainActorProperty());
        directorColumn.setCellValueFactory(data -> data.getValue().directorProperty());
    }
}
