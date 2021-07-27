package pl.shalpuk.scooterService.helper;

import com.google.common.collect.Lists;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.shalpuk.scooterService.model.Coordinates;
import pl.shalpuk.scooterService.model.Location;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class LocationHelper {

    public static List<Location> preparationLocations() {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("C:\\Users\\48798\\IdeaProjects\\ScooterSharing\\src\\main\\resources\\Warsaw.json", StandardCharsets.UTF_8)) {
            JSONArray array = (JSONArray) jsonParser.parse(reader);

            List<Location> locations = (List<Location>) array.stream().limit(25).filter(ob -> {
                JSONObject jsonObject = (JSONObject) ob;
                JSONObject property = (JSONObject) jsonObject.get("properties");
                return property.get("city").equals("Warszawa");
            }).map(o -> {
                JSONObject jsonObject = (JSONObject) o;
                JSONObject property = (JSONObject) jsonObject.get("properties");
                JSONArray coordinatesArray = (JSONArray) jsonObject.get("coordinates");

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
            }).collect(Collectors.toList());
            return locations;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return Lists.newArrayList();
    }

}
