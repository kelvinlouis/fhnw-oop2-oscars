package ch.fhnw.oop2.project.table;

import ch.fhnw.oop2.project.FXMLView;

/**
 * Created by Kelvin on 07-May-16.
 */
public class TableView extends FXMLView {
    public TableView(Object presenter) {
        super("table.fxml", "table.css", presenter);
    }
}
