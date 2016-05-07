package ch.fhnw.oop2.project;

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

    protected final String FXMLFileName;
    protected final String CSSFileName;

    public FXMLView(String fxml, String css) {
        FXMLFileName = fxml;
        CSSFileName = css;

        try {
            view = FXMLLoader.load(getClass().getResource(FXMLFileName));
            addCSS();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCSS() {
        URL uri = getClass().getResource(CSSFileName);
        System.out.println(uri.toExternalForm());
        view.getStylesheets().add(uri.toExternalForm());
    }

    public Parent getView() {
        return view;
    }
}
