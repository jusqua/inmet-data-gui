/**
 * The CityData data struct
 */
public class CityData
{
    private String id;
    private String name;

    public static final String PATH = "cache.json";
    public static final String BASE_URL = "https://apitempo.inmet.gov.br/WSI/estacoes/T";

    /**
     * Get id
     * @return id
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Get name
     * @return name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Set id
     * @param id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Set name
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }
}
