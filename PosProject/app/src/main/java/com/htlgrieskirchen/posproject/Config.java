package com.htlgrieskirchen.posproject;

//static variables

public class Config {
    //ReQuest permissions as Binary (100000, 100001, 100010, ...)
    public static int RQ_FINE_LOCATION = 100000;

    //ReQuest code for intents (100, 101, 102, ...)
    public static int RQ_RESERVATION_INTENT = 100;

    //ResultCode as [Binary] (1000, 1001, 1010, ...)
    public static int RC_SETTINGS = 1000;

    //files
    public static String FILE_FAV_PLACES = "favPlaces.csv";
    public static String FILE_RESERVATIONS = "reservations.csv";


    //  *STANDARD URL*  //
    //server URL
    public static String SERVER_URL = "http://varchar42.me:4242/";


    //  *RESERVATION URL*   //
    //URL for reservations on a table in a restaurant
    public static String RESERVATION_RESERVATIONSFORTABLE_URL1 = "reservations/getReservationsForTable?restaurantNumber=";
    public static String RESERVATION_RESERVATIONSFORTABLE_URL2 = "&tableNumber=1";
    //URL for adding reservation on a table
    public static String RESERVATION_ADD_URL = "";


    //  *RESTAURANT URL*    //
    //URL for restaurants ; get DBID of restaurant with name
    public static String RESTAURANT_GETDBID_URL = "restaurants/getdbid?name=";

    //URL for restaurants containing name
    public static String RESTAURANT_FINDBYNAME_URL = "restaurants/findByName?name=";

    //URL for all restaurants in de database
    public static String RESTAURANT_GETALL_URL = "restaurants/getAllRestaurants";

    //URL for nearest restaurants
    public static String RESTAURANT_NEAREST_URL1 = "restaurants/getTenNearestRestaurants?lon=";
    public static String RESTAURANT_NEAREST_URL2 = "&lat=";
    public static String RESTAURANT_URL_DISTANCE = "&distance="; //distance in metres

    //URL for updating restaurant
    public static String RESTAURANT_UPDATE_URL = "updateRestaurant";

    //URL for put databaseId
    public static String RESTAURANT_PUT_URL = "restaurants/"; //objectId from database

}
