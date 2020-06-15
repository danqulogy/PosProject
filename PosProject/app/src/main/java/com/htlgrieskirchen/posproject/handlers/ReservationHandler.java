package com.htlgrieskirchen.posproject.handlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.posproject.beans.Reservation;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public class ReservationHandler {

    private static List<Reservation> reservationList;

    public static void addReservation(Reservation reservation){
        reservationList.add(reservation);
    }

    public static void safeReservations(OutputStream os){
        try(PrintWriter pw = new PrintWriter(os)){
            Gson gson = new Gson();
            String output = gson.toJson(reservationList);
            pw.write(output);
            pw.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void readReservations(InputStream is){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            Gson gson = new Gson();
            StringBuilder sb = new StringBuilder();
            while(br.ready()){
                sb.append(br.readLine());
            }
            TypeToken<List<Reservation>> typeToken = new TypeToken<List<Reservation>>(){};
            reservationList = gson.fromJson(sb.toString(), typeToken.getType());
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
