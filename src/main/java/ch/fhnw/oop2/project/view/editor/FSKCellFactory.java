package ch.fhnw.oop2.project.view.editor;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * Created by Kelvin on 07-May-16.
 */
class FSKCellFactory implements Callback<ListView<Integer>, ListCell<Integer>>
{
    @Override
    public ListCell<Integer> call(ListView<Integer> listview)
    {
        return new FSKCell();
    }
}