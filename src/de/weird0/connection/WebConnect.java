package de.weird0.connection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.UUID;

public class WebConnect implements de.weird0.connection.Connection {

    public boolean connected;
    private URL url;
    private HttpURLConnection connection;
    private File file;

    public WebConnect(URL url) {
        this.file = null;
        this.url = url;
        connect();
    }

    @Override
    public boolean setURL(URL url) {
        this.url = url;
        return connect();
    }

    @Override
    public boolean connect() {
        try {
            this.connection = (HttpURLConnection) this.url.openConnection();
            this.connection.connect();

            this.connected = this.connection.getResponseCode() != -1;
            return this.connection.getResponseCode() != -1;
        } catch (IOException e) {
            System.err.println("Error creating HTTP connection");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void disconnect() {
        this.connection.disconnect();
    }

    @Override
    public File loadFile() {
        try {
            File file = new File("src/de/weird0/tmp", UUID.randomUUID() + ".txt");
            FileWriter writer = new FileWriter(file);
            Scanner scanner = new Scanner(this.url.openStream());
            while(scanner.hasNextLine()) {
                writer.write(scanner.nextLine());
            }
            writer.close();
            this.file = file;
            return this.file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public File getFile() {
        return this.file == null ? loadFile() : this.file;
    }
}
