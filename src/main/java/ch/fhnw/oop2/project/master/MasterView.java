package ch.fhnw.oop2.project.master;

import ch.fhnw.oop2.project.FXMLView;

/**
 * Created by Kelvin on 07-May-16.
 */
public class MasterView extends FXMLView {

    public MasterView(Object presenter) {
        super("master.fxml", "master.css", presenter);
    }
}
