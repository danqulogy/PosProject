package at.htlgrieskirchen.sternerwimmer.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    public Restaurant findByName(@Param("name")String name);
}
