package com.bushy.fnbgame;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@OpenAPIDefinition(info =
@Info(title = "FNB Board Game",
        version = "1.0.0",
        description = "FNB Board Game API documentation")
)
public class FnbGameApplication {

    public static void main(String[] args) {
        SpringApplication.run(FnbGameApplication.class, args);
    }

}
