package ch.fhnw.oop2.project.toolbar;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public class ToolbarPresenter implements Initializable {
    private ToolbarActionsListener listener;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListeners();
    }

    public void setListener(ToolbarActionsListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    private void initializeListeners() {
        saveButton.setOnMouseClicked(event -> listener.onSave());
        addButton.setOnMouseClicked(event -> listener.onAdd());
        removeButton.setOnMouseClicked(event -> listener.onRemove());
        undoButton.setOnMouseClicked(event -> listener.onUndo());
        redoButton.setOnMouseClicked(event -> listener.onRedo());
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> listener.onFilter(newValue));
    }
}
