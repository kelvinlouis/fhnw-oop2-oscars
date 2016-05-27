package ch.fhnw.oop2.project.master;

import ch.fhnw.oop2.project.DataService;
import ch.fhnw.oop2.project.editor.EditorPresenter;
import ch.fhnw.oop2.project.editor.EditorView;
import ch.fhnw.oop2.project.table.TablePresenter;
import ch.fhnw.oop2.project.table.TableView;
import ch.fhnw.oop2.project.toolbar.ToolbarPresenter;
import ch.fhnw.oop2.project.toolbar.ToolbarView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public class MasterPresenter implements Initializable {
    @FXML
    private BorderPane borderPane;

    @FXML
    private SplitPane splitPane;

    private ToolbarView toolbarView;
    private ToolbarPresenter toolbarPresenter;

    private TableView tableView;
    private TablePresenter tablePresenter;

    private EditorView editorView;
    private EditorPresenter editorPresenter;

    private DataService service;

    public MasterPresenter(DataService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createToolbar();
        createTableView();
        createEditorView();
    }

    private void createToolbar() {
        toolbarPresenter = new ToolbarPresenter();
        toolbarView = new ToolbarView(toolbarPresenter);
        borderPane.setTop(toolbarView.getView());
    }

    private void createTableView() {
        tablePresenter = new TablePresenter();
        tableView = new TableView(tablePresenter);
        splitPane.getItems().add(tableView.getView());

        tablePresenter.setList(service.getAll());
    }

    private void createEditorView() {
        editorPresenter = new EditorPresenter();
        editorView = new EditorView(editorPresenter);
        splitPane.getItems().add(editorView.getView());
    }
}
