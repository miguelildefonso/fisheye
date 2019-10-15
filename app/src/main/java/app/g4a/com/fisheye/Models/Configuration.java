package app.g4a.com.fisheye.Models;

public class Configuration {

    public String name, length, width, depth, fishType,date;

    public Configuration() {
    }

    public Configuration(String name, String length, String width, String depth, String fishType, String date) {
        this.name = name;
        this.length = length;
        this.width = width;
        this.depth = depth;
        this.fishType = fishType;
        this.date = date;
    }
}
