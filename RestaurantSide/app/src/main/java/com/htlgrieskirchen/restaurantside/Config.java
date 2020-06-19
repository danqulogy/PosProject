package com.htlgrieskirchen.restaurantside;

//static variables

public class Config {

    public static String RESTAURANT_NUMBER = "1";

    //ReQuest code for intents (100, 101, 102, ...)
    public static int RQ_RESERVATION_INTENT = 100;

    //  *STANDARD URL*  //
    //server URL
    public static String SERVER_URL = "http://varchar42.me:4242/";


    //  *RESERVATION URL*   //
    //URL for adding reservation on a table
    public static String RESERVATION_ADD_URL = "reservations/addReservation"; //PUT with Reservation as Json
    //URL for deleting reservation
    public static String RESERVATION_DELETE_URL = "reservations/deleteReservation?id="; //with reservationID
    //URL for getting reservation via id
    public static String RESERVATION_BY_ID_URL = "reservations/getReservationById?id=";


    //  *RESTAURANT URL*    //
    //URL for restaurants ; get DBID of restaurant with name
    public static String RESTAURANT_GETDBID_URL = "restaurants/getdbid?name=";
    //URL for restaurants containing name
    public static String RESTAURANT_FINDBYNAME_URL = "restaurants/findByName?name=";
    //URL for all restaurants in de database
    public static String RESTAURANT_GETALL_URL = "restaurants/getAllRestaurants";
    //URL for updating restaurant
    public static String RESTAURANT_UPDATE_URL = "updateRestaurant";
    //URL for put databaseId
    public static String RESTAURANT_PUT_URL = "restaurants/"; //objectId from database
    //URL for restaurant by reservationID
    public static String RESTAURANT_BY_RESERVATION_ID_URL = "restaurants/getRestaurantByReservationId?reservationId=";
    //URL for restaurants by address
    public static String RESTAURANT_GET_BY_ADDRESS_URL1 = "restaurants/getTenNearestRestaurantsByAddress?address=";
    public static String RESTAURANT_GET_BY_ADDRESS_URL2 = "&distance=";
    //URL for restaurant by restaurantNumber
    public static String RESTAURANT_BY_RESTAURANT_NUMBER = "restaurants/getByRestaurantNumber?restaurantNumber=";

}
