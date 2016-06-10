package ch.fhnw.oop2.project.view.table;

import ch.fhnw.oop2.project.LevenshteinDistance;
import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.MasterPresenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
        filteredList = new FilteredList<>(list, p -> true);
        table.setItems(filteredList);
        table.setEditable(true);

        setCellValueFactories();
        setCellFactories();
        initializeListeners();
    }

    private void initializeListeners() {
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            presenter.setSelectedMovie(newValue);
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

        table.setItems(filteredList);
//        if (str.isEmpty()) {
//            table.setItems(list);
//            return;
//        }
//
//        final String lowerStr = str.toLowerCase();
//        List<Movie> filteredList = list.stream().filter(movie -> {
//            int ldTitle = LevenshteinDistance.compute(movie.getTitle(), lowerStr);
//            int ldTitleEn = LevenshteinDistance.compute(movie.getTitleEnglish(), lowerStr);
//            System.out.println(ldTitle + ":" + ldTitleEn);
//            return ldTitle < 10 || ldTitleEn < 10;
//        }).collect(Collectors.toList());
//
//        table.setItems(FXCollections.observableArrayList(filteredList));
//
//        System.out.println(filteredList.size());
    }
}
