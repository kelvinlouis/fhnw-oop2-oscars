package ch.fhnw.oop2.project;/**
 * Created by Kelvin on 07-May-16.
 */

import ch.fhnw.oop2.project.master.MasterPresenter;
import ch.fhnw.oop2.project.master.MasterView;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MasterView view = new MasterView(new MasterPresenter(MovieService.getInstance()));

        Scene scene = new Scene(view.getView(), 1100, 600);
        stage.setTitle("Academy Award for Best Picture");
        stage.setMinWidth(1100);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }
}