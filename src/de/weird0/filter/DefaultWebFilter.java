package de.weird0.filter;

import de.weird0.connection.Connection;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DefaultWebFilter implements Filter<String, String> {

    private File source;
    private List<String> keys = new ArrayList<>();
    private List<String> data = new ArrayList<>();

    public DefaultWebFilter(String[] filter) {
        assert filter.length > 0;
        this.keys.addAll(Arrays.asList(filter));
    }

    @Override
    public File getSource(Connection connection) {
        this.source = connection.getFile();
        return this.source;
    }

    public List<String> filter() {
        return filter(null);
    }

    @Override
    public List<String> filter(File file) {
        if (file == null) file = this.source;
        String text = fileToText(file);
        text = applyKeys(text);
        return Arrays.asList(split(text));
    }

    @Override
    public void addFilter(String key) {
        this.keys.add(key);
    }

    @Override
    public List<String> getFilterData() {
        return this.data;
    }

    private String[] split(String s) {
        return s.split(" ");
    }

    private String applyKeys(String toBeFiltered) {
        for (String key : this.keys) {
            toBeFiltered = toBeFiltered.replaceAll(key, "");
        }
        return toBeFiltered;
    }

    private String fileToText(File file) {
        try {
            String text = "";
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                text += scanner.nextLine();
            }
            return text;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
