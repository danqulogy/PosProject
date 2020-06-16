package com.htlgrieskirchen.posproject.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.posproject.beans.Reservation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationHandler {

    private static List<Reservation> reservationList = new ArrayList<>();

    public static void addReservation(Reservation reservation){
        reservationList.add(reservation);
    }

    public static void deleteReservation(String reservationId){
        for(Reservation r: reservationList){
            if(r.getId().equals(reservationId)) reservationList.remove(r);
        }
    }

    public static void safeReservations(OutputStream os){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        try(PrintWriter pw = new PrintWriter(os)){

            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for(int i = 0; i < reservationList.size(); i ++){
                Reservation reservation = reservationList.get(i);
                String sJson = "{\"restaurantNumber\":\"" +reservation.getRestaurantNumber() +"\", \"tableNumber\":\"" +reservation.getTableNumber()
                        +"\", \"id\":\"" +reservation.getId() +"\", \"chairs\":\"" +reservation.getChairs()
                        +"\", \"reservationStart\":\"" +dtf.format(reservation.getReservationStart()) +"\", \"reservationEnd\":\"" +dtf.format(reservation.getReservationEnd()) +"\"}";
                sb.append(sJson);
                if(i == reservationList.size()-1) sb.append("]");
                else sb.append(", ");
            }

            pw.write(sb.toString());
            pw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void readReservations(InputStream is){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        reservationList.clear();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){

            String input = br.readLine();

            input = input.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\"", "").trim();


            String[] arr = input.split("\\}");
            for(String s: arr){
                if(s.equals("") || s.isEmpty()) break;
                s = s.replaceAll("\\{", "").trim();
                String[] arr1 = s.split(",");
                LocalDateTime start = LocalDateTime.parse(arr1[4].split(":")[1] +":"+ arr1[4].split(":")[2], dtf);
                LocalDateTime end = LocalDateTime.parse(arr1[5].split(":")[1] +":"+ arr1[5].split(":")[2], dtf);
                reservationList.add(new Reservation(Integer.parseInt(arr1[0].split(":")[1]), Integer.parseInt(arr1[1].split(":")[1]), arr1[2].split(":")[1], Integer.parseInt(arr1[3].split(":")[1]), start, end));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Reservation> getReservationList() {
        return reservationList;
    }

    public static void setReservationList(List<Reservation> reservationList) {
        ReservationHandler.reservationList = reservationList;
    }
}
