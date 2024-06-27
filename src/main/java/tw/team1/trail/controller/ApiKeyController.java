package tw.team1.trail.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ApiKeyController {

    @Value("${google.maps.api.key}")
    private String apiKey;

    @GetMapping("/api/google-maps-api-key")
    public Map<String, String> getGoogleMapsApiKey() {
        Map<String, String> response = new HashMap<>();
        response.put("apiKey", apiKey);
        return response;
    }
}
