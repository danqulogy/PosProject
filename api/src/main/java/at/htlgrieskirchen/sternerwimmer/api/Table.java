package at.htlgrieskirchen.sternerwimmer.api;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Table {
    @Id
    public String id;
    public String tableNumber;
    public String chairsAvailable;
    public List<Reservation> reservations;


    public Table() {
    }


    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getId() {
        return id;
    }

    public String getChairsAvailable() {
        return chairsAvailable;
    }

    public void setChairsAvailable(String chairsAvailable) {
        this.chairsAvailable = chairsAvailable;
    }


    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                ", tableNumber='" + tableNumber + '\'' +
                ", chairsAvailable='" + chairsAvailable + '\'' +
                '}';
    }
}
