package ch.fhnw.oop2.project.editor;

import ch.fhnw.oop2.project.Movie;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Kelvin on 07-May-16.
 */
public class EditorPresenter implements Initializable{
    private ObjectProperty<Movie> selectedMovie = new SimpleObjectProperty<>();

    private final int MAX_YEAR = LocalDate.now().getYear();
    private final ObservableList<Integer> fskItems = FXCollections.observableArrayList(0, 6, 12, 16, 18);

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
    private ComboBox<Integer> fskComboBox;

    @FXML
    private Spinner<Integer> oscarsSpinner;

    @FXML
    private Spinner<Integer> productionYearSpinner;

    @FXML
    private Spinner<Integer> durationSpinner;

    @FXML
    private DatePicker launchDatePicker;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBindings();
        initializeListeners();
        changeFSKItems();
    }

    private void initializeBindings() {
        yearOfAwardLabel.textProperty().bind(yearOfAwardSpinner.valueProperty().asString());
        titleLabel.textProperty().bind(titleTextField.textProperty());
        directorLabel.textProperty().bind(directorTextField.textProperty());
        mainActorLabel.textProperty().bind(mainActorTextField.textProperty());
    }

    private void initializeListeners() {
        selectedMovie.addListener((obs, old, newValue) -> onMovieChange(newValue));

        addListener(titleTextField, Movie::setTitle);
        addListener(titleEnTextField, Movie::setTitleEnglish);
        addListener(yearOfAwardSpinner, Movie::setYearOfAward);
        addListener(productionYearSpinner, Movie::setYearOfProduction);
        addListener(durationSpinner, Movie::setDuration);
        addListener(launchDatePicker, Movie::setStartDate);
        addListener(fskComboBox, Movie::setFsk);

        addListener(genreTextField, Movie::getGenre, ", ");
        addListener(mainActorTextField, Movie::getMainActor, ", ");
        addListener(directorTextField, Movie::getDirector, ", ");
        addListener(countryTextField, Movie::getCountry, "/", countries -> setFlags(countries));
        addListener(oscarsSpinner, Movie::setNumberOfOscars, i -> setOscars(i));
    }

    private void addListener(TextInputControl element, Movie.MovieStringSetter setter) {
        element.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setter.set(selectedMovie.get(), newValue);
            }
        });
    }

    private void addListener(Spinner element, Movie.MovieIntegerSetter setter, Consumer<Integer> con) {
        element.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setter.set(selectedMovie.get(), (int) newValue);

                if (con != null) {
                    // Execute a consumer after setting value
                    con.accept((int) newValue);
                }
            }
        });
    }

    private void addListener(Spinner element, Movie.MovieIntegerSetter setter) {
        addListener(element, setter, null);
    }

    private void addListener(ComboBox<Integer> element, Movie.MovieIntegerSetter setter) {
        element.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setter.set(selectedMovie.get(), newValue);
            }
        });
    }

    private void addListener(DatePicker element, Movie.MovieDateSetter setter) {
        element.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setter.set(selectedMovie.get(), Optional.of(newValue));
            }
        });
    }

    private void addListener(TextInputControl element, Movie.MovieListGetter getter, String splitter, Consumer<List<String>> con) {
        element.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                List<String> newList = Arrays.asList(newValue.split(splitter));
                getter.get(selectedMovie.get()).setAll(newList);

                if (con != null) {
                    // Execute a consumer after setting values
                    con.accept(newList);
                }
            }
        });
    }

    private void addListener(TextInputControl element, Movie.MovieListGetter getter, String splitter) {
        addListener(element, getter, splitter, null);
    }

    private void onMovieChange(Movie movie) {
        refreshElements(movie);
    }

    private void refreshElements(Movie movie) {
        titleTextField.textProperty().set(movie.getTitle());
        titleEnTextField.textProperty().set(movie.getTitleEnglish());
        yearOfAwardSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, movie.getYearOfAward()));
        directorTextField.textProperty().set(String.join(", ", movie.getDirector()));
        mainActorTextField.textProperty().set(String.join(", ", movie.getMainActor()));
        genreTextField.textProperty().set(String.join(", ", movie.getGenre()));
        countryTextField.textProperty().set(String.join("/", movie.getCountry()));
        productionYearSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, movie.getYearOfProduction()));
        durationSpinner.setValueFactory(createSpinnerFactory(0,300, movie.getDuration()));
        oscarsSpinner.setValueFactory(createSpinnerFactory(0, 10, movie.getNumberOfOscars()));
        launchDatePicker.setValue(movie.getStartDate().orElse(null));
        fskComboBox.setValue(movie.getFsk());

        setPoster(movie.getId());
        setFlags(movie.getCountry());
        setOscars(movie.getNumberOfOscars());
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

    private void setOscars(int nr) {
        List<Node> oscars = IntStream.range(0, nr)
                .mapToObj(i -> new OscarView())
                .collect(Collectors.toList());

        oscarsContainer.getChildren().clear();
        oscarsContainer.getChildren().addAll(oscars);
    }

    private void changeFSKItems() {
        fskComboBox.setItems(fskItems);

        // Set the CellFactory property
        fskComboBox.setCellFactory(new FSKCellFactory());

        // Set the ButtonCell property
        fskComboBox.setButtonCell(new FSKCell());
    }

    public ObjectProperty<Movie> selectedMovieProperty() {
        return selectedMovie;
    }

    private SpinnerValueFactory.IntegerSpinnerValueFactory createSpinnerFactory(int min, int max, int v) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, v);
    }
}
