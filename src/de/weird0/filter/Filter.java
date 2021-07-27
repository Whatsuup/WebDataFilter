package de.weird0.filter;

import de.weird0.connection.Connection;

import java.io.File;
import java.util.List;

public interface Filter<E, K> {

    File getSource(Connection connection);

    List<E> filter(File file);

    void addFilter(K key);

    List<E> getFilterData();

}
