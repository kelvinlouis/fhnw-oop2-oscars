package ch.fhnw.oop2.project.view.editor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

/**
 * Created by Kelvin on 08-Jun-16.
 */
class FlagView extends ImageView {
    FlagView(String country) {
        URL url = getClass().getResource("../resources/flags/" + country.toLowerCase() + ".png");

        if (url != null) {
            setImage(new Image(url.toExternalForm()));
        } else {
            setImage(null);
        }

        setFitHeight(24);
        setPreserveRatio(true);
    }
}