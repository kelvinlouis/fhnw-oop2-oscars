package ch.fhnw.oop2.project.view.table;

import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.MasterPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private MasterPresenter presenter;
    private ObservableList<Movie> list = FXCollections.observableArrayList();
    private FilteredList<Movie> filteredList;

    @FXML
    javafx.scene.control.TableView<Movie> table;

    @FXML
    private TableColumn<Movie, Number> stateColumn;

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
        load();
    }

    public void initialize(URL location, ResourceBundle resources) {
        initializeTableList();
        setCellValueFactories();
        setCellFactories();
        initializeListeners();
    }

    private void initializeTableList() {
        list.addAll(presenter.getMovies());

        yearColumn.setSortType(TableColumn.SortType.ASCENDING);

        // Use a filtered and sorted list for the table
        filteredList = new FilteredList<>(list, p -> true);
        SortedList<Movie> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

        table.getSortOrder().add(yearColumn);

        table.setEditable(true);
    }

    private void initializeListeners() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setSelectedMovie(newValue, oldValue);
        });

        yearColumn.setOnEditCommit(event -> presenter.setYearOfAward((int) event.getNewValue()));
        titleColumn.setOnEditCommit(event -> presenter.setTitle(event.getNewValue()));
        mainActorColumn.setOnEditCommit(event -> presenter.setMainActor(event.getNewValue()));
        directorColumn.setOnEditCommit(event -> presenter.setDirector(event.getNewValue()));
    }

    private void setCellFactories() {
        stateColumn.setCellFactory(param -> new TableStateCell());

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
        stateColumn.setCellValueFactory(data -> data.getValue().stateProperty());
        yearColumn.setCellValueFactory(data -> data.getValue().yearOfAwardProperty());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        mainActorColumn.setCellValueFactory(data -> data.getValue().mainActorProperty());
        directorColumn.setCellValueFactory(data -> data.getValue().directorProperty());
    }

    public void addMovie(Movie movie) {
        int index = list.size();

        list.add(movie);
        table.scrollTo(index);
        table.getSelectionModel().selectLast();
    }

    public void removeMovie(Movie movie) {
        list.remove(movie);
        table.getSelectionModel().select(null);
    }

    public void filterMovies(String str) {
        filteredList.setPredicate(movie -> {
            if (str == null || str.isEmpty()) return true;

            String lcFilter = str.toLowerCase();

            if (movie.toString().toLowerCase().contains(lcFilter)) {
                return true;
            }

            return false;
        });
    }
}
