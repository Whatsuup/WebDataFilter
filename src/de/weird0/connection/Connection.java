package de.weird0.connection;

import java.io.File;
import java.net.URL;

public interface Connection {

    boolean setURL(URL url);

    boolean connect();

    void disconnect();

    File loadFile();

    File getFile();
}
