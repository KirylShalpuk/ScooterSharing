package pl.shalpuk.scooterService.helper;

import com.google.common.collect.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import pl.shalpuk.scooterService.model.Coordinates;
import pl.shalpuk.scooterService.model.Location;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class LocationHelper {

    public static List<Location> preparationLocations() {
        JSONParser jsonParser = new JSONParser();
        try (InputStream inputStream = new ClassPathResource("locations/Warsaw.json").getInputStream()) {
            InputStreamReader reader = new InputStreamReader(inputStream);
            JSONArray locationArray = (JSONArray) jsonParser.parse(reader);

            List<Location> locations = (List<Location>) locationArray.stream()
                    .limit(25)
                    .map(o -> getLocationFromArray((JSONObject) o))
                    .collect(Collectors.toList());
            return locations;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

    private static Location getLocationFromArray(JSONObject locationObject) {
        JSONObject property = (JSONObject) locationObject.get("properties");
        JSONArray coordinatesArray = (JSONArray) locationObject.get("coordinates");

        Coordinates coordinates = new Coordinates();
        coordinates.setLatitude(coordinatesArray.get(0).toString());
        coordinates.setLongitude(coordinatesArray.get(1).toString());

        Location location = new Location();
        location.setCountry("Polska");
        location.setCity((String) property.get("city"));
        location.setStreet((String) property.get("street"));
        location.setBuilding((String) property.get("number"));
        location.setCoordinates(coordinates);

        return location;
    }
}
