package ch.fhnw.oop2.project.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Kelvin on 24-May-16.
 */
public interface DataService <T> {
    List<T> getAll();
    void save(File file, List<T> list) throws IOException;
    void save(File file) throws IOException;
    T createItem();
    void removeItem(T item);
}
