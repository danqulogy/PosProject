package at.htlgrieskirchen.sternerwimmer.api.classes;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "restaurants", path = "restaurants")
public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    public Restaurant findByName(@Param("name")String name);
    public Restaurant findByRestaurantNumber(@Param("restaurantNumber") String restaurantNumber);
}
