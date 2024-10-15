package me.vg.todo_crud.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "cors.configuration")
public class CorsConfiguration {

    private String[] origins;

    public String[] getOrigins() {
        return this.origins;
    }

    public void setOrigins(String[] origins) {
        this.origins = origins;
    }
}
