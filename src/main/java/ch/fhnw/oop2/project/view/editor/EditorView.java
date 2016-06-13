package ch.fhnw.oop2.project.view.editor;

import ch.fhnw.oop2.project.view.FXMLView;
import ch.fhnw.oop2.project.model.Movie;
import ch.fhnw.oop2.project.MoviePresenter;
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
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Kelvin on 07-May-16.
 */
public class EditorView extends FXMLView implements Initializable {
    private final MoviePresenter presenter;
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

    public EditorView(MoviePresenter presenter) {
        this.presenter = presenter;
        load();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeBindings();
        initializeListeners();
        changeFSKItems();
        adjustDatePicker();
        disable();
    }

    private void initializeBindings() {
        yearOfAwardLabel.textProperty().bind(yearOfAwardSpinner.valueProperty().asString());
        titleLabel.textProperty().bind(titleTextField.textProperty());
        directorLabel.textProperty().bind(directorTextField.textProperty());
        mainActorLabel.textProperty().bind(mainActorTextField.textProperty());
    }

    private void initializeListeners() {
        addListener(yearOfAwardSpinner, i -> presenter.setYearOfAward(i));
        addListener(titleTextField, s -> presenter.setTitle(s));
        addListener(titleEnTextField, s -> presenter.setTitleEnglish(s));
        addListener(mainActorTextField,s -> presenter.setMainActor(s));
        addListener(directorTextField, s -> presenter.setDirector(s));
        addListener(productionYearSpinner, i -> presenter.setYearOfProduction(i));
        addListener(durationSpinner, i -> presenter.setDuration(i));
        addListener(launchDatePicker, localDate -> presenter.setStartDate(localDate));
        addListener(fskComboBox, i -> presenter.setFsk(i));
        addListener(genreTextField, s -> presenter.setGenre(s));

        addListener(countryTextField, "/", list -> {
            presenter.setCountries(list);
            setFlags(list);
        });

        addListener(oscarsSpinner, integer -> {
            presenter.setNumberOfOscars(integer);
            setOscars(integer);
        });
    }

    private void addListener(Spinner element, Consumer<Integer> con) {
        element.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                beforeSetNewValue();

                if (con != null) {
                    // Execute a consumer after setting value
                    con.accept((int) newValue);
                }
            }
        });
    }

    private void addListener(TextInputControl element, Consumer<String> con) {
        element.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                beforeSetNewValue();

                if (con != null) {
                    // Execute a consumer after setting value
                    con.accept(newValue);
                }
            }
        });
    }


    private void addListener(ComboBox<Integer> element, Consumer<Integer> con) {
        element.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                beforeSetNewValue();

                if (con != null) {
                    // Execute a consumer after setting value
                    con.accept(newValue);
                }
            }
        });
    }

    private void addListener(DatePicker element, Consumer<Optional<LocalDate>> con) {
        element.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                beforeSetNewValue();

                if (con != null) {
                    // Execute a consumer after setting value
                    con.accept(Optional.of(newValue));
                }
            }
        });
    }

    private void addListener(TextInputControl element, String splitter, Consumer<List<String>> con) {
        element.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && blockListeners == false) {
                List<String> newList = Arrays.asList(newValue.split(splitter));

                beforeSetNewValue();

                if (con != null) {
                    // Execute a consumer after setting values
                    con.accept(newList);
                }
            }
        });
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
        URL resource = getClass().getResource("/posters/" + id + ".jpg");

        if (resource == null) {
            resource = getClass().getResource("/posters/no_poster.gif");
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

    private void adjustDatePicker() {
        launchDatePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }

    private void changeFSKItems() {
        fskComboBox.setItems(fskItems);

        // Set the CellFactory property
        fskComboBox.setCellFactory(param -> new FSKCell());

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
        mainActorTextField.textProperty().set(movie.getMainActor());
    }

    public void changedDirector(Movie movie) {
        directorTextField.textProperty().set(movie.getDirector());
    }
}
