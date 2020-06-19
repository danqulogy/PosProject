package at.htlgrieskirchen.sternerwimmer.api.classes;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Restaurant {
    @Id
    public String id;
    public String restaurantNumber;
    public String lon;
    public String lat;
    public String name;
    public String openingTimes;
    public String infos;
    public List<Table> tables;


    public Restaurant() {
    }

    public Restaurant(String restaurantNumber, String lon, String lat, String name, String openingTimes, String infos, List<Table> tables) {
        this.restaurantNumber = restaurantNumber;
        this.lon = lon;
        this.lat = lat;
        this.name = name;
        this.openingTimes = openingTimes;
        this.infos = infos;
        this.tables = tables;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpeningTimes() {
        return openingTimes;
    }

    public void setOpeningTimes(String openingTimes) {
        this.openingTimes = openingTimes;
    }

    public String getInfos() {
        return infos;
    }

    public void setInfos(String infos) {
        this.infos = infos;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public String getRestaurantNumber() {
        return restaurantNumber;
    }

    public void setRestaurantNumber(String restaurantNumber) {
        this.restaurantNumber = restaurantNumber;
    }

    public String getId() {
        return id;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
