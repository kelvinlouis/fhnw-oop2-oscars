package ch.fhnw.oop2.project.table;

import ch.fhnw.oop2.project.Movie;

/**
 * Created by Kelvin on 08-Jun-16.
 */
public interface TableActionListener {
    void onYearOfProductionChange(int year);
    void onTitleChange(String title);
    void onMainActorChange(String actors);
    void onDirectorChange(String actors);
    void onSelectedMovieChange(Movie movie);
}
