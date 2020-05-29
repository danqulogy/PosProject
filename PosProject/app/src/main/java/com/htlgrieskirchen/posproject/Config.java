package com.htlgrieskirchen.posproject;

//static variables

public class Config {
    //ReQuest permissions as Binary (100000, 100001, 100010, ...)
    public static int RQ_FINE_LOCATION = 100000;

    //ResultCode as [Binary] (1000, 1001, 1010, ...)
    public static int RC_SETTINGS = 1000;

    //server URL
    public static String SERVER_URL = "http://85.118.186.133:8080/";
    //URL for reservations
    public static String RESERVATIONS_URL1 = "reservations/getReservationsForTable?restaurantNumber=";
    public static String RESERVATIONS_URL2 = "&tableNumber=1";
    //URL for reservationCodes
    public static String RESERVATION_CODE_URL = "http://85.118.186.133:8080/restaurants/getdbid?name=";
    //URL for nearest restaurants
    public static String RESTAURANT_URL1 = "http://85.118.186.133:8080/restaurants/getTenNearestRestaurants?lon=";
    public static String RESTAURANT_URL2 = "&lat=";
    public static String RESTAURANT_URL_DISTANCE = "&distance"; //distance in metres
}
