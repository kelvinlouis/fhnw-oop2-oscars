package ch.fhnw.oop2.project.view.editor;

import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.MasterPresenter;
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
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Kelvin on 07-May-16.
 */
public class EditorView extends FXMLView implements Initializable {
    private final MasterPresenter presenter;
    private ObjectProperty<Movie> selectedMovie = new SimpleObjectProperty<>();

    private final int MAX_YEAR = 2100;
    private final ObservableList<Integer> fskItems = FXCollections.observableArrayList(0, 6, 12, 16, 18);

    private boolean blockListeners = false;
    private boolean disabled = false;

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

    @FXML
    private HBox blockMask;

    public EditorView(MasterPresenter presenter) {
        this.presenter = presenter;
        load("editor.fxml", "editor.css");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBindings();
        initializeListeners();
        changeFSKItems();
        disable();
    }

    private void initializeBindings() {
        yearOfAwardLabel.textProperty().bind(yearOfAwardSpinner.valueProperty().asString());
        titleLabel.textProperty().bind(titleTextField.textProperty());
        directorLabel.textProperty().bind(directorTextField.textProperty());
        mainActorLabel.textProperty().bind(mainActorTextField.textProperty());
    }

    private void initializeListeners() {
        addListener(yearOfAwardSpinner, Movie::setYearOfAward);
        addListener(titleTextField, Movie::setTitle);
        addListener(titleEnTextField, Movie::setTitleEnglish);
        addListener(mainActorTextField, Movie::setMainActor);
        addListener(directorTextField, Movie::setDirector);
        addListener(productionYearSpinner, Movie::setYearOfProduction);
        addListener(durationSpinner, Movie::setDuration);
        addListener(launchDatePicker, Movie::setStartDate);
        addListener(fskComboBox, Movie::setFsk);
        addListener(genreTextField, Movie::setGenre);

        addListener(countryTextField, Movie::getCountry, "/", countries -> setFlags(countries));
        addListener(oscarsSpinner, Movie::setNumberOfOscars, i -> setOscars(i));
    }

    private void addListener(TextInputControl element, Movie.MovieStringSetter setter) {
        element.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                beforeSetNewValue();
                setter.set(selectedMovie.get(), newValue);
            }
        });
    }

    private void addListener(Spinner element, Movie.MovieIntegerSetter setter, Consumer<Integer> con) {
        element.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                beforeSetNewValue();
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
            if (newValue != null && blockListeners == false) {
                beforeSetNewValue();
                setter.set(selectedMovie.get(), newValue);
            }
        });
    }

    private void addListener(DatePicker element, Movie.MovieDateSetter setter) {
        element.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                beforeSetNewValue();
                setter.set(selectedMovie.get(), Optional.of(newValue));
            }
        });
    }

    private void addListener(TextInputControl element, Movie.MovieListGetter getter, String splitter, Consumer<List<String>> con) {
        element.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                List<String> newList = Arrays.asList(newValue.split(splitter));

                beforeSetNewValue();
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

    private void beforeSetNewValue() {
        selectedMovie.get().setState(Movie.State.SELECTED_CHANGED);
    }

    private void refreshElements(Movie movie) {
        titleTextField.textProperty().set(movie.getTitle());
        titleEnTextField.textProperty().set(movie.getTitleEnglish());
        yearOfAwardSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, movie.getYearOfAward()));
        directorTextField.textProperty().set(movie.getDirector());
        mainActorTextField.textProperty().set(movie.getMainActor());
        genreTextField.textProperty().set(movie.getGenre());
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
        flagsContainer.getChildren().clear();

        if (countries != null) {
            List<Node> flags = countries.stream()
                    .distinct()
                    .map(flag -> new FlagView(flag))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            flagsContainer.getChildren().addAll(flags);
        }
    }

    private void setPoster(int id) {
        URL resource = getClass().getResource("../../resources/posters/" + id + ".jpg");

        if (resource == null) {
            resource = getClass().getResource("../../resources/posters/no_poster.gif");
        }
        posterImage.setImage(new Image(resource.toExternalForm()));
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

    private SpinnerValueFactory.IntegerSpinnerValueFactory createSpinnerFactory(int min, int max, int v) {
        return new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, v);
    }

    public void changedSelectedMovie(Movie movie) {
        if (movie == null) {
            disable();
            clear();
        } else {
            enable();

            selectedMovie.setValue(movie);

            this.blockListeners = true;
            refreshElements(movie);
            this.blockListeners = false;
        }
    }

    private void disable() {
        if (disabled == false) {
            disableElements(true);
            disabled = true;
        }
    }

    private void clear() {
        blockListeners = true;

        titleTextField.textProperty().set("");
        titleEnTextField.textProperty().set("");
        yearOfAwardSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, 0));
        directorTextField.textProperty().set("");
        mainActorTextField.textProperty().set("");
        genreTextField.textProperty().set("");
        countryTextField.textProperty().set("");
        productionYearSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, 0));
        durationSpinner.setValueFactory(createSpinnerFactory(0,300, 0));
        oscarsSpinner.setValueFactory(createSpinnerFactory(0, 10, 0));
        launchDatePicker.setValue(null);
        fskComboBox.setValue(0);

        blockListeners = false;

        setPoster(-1);
        setFlags(null);
        setOscars(0);
    }

    private void enable() {
        if (disabled == true) {
            disableElements(false);
            disabled = false;
        }
    }

    private void disableElements(boolean state) {
        blockMask.visibleProperty().setValue(state);
        yearOfAwardLabel.disableProperty().setValue(state);
        posterImage.disableProperty().setValue(state);
        titleTextField.disableProperty().setValue(state);
        titleEnTextField.disableProperty().setValue(state);
        yearOfAwardSpinner.disableProperty().setValue(state);
        directorTextField.disableProperty().setValue(state);
        mainActorTextField.disableProperty().setValue(state);
        genreTextField.disableProperty().setValue(state);
        countryTextField.disableProperty().setValue(state);
        productionYearSpinner.disableProperty().setValue(state);
        durationSpinner.disableProperty().setValue(state);
        oscarsSpinner.disableProperty().setValue(state);
        launchDatePicker.disableProperty().setValue(state);
        fskComboBox.disableProperty().setValue(state);
    }

    public void changedYearOfAward(Movie movie) {
        yearOfAwardSpinner.setValueFactory(createSpinnerFactory(0, MAX_YEAR, movie.getYearOfAward()));
    }

    public void changedTitle(Movie movie) {
         titleTextField.textProperty().set(movie.getTitle());
    }

    public void changedMainActor(Movie movie) {
        titleTextField.textProperty().set(movie.getMainActor());
    }

    public void changedDirector(Movie movie) {
        titleTextField.textProperty().set(movie.getDirector());
    }
}
