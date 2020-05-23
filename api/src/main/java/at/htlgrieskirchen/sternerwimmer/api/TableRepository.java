package at.htlgrieskirchen.sternerwimmer.api;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "tables", path = "tables")
public interface TableRepository extends MongoRepository<Table, String> {
    public List<Table> findByChairsAvailible(@Param("chairsAvailible") String chairsAvailible);

    public Table findByTableNumber(@Param("tableNumber") String tableNumber);
}
