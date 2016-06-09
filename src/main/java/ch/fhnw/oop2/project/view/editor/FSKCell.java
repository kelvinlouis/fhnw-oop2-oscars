package ch.fhnw.oop2.project.view.editor;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;

/**
 * Created by Kelvin on 07-May-16.
 */
class FSKCell extends ListCell<Integer> {
    @Override
    public void updateItem(Integer item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || empty) {
            setGraphic(null);
        } else {
            String url = getClass().getResource("../../resources/fsk_labels/"  + item + ".png").toExternalForm();
            ImageView image = new ImageView(url);
            image.setPreserveRatio(true);

            setGraphic(image);
        }
    }
}
