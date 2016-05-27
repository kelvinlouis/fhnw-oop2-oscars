package ch.fhnw.oop2.project.toolbar;

import ch.fhnw.oop2.project.FXMLView;

/**
 * Created by Kelvin on 07-May-16.
 */
public class ToolbarView extends FXMLView {
    public ToolbarView(Object presenter) {
        super("toolbar.fxml", "toolbar.css", presenter);
    }
}
