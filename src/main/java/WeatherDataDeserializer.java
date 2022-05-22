import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Custom WeatherData JsonDeserializer to Gson lib
 */
public class WeatherDataDeserializer implements JsonDeserializer<ArrayList<WeatherData>>
{
    @Override
    public ArrayList<WeatherData> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        ArrayList<WeatherData> dataset = new ArrayList<WeatherData>(0);

        for (JsonElement _json: json.getAsJsonArray())
        {
            JsonObject object = _json.getAsJsonObject();

            WeatherData data = new WeatherData();
            data.setId(object.get("CD_ESTACAO").getAsString());

            data.setPrecipitation(getFloatData(object, "CHUVA"));
            data.setRadiation(getFloatData(object, "RAD_GLO"));
            data.setTemperatureInst(getFloatData(object, "TEM_INS"));
            data.setTemperatureMin(getFloatData(object, "TEM_MIN"));
            data.setTemperatureMax(getFloatData(object, "TEM_MAX"));
            data.setMoistureInst(getFloatData(object, "UMD_INS"));
            data.setMoistureMin(getFloatData(object, "UMD_MIN"));
            data.setMoistureMax(getFloatData(object, "UMD_MAX"));
            data.setPressureInst(getFloatData(object, "PRE_INS"));
            data.setPressureMin(getFloatData(object, "PRE_MIN"));
            data.setPressureMax(getFloatData(object, "PRE_MAX"));
            data.setDewInst(getFloatData(object, "PTO_INS"));
            data.setDewMin(getFloatData(object, "PTO_MIN"));
            data.setDewMax(getFloatData(object, "PTO_MAX"));

            try
            {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HHmm");
                String dateNhour = object.get("DT_MEDICAO").getAsString() + " " + object.get("HR_MEDICAO").getAsString();
                data.setDateTime(formatter.parse(dateNhour));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            dataset.add(data);
        }

        return dataset;
    }

    private Float getFloatData(JsonObject object, String id)
    {
        return object.get(id).isJsonNull() ? WeatherData.NAN : object.get(id).getAsFloat();
    }
}
