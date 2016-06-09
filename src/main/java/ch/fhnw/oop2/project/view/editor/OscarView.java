package ch.fhnw.oop2.project.view.editor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by Kelvin on 08-Jun-16.
 */
class OscarView extends ImageView {
    OscarView() {
        setImage(new Image(getClass().getResource("../../resources/oscar.png").toExternalForm()));
        setFitHeight(40);
        setPreserveRatio(true);
    }
}
