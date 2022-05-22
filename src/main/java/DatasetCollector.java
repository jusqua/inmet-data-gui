import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Takes responsability to get the datasets from the APIs
 */
public class DatasetCollector
{
    /**
     * Gets the CityData dataset from API and caches data locally
     * @param cityTipe
     * @return dataset
     * @throws IOException
     */
    public static ArrayList<CityData> getCityData() throws IOException
    {
        final Type CITY_DATA_TYPE_TOKEN = new TypeToken<ArrayList<CityData>>(){}.getType();

        Path path = Paths.get(CityData.PATH);

        if (Files.exists(path))
        {
            Gson gson = new Gson();
            String json = Files.readString(path);
            return gson.fromJson(json, CITY_DATA_TYPE_TOKEN);
        }

        String content = fetchContent(CityData.BASE_URL);
        if (content == null)
        {
            return null;
        }

        Gson gson = new GsonBuilder()
                        .registerTypeAdapter(ArrayList.class, new CityDataDeserializer())
                        .serializeNulls()
                        .create();
        ArrayList<CityData> data = gson.fromJson(content, CITY_DATA_TYPE_TOKEN);
        
        Files.write(path, gson.toJson(data).getBytes());

        return data;
    }

    /**
     * Gets WeatherData dataset from API
     * @param id
     * @param choice
     * @return dataset
     * @throws IOException
     */
    public static ArrayList<WeatherData> getWeatherData(String id, Timestamp choice) throws IOException
    {
        final Type WEATHER_DATA_TYPE_TOKEN = new TypeToken<ArrayList<WeatherData>>(){}.getType();
        LocalDate dateTime = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String today = dateTime.format(formatter);
        String target = dateTime.minusDays(choice.getDaysAgo()).format(formatter);

        String content = fetchContent(WeatherAPI.matchURL(target, today, id));
        Gson gson = new GsonBuilder()
                        .registerTypeAdapter(ArrayList.class, new WeatherDataDeserializer())
                        .serializeNulls()
                        .create();

        return gson.fromJson(content, WEATHER_DATA_TYPE_TOKEN);
    }

    /**
     * Download the Json object
     * @param _url
     * @return content
     * @throws IOException
     */
    private static String fetchContent(String _url) throws IOException
    {
        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        int responseCode = connection.getResponseCode();

        if(responseCode != 200)
        {
            return null;
        }

        BufferedReader scanner = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer response = new StringBuffer();
        String buffer;

        while ((buffer = scanner.readLine()) != null)
        {
            response.append(buffer);
        }
        scanner.close();
        
        return response.toString();
    }
}
