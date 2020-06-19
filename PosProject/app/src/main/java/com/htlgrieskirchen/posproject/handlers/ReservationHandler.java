package com.htlgrieskirchen.posproject.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.htlgrieskirchen.posproject.beans.Reservation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationHandler {

    private static List<Reservation> reservationList = new ArrayList<>();

    public static void addReservation(Reservation reservation) {
        reservationList.add(reservation);
    }

    public static void deleteReservation(String reservationId) {
        for (Reservation r : reservationList) {
            if (r.getId().equals(reservationId)) reservationList.remove(r);
        }
    }

    public static void safeReservations(OutputStream os) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os))) {
            JsonSerializer<LocalDateTime> localDateTimeJsonSerializer = new JsonSerializer<LocalDateTime>() {
                @Override
                public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
                    return new JsonPrimitive(src.format(dtf));
                }
            };
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, localDateTimeJsonSerializer);
            String string = gsonBuilder.create().toJson(reservationList, new TypeToken<List<Reservation>>() {
            }.getType());
            bw.write(string);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readReservations(InputStream is) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        reservationList.clear();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line = br.readLine();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                @Override
                public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String test = json.getAsString();
                    return LocalDateTime.parse(json.getAsString(), dtf);
                }
            });
            reservationList = gsonBuilder.create().fromJson(line, new TypeToken<List<Reservation>>() {
            }.getType());
        } catch (Exception e) {
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
