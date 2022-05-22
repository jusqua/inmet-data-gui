import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Custom CityData JsonDeserializer to Gson lib
 */
public class CityDataDeserializer implements JsonDeserializer<ArrayList<CityData>>
{
    @Override
    public ArrayList<CityData> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
    {
        ArrayList<CityData> dataset = new ArrayList<CityData>(0);
        
        for (JsonElement _json: json.getAsJsonArray())
        {
            JsonObject object = _json.getAsJsonObject();
            CityData data = new CityData();
            data.setId(object.get("sigla").getAsString());
            data.setName(object.get("nome_estacao").getAsString());
            dataset.add(data);
        }

        return dataset;
    }
}
