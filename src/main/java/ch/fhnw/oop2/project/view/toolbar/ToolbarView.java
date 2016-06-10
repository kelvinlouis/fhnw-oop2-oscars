package ch.fhnw.oop2.project.view.toolbar;

import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.MasterPresenter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public class ToolbarView extends FXMLView implements Initializable {
    private MasterPresenter presenter;

    @FXML
    private Button saveButton;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private TextField filterTextField;

    public ToolbarView(MasterPresenter presenter) {
        this.presenter = presenter;
        load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListeners();
    }

    private void initializeListeners() {
        saveButton.setOnMouseClicked(event -> presenter.save());
        addButton.setOnMouseClicked(event -> presenter.add());
        removeButton.setOnMouseClicked(event -> presenter.remove());
        undoButton.setOnMouseClicked(event -> presenter.undo());
        redoButton.setOnMouseClicked(event -> presenter.redo());
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> presenter.filter(newValue));
    }

    public void clearFilter() {
        filterTextField.textProperty().set("");
    }
}
