package ch.fhnw.oop2.project.editor;

import ch.fhnw.oop2.project.FXMLView;

/**
 * Created by Kelvin on 07-May-16.
 */
public class EditorView extends FXMLView {
    public EditorView(Object presenter) {
        super("editor.fxml", "editor.css", presenter);
    }
}
