package at.htlgrieskirchen.sternerwimmer.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    = "distance", defaultValue = "25")String distance) {
        return restaurantRepository.findAll()
                .stream()
                .filter((r -> {
                    double R = 6371e3;
                    double lat1 = Double.parseDouble(r.getLat());
                    double lat2 = Double.parseDouble(lat);
                    double lon1 = Double.parseDouble(r.getLon());
                    double lon2 = Double.parseDouble(lon);
                    double phi1 = lat1 * Math.PI/180;
                    double phi2 = lat2 * Math.PI/180;
                    double dphi = (lat2-lat1) * Math.PI/180;
                    double dlambda =(lon2-lon1) * Math.PI/180;

                    double a = Math.sin(dphi/2) * Math.sin(dphi/2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda/2) * Math.sin(dlambda/2);
                    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                    double d = R*c;
                    System.out.println(d);
                    return d <= Integer.parseInt(distance);
                })).limit(10).collect(Collectors.toList());

    }

    @GetMapping("/getdbid")
    public String getDbId(@RequestParam(value = "name", defaultValue = "error") String name) {
        return restaurantRepository.findByName(name).getId();
    }

    @GetMapping("/findByName")
    public List<Restaurant> findByname(@RequestParam(value = "name")String name){
        return restaurantRepository.findAll()
                .stream()
                .filter(r -> r.name.contains(name))
                .collect(Collectors.toList());
    }
}
