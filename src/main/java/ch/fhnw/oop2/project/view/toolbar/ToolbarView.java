package ch.fhnw.oop2.project.view.toolbar;

import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.MoviePresenter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public class ToolbarView extends FXMLView implements Initializable {
    private MoviePresenter presenter;

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
    private ImageView enLang;

    @FXML
    private ImageView deLang;

    @FXML
    private TextField filterTextField;

    public ToolbarView(MoviePresenter presenter) {
        this.presenter = presenter;
        load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeListeners();
        markActiveLanguage();
    }

    private void initializeListeners() {
        saveButton.setOnMouseClicked(event -> presenter.save());
        addButton.setOnMouseClicked(event -> presenter.add());
        removeButton.setOnMouseClicked(event -> presenter.remove());
        undoButton.setOnMouseClicked(event -> presenter.undo());
        redoButton.setOnMouseClicked(event -> presenter.redo());
        enLang.setOnMouseClicked(event -> presenter.changeLanguage(Locale.ENGLISH));
        deLang.setOnMouseClicked(event -> presenter.changeLanguage(Locale.GERMAN));
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> presenter.filter(newValue));
    }

    public void clearFilter() {
        filterTextField.textProperty().set("");
    }

    private void markActiveLanguage() {
        if (Locale.getDefault() == Locale.GERMAN) {
            enLang.getStyleClass().add("inactive");
            deLang.getStyleClass().add("active");
        } else {
            enLang.getStyleClass().add("active");
            deLang.getStyleClass().add("inactive");
        }
    }
}
