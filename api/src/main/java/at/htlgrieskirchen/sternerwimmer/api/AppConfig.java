package at.htlgrieskirchen.sternerwimmer.api;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    public static final String DATE_PATTERN = "dd.MM.yyyy hh:mm";

    public @Bean MongoClient mongoClient(){
        return MongoClients.create("mongodb://localhost:27017");
    }

}
