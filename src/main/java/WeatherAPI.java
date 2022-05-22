/**
 * Weather API controller
 */
public class WeatherAPI
{
    private static final String BASE_URL = "https://apitempo.inmet.gov.br/estacao/";

    /**
     * Match info provided to match the data API
     * @param target
     * @param today
     * @param id
     * @return url
     */
    public static String matchURL(String target, String today, String id)
    {
        return String.format("%s/%s/%s/%s", BASE_URL, target, today, id);
    }
}
