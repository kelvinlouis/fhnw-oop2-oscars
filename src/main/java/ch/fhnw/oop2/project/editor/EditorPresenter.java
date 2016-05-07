package ch.fhnw.oop2.project.editor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public class EditorPresenter implements Initializable{
    @FXML
    private ComboBox fsk;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //changeFSKValues();
    }

    private void changeFSKValues() {
        fsk.getItems().addAll("Line", "Rectangle", "Circle", "Text");

        // Set the CellFactory property
        fsk.setCellFactory(new FSKCellFactory());

        // Set the ButtonCell property
        fsk.setButtonCell(new FSKCell());
    }
}
