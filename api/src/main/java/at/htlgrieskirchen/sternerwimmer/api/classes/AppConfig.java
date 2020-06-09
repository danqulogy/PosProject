package at.htlgrieskirchen.sternerwimmer.api.classes;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static final String DATE_PATTERN = "dd.MM.yyyy hh:mm";

    public @Bean MongoClient mongoClient(){
        return MongoClients.create("mongodb://admin:WsNWPbvt9QW363ak@188.68.56.42:27017/restaurantdb");
    }

}
