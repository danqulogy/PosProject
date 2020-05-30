package com.htlgrieskirchen.posproject;

//static variables

public class Config {
    //ReQuest permissions as Binary (100000, 100001, 100010, ...)
    public static int RQ_FINE_LOCATION = 100000;

    //ResultCode as [Binary] (1000, 1001, 1010, ...)
    public static int RC_SETTINGS = 1000;


    //  *STANDARD URL*  //
    //server URL
    public static String SERVER_URL = "http://85.118.186.133:8080/";


    //  *RESERVATION URL*   //
    //URL for reservations on a table in a restaurant
    public static String RESERVATION_URL1 = "reservations/getReservationsForTable?restaurantNumber=";
    public static String RESERVATION_URL2 = "&tableNumber=1";
    //URL for adding reservation on a table
    public static String RESERVATION_ADD_URL = "";


    //  *RESTAURANT URL*    //
    //URL for restaurants with the entered name (objectId)
    public static String RESTAURANT_GETDBID_URL = "restaurants/getdbid?name=";

    //URL for restaurants containing name
    public static String RESTAURANT_FINDBYNAME_URL = "restaurants/findByName?name=";

    //URL for all restaurants in de database
    public static String RESTAURANT_GETALL_URL = "restaurants/getAllRestaurants";

    //URL for nearest restaurants
    public static String RESTAURANT_URL1 = "restaurants/getTenNearestRestaurants?lon=";
    public static String RESTAURANT_URL2 = "&lat=";
    public static String RESTAURANT_URL_DISTANCE = "&distance"; //distance in metres

    //URL for put dbid
    public static String RESTAURANT_PUT_URL = "restaurants/"; //objectId from database


}
