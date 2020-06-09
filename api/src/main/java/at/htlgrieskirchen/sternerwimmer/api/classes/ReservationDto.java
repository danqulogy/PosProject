package at.htlgrieskirchen.sternerwimmer.api.classes;

public class ReservationDto {
    public String talbeNumber;
    public String reservationNumber;
    public String reservationStart;
    public String reservationEnd;

    public ReservationDto() {
    }

    public ReservationDto(String talbeNumber, String reservationNumber, String reservationStart, String reservationEnd) {
        this.talbeNumber = talbeNumber;
        this.reservationNumber = reservationNumber;
        this.reservationStart = reservationStart;
        this.reservationEnd = reservationEnd;
    }

    public String getTalbeNumber() {
        return talbeNumber;
    }

    public void setTalbeNumber(String talbeNumber) {
        this.talbeNumber = talbeNumber;
    }

    public String getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(String reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(String reservationStart) {
        this.reservationStart = reservationStart;
    }

    public String getReservationEnd() {
        return reservationEnd;
    }

    public void setReservationEnd(String reservationEnd) {
        this.reservationEnd = reservationEnd;
    }
}
