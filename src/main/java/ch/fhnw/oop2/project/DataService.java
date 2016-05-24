package ch.fhnw.oop2.project;

import java.util.List;

/**
 * Created by Kelvin on 24-May-16.
 */
public interface DataService <T> {
    public List<T> getAll();
    public void save(List<T> list);
    public T createItem();
}
