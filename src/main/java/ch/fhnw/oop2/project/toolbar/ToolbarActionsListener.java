package ch.fhnw.oop2.project.toolbar;

/**
 * Created by Kelvin on 08-Jun-16.
 */
public interface ToolbarActionsListener {
    void onSave();
    void onAdd();
    void onRemove();
    void onFilter(String text);
    void onUndo();
    void onRedo();
}
