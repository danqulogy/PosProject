package at.htlgrieskirchen.sternerwimmer.api.classes;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/restaurants")
public class RestaurantRestController {
    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping("/getTenNearestRestaurants")
    public List<Restaurant> getTenNearestaurants(@RequestParam(value = "lon", defaultValue = "-1") String lon, @RequestParam(value = "lat", defaultValue = "-1") String lat, @RequestParam(value
            = "distance", defaultValue = "25000") String distance) {
        return restaurantRepository.findAll()
                .stream()
                .filter((r -> {
                    double R = 6371e3;
                    double lat1 = Double.parseDouble(r.getLat());
                    double lat2 = Double.parseDouble(lat);
                    double lon1 = Double.parseDouble(r.getLon());
                    double lon2 = Double.parseDouble(lon);
                    double phi1 = lat1 * Math.PI / 180;
                    double phi2 = lat2 * Math.PI / 180;
                    double dphi = (lat2 - lat1) * Math.PI / 180;
                    double dlambda = (lon2 - lon1) * Math.PI / 180;

                    double a = Math.sin(dphi / 2) * Math.sin(dphi / 2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2) * Math.sin(dlambda / 2);
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
                    double d = R * c;
                    System.out.println(d);
                    return d <= Integer.parseInt(distance);
                })).limit(10).collect(Collectors.toList());

    }

    @GetMapping("findTenNearestRestaurantsByAddress")
    public List<Restaurant> findTenNearestRestaurantsByAddress(@RequestParam(value = "address") String address, @RequestParam(value = "distance", defaultValue = "25000") String distance) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("https://eu1.locationiq.com/v1/search.php?key=897f819f68407b&q=" + address + "&format=json").openConnection();
            httpURLConnection.setRequestMethod("GET");
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.read());
            }
            Gson gson = new Gson();
            Location location = gson.fromJson(stringBuilder.toString(), Location.class);
            System.out.println("debug for location object returned by LocationIQ: " + location.getLon() + " " + location.getLat());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/getdbid")
    public java.lang.String getDbId(@RequestParam(value = "name", defaultValue = "error") String name) {
        return restaurantRepository.findByName(name).getId();
    }

    @GetMapping("/findByName")
    public List<Restaurant> findByname(@RequestParam(value = "name") String name) {
        return restaurantRepository.findAll()
                .stream()
                .filter(r -> r.name.contains(name))
                .collect(Collectors.toList());
    }


    @GetMapping("/getAllRestaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }


}
