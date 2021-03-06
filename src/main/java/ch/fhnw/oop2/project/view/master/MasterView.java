package ch.fhnw.oop2.project.view.master;

import ch.fhnw.oop2.project.MoviePresenter;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.service.MovieService;
import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.view.MovieView;
import ch.fhnw.oop2.project.view.editor.EditorView;
import ch.fhnw.oop2.project.view.table.MovieTableView;
import ch.fhnw.oop2.project.view.toolbar.ToolbarView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public class MasterView extends FXMLView implements Initializable, MovieView {
    private final MoviePresenter presenter;
    private final Stage stage;

    @FXML
    private BorderPane borderPane;

    @FXML
    private SplitPane splitPane;

    private ToolbarView toolbarView;
    private MovieTableView tableView;
    private EditorView editorView;


    public MasterView(Stage stage) {
        // Using the stage for saving the file
        this.stage = stage;
        this.presenter = new MoviePresenter(MovieService.getInstance(), this);

        load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createToolbar();
        createTableView();
        createEditorView();
    }

    private void createToolbar() {
        toolbarView = new ToolbarView(presenter);
        borderPane.setTop(toolbarView.getView());
    }

    private void createTableView() {
        tableView = new MovieTableView(presenter);
        splitPane.getItems().add(tableView.getView());
    }

    private void createEditorView() {
        editorView = new EditorView(presenter);
        splitPane.getItems().add(editorView.getView());
    }

    @Override
    public void changedSelectedMovie(Movie movie) {
        editorView.changedSelectedMovie(movie);
    }

    @Override
    public void changedYearOfAward(Movie movie) {
        editorView.changedYearOfAward(movie);
    }

    @Override
    public void changedTitle(Movie movie) {
        editorView.changedTitle(movie);
    }

    @Override
    public void changedMainActor(Movie movie) {
        editorView.changedMainActor(movie);
    }

    @Override
    public void changedDirector(Movie movie) {
        editorView.changedDirector(movie);
    }

    @Override
    public void addedMovie(Movie movie) {
        toolbarView.clearFilter();
        tableView.addMovie(movie);
    }

    public void removedMovie(Movie movie) {
        tableView.removeMovie(movie);
    }

    public File chooseSaveFile() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV", "*.csv");
        fileChooser.getExtensionFilters().add(extensionFilter);

        return fileChooser.showSaveDialog(stage);
    }

    public void filterMovies(String str) {
        tableView.filterMovies(str);
    }

    public void refreshView() {
        borderPane.getChildren().remove(toolbarView.getView());
        splitPane.getItems().clear();

        createToolbar();
        createTableView();
        createEditorView();
    }
}
