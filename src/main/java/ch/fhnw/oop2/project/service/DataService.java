package ch.fhnw.oop2.project.service;

import java.util.List;

/**
 * Created by Kelvin on 24-May-16.
 */
public interface DataService <T> {
    List<T> getAll();
    void save(List<T> list);
    T createItem();
    void removeItem(T item);
}
