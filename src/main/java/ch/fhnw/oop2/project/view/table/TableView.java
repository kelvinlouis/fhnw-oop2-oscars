package ch.fhnw.oop2.project.view.table;

import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.MasterPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public class TableView extends FXMLView implements Initializable {
    private ObservableList<Movie> list = FXCollections.observableArrayList();
    private MasterPresenter presenter;

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

    public TableView(MasterPresenter presenter) {
        this.presenter = presenter;
        load("table.fxml", "table.css");
    }

    public void initialize(URL location, ResourceBundle resources) {
        list.addAll(presenter.getMovies());
        table.setItems(list);
        table.setEditable(true);

        setCellValueFactories();
        setCellFactories();
        initializeListeners();
    }

    private void initializeListeners() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                presenter.setSelectedMovie(newValue);
            }
        });

        yearColumn.setOnEditCommit(event -> presenter.setYearOfAward((int) event.getNewValue()));
        titleColumn.setOnEditCommit(event -> presenter.setTitle(event.getNewValue()));
        mainActorColumn.setOnEditCommit(event -> presenter.setMainActor(event.getNewValue()));
        directorColumn.setOnEditCommit(event -> presenter.setDirector(event.getNewValue()));
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
    }

    private void setCellValueFactories() {
        yearColumn.setCellValueFactory(data -> data.getValue().yearOfAwardProperty());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        mainActorColumn.setCellValueFactory(data -> data.getValue().mainActorProperty());
        directorColumn.setCellValueFactory(data -> data.getValue().directorProperty());
    }

    public void addMovie(Movie movie) {
        int index = list.size();

        list.add(movie);
        table.scrollTo(index);
        table.getSelectionModel().select(index);
    }
}
