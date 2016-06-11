package ch.fhnw.oop2.project.view.table;

import ch.fhnw.oop2.project.model.Movie;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Created by Kelvin on 10-Jun-16.
 */
class TableStateCell extends TableCell<Movie, Number> {
    private final VBox vbox;
    private final ImageView imageView;

    TableStateCell() {
        vbox = new VBox();
        imageView = new ImageView();

        imageView.setFitHeight(16);
        imageView.setFitWidth(16);

        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(imageView);
        setGraphic(vbox);
    }

    @Override
    protected void updateItem(Number item, boolean empty) {
        if (item != null) {
            String state = Movie.State.values()[item.intValue()].toString();
            String url = getClass().getResource("/marks/"  + state + ".png").toExternalForm();
            imageView.setImage(new Image(url));
        } else {
            imageView.setImage(null);
        }

    }
}
