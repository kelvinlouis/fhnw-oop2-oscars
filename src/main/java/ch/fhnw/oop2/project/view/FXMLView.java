package ch.fhnw.oop2.project.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Kelvin on 07-May-16.
 */
public abstract class FXMLView extends StackPane {
    private Parent view;
    private String FXMLFileName;
    private String CSSFileName;

    public void load(String fxml, String css, String bundle) {
        FXMLFileName = fxml;
        CSSFileName = css;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLFileName));
            loader.setController(this);

            if (bundle != null && !bundle.isEmpty()) {
                loader.setResources(ResourceBundle.getBundle(bundle));
            }

            view = loader.load();

            addCSS();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        String packageName = getPackageName();

        load(packageName + ".fxml", packageName + ".css", getClass().getPackage().getName() + "." + packageName);
    }

    private void addCSS() {
        URL uri = getClass().getResource(CSSFileName);
        view.getStylesheets().add(uri.toExternalForm());
    }

    private String getPackageName() {
        String packageName = getClass().getPackage().getName().toString();
        return packageName.substring(packageName.lastIndexOf('.')+1);
    }

    public Parent getView() {
        return view;
    }
}
