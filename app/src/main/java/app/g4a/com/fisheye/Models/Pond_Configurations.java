package app.g4a.com.fisheye.Models;

public class Pond_Configurations {

    public String pondName, length, width, depth, water, fish1, fish2,
            fish3, fish4, release1, release2, release3, release4, harvest1,
            harvest2, harvest3, harvest4;

    public Pond_Configurations() {
    }

    public Pond_Configurations(String pondName, String length, String width, String depth,
                               String water, String fish1, String fish2, String fish3, String fish4,
                               String release1, String release2, String release3, String release4,
                               String harvest1, String harvest2, String harvest3, String harvest4) {
        this.pondName = pondName;
        this.length = length;
        this.width = width;
        this.depth = depth;
        this.water = water;
        this.fish1 = fish1;
        this.fish2 = fish2;
        this.fish3 = fish3;
        this.fish4 = fish4;
        this.release1 = release1;
        this.release2 = release2;
        this.release3 = release3;
        this.release4 = release4;
        this.harvest1 = harvest1;
        this.harvest2 = harvest2;
        this.harvest3 = harvest3;
        this.harvest4 = harvest4;
    }
}
