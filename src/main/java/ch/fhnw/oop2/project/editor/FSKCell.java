package ch.fhnw.oop2.project.editor;

import javafx.scene.control.ListCell;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 * Created by Kelvin on 07-May-16.
 */
class FSKCell extends ListCell<String>
{
    @Override
    public void updateItem(String item, boolean empty)
    {
        super.updateItem(item, empty);

        if (empty)
        {
            setText(null);
            setGraphic(null);
        }
        else
        {
            setText(item);
            Shape shape = this.getShape(item);
            setGraphic(shape);
        }
    }

    public Shape getShape(String shapeType)
    {
        Shape shape = null;

        switch (shapeType.toLowerCase())
        {
            case "line":
                shape = new Line(0, 10, 20, 10);
                break;
            case "rectangle":
                shape = new Rectangle(0, 0, 20, 20);
                break;
            case "circle":
                shape = new Circle(20, 20, 10);
                break;
            case "Text":
                shape = new Text(10, 50, "This is a Text");
                break;
            default:
                shape = null;
        }

        return shape;
    }
}
