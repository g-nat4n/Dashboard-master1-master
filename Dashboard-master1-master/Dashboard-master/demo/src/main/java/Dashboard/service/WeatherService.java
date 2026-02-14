package Dashboard.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WeatherService {

    // Certifique-se de que NÃO HÁ ESPAÇOS dentro das aspas
    private final String API_KEY = "e5018a09";

    public Map<String, Object> getWeatherByCoords(String lat, String lon) {
        // Montamos a URL garantindo que lat e lon sejam injetados corretamente
        String url = "https://api.hgbrasil.com/weather?key=" + API_KEY + "&lat=" + lat + "&lon=" + lon + "&user_ip=remote";

        RestTemplate restTemplate = new RestTemplate();
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            // A HG Brasil retorna os dados úteis dentro de "results"
            return (Map<String, Object>) response.get("results");
        } catch (Exception e) {
            System.out.println("Erro no Service: " + e.getMessage());
            return Map.of("error", "Erro ao buscar clima por coordenadas");
        }
    }
}