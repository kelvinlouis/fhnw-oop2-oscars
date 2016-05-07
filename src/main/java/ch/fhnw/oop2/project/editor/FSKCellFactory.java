package ch.fhnw.oop2.project.editor;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Created by Kelvin on 07-May-16.
 */
class FSKCellFactory implements Callback<ListView<String>, ListCell<String>>
{
    @Override
    public ListCell<String> call(ListView<String> listview)
    {
        return new FSKCell();
    }
}