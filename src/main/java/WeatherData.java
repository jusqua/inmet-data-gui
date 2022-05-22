import java.util.Date;

/**
 * The WeatherData data struct
 */
public class WeatherData
{
    private String id;
    private Date dateTime;
    private Float precipitation;
    private Float radiation;
    private Float temperatureInst, temperatureMin, temperatureMax;
    private Float moistureInst, moistureMin, moistureMax;
    private Float pressureInst, pressureMin, pressureMax;
    private Float dewInst, dewMin, dewMax;

    /**
     * This is an impossible number to use instead of null type
     */
    public static final Float NAN = -1f;

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Date getDateTime()
    {
        return this.dateTime;
    }

    public void setDateTime(Date dateTime)
    {
        this.dateTime = dateTime;
    }

    public void setPrecipitation(Float precipitation)
    {
        this.precipitation = precipitation;
    }

    public Float getPrecipitation()
    {
        return this.precipitation;
    }

    public void setTemperatureInst(Float temperatureInst)
    {
        this.temperatureInst = temperatureInst;
    }

    public Float getTemperatureInst()
    {
        return this.temperatureInst;
    }

    public void setTemperatureMin(Float temperatureMin)
    {
        this.temperatureMin = temperatureMin;
    }

    public Float getTemperatureMin()
    {
        return this.temperatureMin;
    }

    public void setTemperatureMax(Float temperatureMax)
    {
        this.temperatureMax = temperatureMax;
    }

    public Float getTemperatureMax()
    {
        return this.temperatureMax;
    }

    public void setRadiation(Float radiation)
    {
        this.radiation = radiation;
    }

    public Float getRadiation()
    {
        return this.radiation;
    }

    public void setMoistureInst(Float moistureInst)
    {
        this.moistureInst = moistureInst;
    }

    public Float getMoistureInst()
    {
        return this.moistureInst;
    }

    public void setMoistureMin(Float moistureMin)
    {
        this.moistureMin = moistureMin;
    }

    public Float getMoistureMin()
    {
        return this.moistureMin;
    }

    public void setMoistureMax(Float moistureMax)
    {
        this.moistureMax = moistureMax;
    }

    public Float getMoistureMax()
    {
        return this.moistureMax;
    }

    public void setDewInst(Float dewInst)
    {
        this.dewInst = dewInst;
    }

    public Float getDewInst()
    {
        return this.dewInst;
    }

    public void setDewMin(Float dewMin)
    {
        this.dewMin = dewMin;
    }

    public Float getDewMin()
    {
        return this.dewMin;
    }

    public void setDewMax(Float dewMax)
    {
        this.dewMax = dewMax;
    }

    public Float getDewMax()
    {
        return this.dewMax;
    }

    public void setPressureInst(Float pressureInst)
    {
        this.pressureInst = pressureInst;
    }

    public Float getPressureInst()
    {
        return this.pressureInst;
    }

    public void setPressureMin(Float pressureMin)
    {
        this.pressureMin = pressureMin;
    }

    public Float getPressureMin()
    {
        return this.pressureMin;
    }

    public void setPressureMax(Float pressureMax)
    {
        this.pressureMax = pressureMax;
    }

    public Float getPressureMax()
    {
        return this.pressureMax;
    }
}
