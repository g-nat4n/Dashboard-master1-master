package Dashboard.Controller;

import Dashboard.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; // ESTE É O IMPORT CORRETO
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/clima")
@CrossOrigin(origins = "*")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/coordenadas")
    public Map<String, Object> getClimaPorCoords(
            @RequestParam("lat") String lat,
            @RequestParam("lon") String lon) {

        return weatherService.getWeatherByCoords(lat, lon);
    }
}

