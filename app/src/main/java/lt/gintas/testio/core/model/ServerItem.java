package lt.gintas.testio.core.model;

/**
 * Created by gintautas on 03/12/2017.
 */

public class ServerItem {

    private String name;
    private String distance;

    public ServerItem(String name, String distance) {
        this.name = name;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

}
