package ch.fhnw.oop2.project.editor;

import ch.fhnw.oop2.project.Movie;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Kelvin on 07-May-16.
 */
public class EditorPresenter implements Initializable{
    private ObjectProperty<Movie> selectedMovie = new SimpleObjectProperty<>();

    private final int MAX_YEAR = LocalDate.now().getYear();

    @FXML
    private Label yearOfAwardLabel;

    @FXML
    private HBox flagsContainer;

    @FXML
    private Label titleLabel;

    @FXML
    private Label directorLabel;

    @FXML
    private Label mainActorLabel;

    @FXML
    private HBox oscarsContainer;

    @FXML
    private ImageView posterImage;

    @FXML
    private Spinner<Integer> yearOfAwardSpinner;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField directorTextField;

    @FXML
    private TextField mainActorTextField;

    @FXML
    private TextField titleEnTextField;

    @FXML
    private TextField genreTextField;

    @FXML
    private TextField countryTextField;

    @FXML
    private ComboBox fskComboBox;

    @FXML
    private Spinner<Integer> oscarsSpinner;

    @FXML
    private Spinner<Integer> productionYearSpinner;

    @FXML
    private Spinner<Integer> durationSpinner;

    @FXML
    private DatePicker launchDatePicker;

    class OscarView extends ImageView {
        OscarView() {
            setImage(new Image(getClass().getResource("../resources/oscar.png").toExternalForm()));
            setFitHeight(40);
            setPreserveRatio(true);
        }
    }

    class FlagView extends ImageView {
        FlagView(String flag) {
            URL url = getClass().getResource("../resources/flags/" + flag.toLowerCase() + ".png");

            if (url != null) {
                setImage(new Image(url.toExternalForm()));
            } else {
                setImage(null);
            }

            setFitHeight(24);
            setPreserveRatio(true);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBindings();
        initializeListeners();
        //changeFSKValues();
    }

    private void initializeBindings() {
        titleLabel.textProperty().bind(titleTextField.textProperty());
        directorLabel.textProperty().bind(directorTextField.textProperty());
        mainActorLabel.textProperty().bind(mainActorTextField.textProperty());
    }

    private void initializeListeners() {
        selectedMovie.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                titleTextField.textProperty().unbindBidirectional(oldValue.titleProperty());
                titleEnTextField.textProperty().unbindBidirectional(oldValue.titleEnglishProperty());
            }

            yearOfAwardLabel.textProperty().bind(newValue.yearOfAwardProperty().asString());
            yearOfAwardSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, newValue.getYearOfAward()));
            titleTextField.textProperty().bindBidirectional(newValue.titleProperty());
            directorTextField.textProperty().set(String.join(", ", newValue.getDirector()));
            mainActorTextField.textProperty().set(String.join(", ", newValue.getMainActor()));
            titleEnTextField.textProperty().bindBidirectional(newValue.titleEnglishProperty());
            genreTextField.textProperty().set(String.join(", ", newValue.getGenre()));
            countryTextField.textProperty().set(String.join("/", newValue.getCountry()));
            productionYearSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, newValue.getYearOfProduction()));
            durationSpinner.setValueFactory(createSpinnerFactory(0,300, newValue.getDuration()));
            oscarsSpinner.setValueFactory(createSpinnerFactory(0, 10, newValue.getNumberOfOscars()));
            launchDatePicker.setValue(newValue.getStartDate().orElse(null));

            setPoster(newValue.getId());
//            setFlags(newValue.getCountry());
        });

        oscarsSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            List<Node> oscars = IntStream.range(0, newValue)
                    .mapToObj(i -> new OscarView())
                    .collect(Collectors.toList());

            oscarsContainer.getChildren().clear();
            oscarsContainer.getChildren().addAll(oscars);
        });

        countryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setFlags(selectedMovie.get().getCountry());
            }
        });
    }

    private SpinnerValueFactory.IntegerSpinnerValueFactory createSpinnerFactory(int min, int max, int v) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, v);
    }

    private void setFlags(List<String> countries) {
        List<Node> flags = countries.stream()
                .distinct()
                .map(flag -> new FlagView(flag))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        flagsContainer.getChildren().clear();
        flagsContainer.getChildren().addAll(flags);
    }

    private void setPoster(int id) {
        URL resource = getClass().getResource("../resources/posters/" + id + ".jpg");

        if (resource != null) {
            posterImage.setImage(new Image(resource.toExternalForm()));
        } else {
            posterImage.setImage(null);
        }
    }

    private void changeFSKValues() {
        fskComboBox.getItems().addAll("Line", "Rectangle", "Circle", "Text");

        // Set the CellFactory property
        fskComboBox.setCellFactory(new FSKCellFactory());

        // Set the ButtonCell property
        fskComboBox.setButtonCell(new FSKCell());
    }

    public ObjectProperty<Movie> selectedMovieProperty() {
        return selectedMovie;
    }
}
