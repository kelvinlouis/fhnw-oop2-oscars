package ch.fhnw.oop2.project.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Kelvin on 07-May-16.
 */
public class Movie {
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty state = new SimpleIntegerProperty();
    private StringProperty title = new SimpleStringProperty();
    private IntegerProperty yearOfAward = new SimpleIntegerProperty();
    private StringProperty director = new SimpleStringProperty();
    private StringProperty mainActor = new SimpleStringProperty();
    private StringProperty titleEnglish = new SimpleStringProperty();
    private IntegerProperty yearOfProduction = new SimpleIntegerProperty();
    private ObservableList<String> country = FXCollections.observableArrayList();
    private IntegerProperty duration = new SimpleIntegerProperty();
    private IntegerProperty fsk = new SimpleIntegerProperty();
    private StringProperty genre = new SimpleStringProperty();
    private ObjectProperty<Optional<LocalDate>> startDate = new SimpleObjectProperty<>();
    private IntegerProperty numberOfOscars = new SimpleIntegerProperty();

    public enum State {
        UNTOUCHED, SELECTED, CHANGED, SELECTED_CHANGED;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public Movie(int id, int year) {
        setId(id);
        setState(State.UNTOUCHED);
        setTitle("");
        setYearOfAward(year);
        setDirector("");
        setMainActor("");
        setTitleEnglish("");
        setYearOfProduction(0);
        setCountry(FXCollections.observableArrayList());
        setDuration(0);
        setFsk(0);
        setGenre("");
        setNumberOfOscars(1);
        setStartDate(Optional.empty());
    }

    public Movie() {
        setState(State.UNTOUCHED);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final String sep = ",";

        sb.append(getTitle()).append(sep)
            .append(getYearOfAward()).append(sep)
            .append(getDirector()).append(sep)
            .append(getMainActor()).append(sep)
            .append(getTitleEnglish()).append(sep)
            .append(getGenre());

        return sb.toString();
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Movie.State getState() {
        return State.values()[state.get()];
    }

    public IntegerProperty stateProperty() {
        return state;
    }

    public void setState(State state) {
        this.state.set(state.ordinal());
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getYearOfAward() {
        return yearOfAward.get();
    }

    public IntegerProperty yearOfAwardProperty() {
        return yearOfAward;
    }

    public void setYearOfAward(int yearOfAward) {
        this.yearOfAward.set(yearOfAward);
    }

    public String getDirector() {
        return director.get();
    }

    public StringProperty directorProperty() {
        return director;
    }

    public void setDirector(String director) {
        this.director.set(director);
    }

    public String getMainActor() {
        return mainActor.get();
    }

    public StringProperty mainActorProperty() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor.set(mainActor);
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getTitleEnglish() {
        return titleEnglish.get();
    }

    public StringProperty titleEnglishProperty() {
        return titleEnglish;
    }

    public void setTitleEnglish(String titleEnglish) {
        this.titleEnglish.set(titleEnglish);
    }

    public int getYearOfProduction() {
        return yearOfProduction.get();
    }

    public IntegerProperty yearOfProductionProperty() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction.set(yearOfProduction);
    }

    public ObservableList<String> getCountry() {
        return country;
    }

    public void setCountry(ObservableList<String> country) {
        this.country = country;
    }

    public int getDuration() {
        return duration.get();
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration.set(duration);
    }

    public int getFsk() {
        return fsk.get();
    }

    public IntegerProperty fskProperty() {
        return fsk;
    }

    public void setFsk(int fsk) {
        this.fsk.set(fsk);
    }

    public Optional<LocalDate> getStartDate() {
        return startDate.get();
    }

    public ObjectProperty<Optional<LocalDate>> startDateProperty() {
        return startDate;
    }

    public void setStartDate(Optional<LocalDate> startDate) {
        this.startDate.set(startDate);
    }

    public int getNumberOfOscars() {
        return numberOfOscars.get();
    }

    public IntegerProperty numberOfOscarsProperty() {
        return numberOfOscars;
    }

    public void setNumberOfOscars(int numberOfOscars) {
        this.numberOfOscars.set(numberOfOscars);
    }
}
