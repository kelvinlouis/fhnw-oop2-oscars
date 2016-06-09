package ch.fhnw.oop2.project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Kelvin on 07-May-16.
 */
public abstract class FXMLView extends StackPane {
    private Parent view;
    private String FXMLFileName;
    private String CSSFileName;

    public void load(String fxml, String css) {
        FXMLFileName = fxml;
        CSSFileName = css;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFileName));
            loader.setController(this);
            view = loader.load();

            addCSS();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCSS() {
        URL uri = getClass().getResource(CSSFileName);
        view.getStylesheets().add(uri.toExternalForm());
    }

    public Parent getView() {
        return view;
    }
}
