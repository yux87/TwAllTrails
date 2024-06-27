package tw.team1.trail.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyProvider {

    @Value("${google.maps.api.key}")
    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }
}
