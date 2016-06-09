package ch.fhnw.oop2.project.view.master;

import ch.fhnw.oop2.project.MasterPresenter;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.model.MovieService;
import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.view.MovieView;
import ch.fhnw.oop2.project.view.editor.EditorView;
import ch.fhnw.oop2.project.view.table.TableView;
import ch.fhnw.oop2.project.view.toolbar.ToolbarView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public class MasterView extends FXMLView implements Initializable, MovieView {
    private MasterPresenter presenter;

    @FXML
    private BorderPane borderPane;

    @FXML
    private SplitPane splitPane;

    private ToolbarView toolbarView;
    private TableView tableView;
    private EditorView editorView;


    public MasterView() {
        this.presenter = new MasterPresenter(MovieService.getInstance(), this);
        load("master.fxml", "master.css");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createToolbar();
        createTableView();
        createEditorView();
        initializeListeners();
    }

    private void initializeListeners() {
    }

    private void createToolbar() {
        toolbarView = new ToolbarView(presenter);
        borderPane.setTop(toolbarView.getView());
    }

    private void createTableView() {
        tableView = new TableView(presenter);
        splitPane.getItems().add(tableView.getView());
    }

    private void createEditorView() {
        editorView = new EditorView(presenter);
        splitPane.getItems().add(editorView.getView());
    }

    @Override
    public void changedSelectedMovie(Movie movie) {

    }

    @Override
    public void changedYearOfAward(int year) {

    }

    @Override
    public void changedTitle(String title) {

    }

    @Override
    public void changedMainActor(String actor) {

    }

    @Override
    public void changedDirector(String director) {

    }
}
