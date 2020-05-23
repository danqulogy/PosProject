package at.htlgrieskirchen.sternerwimmer.api;

 import org.springframework.data.annotation.Id;
public class Table {
    @Id
    public String id;
    public String tableNumber;
    public String chairsAvailible;
    public String reservedTimeStart;
    public String reservedTimeEnd;
    public String isReserved;


    public Table() {
    }

    public Table(String tableNumber, String chairsAvailible, String reservedTimeStart, String reservedTimeEnd, String isReserved) {
        this.tableNumber = tableNumber;
        this.chairsAvailible = chairsAvailible;
        this.reservedTimeStart = reservedTimeStart;
        this.reservedTimeEnd = reservedTimeEnd;
        this.isReserved = isReserved;
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

    public String getChairsAvailible() {
        return chairsAvailible;
    }

    public void setChairsAvailible(String chairsAvailible) {
        this.chairsAvailible = chairsAvailible;
    }

    public String getReservedTimeStart() {
        return reservedTimeStart;
    }

    public void setReservedTimeStart(String reservedTimeStart) {
        this.reservedTimeStart = reservedTimeStart;
    }

    public String getReservedTimeEnd() {
        return reservedTimeEnd;
    }

    public void setReservedTimeEnd(String reservedTimeEnd) {
        this.reservedTimeEnd = reservedTimeEnd;
    }

    public String getIsReserved() {
        return isReserved;
    }

    public void setIsReserved(String isReserved) {
        this.isReserved = isReserved;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id='" + id + '\'' +
                ", tableNumber='" + tableNumber + '\'' +
                ", chairsAvailible='" + chairsAvailible + '\'' +
                ", reservedTimeStart='" + reservedTimeStart + '\'' +
                ", reservedTimeEnd='" + reservedTimeEnd + '\'' +
                ", isReserved='" + isReserved + '\'' +
                '}';
    }
}
