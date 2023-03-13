package sg.edu.nus.iss.workshop16.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Pegs implements Serializable{
    private int total_count;
    private List<Type> types;

    public int getTotal_count() {
        return total_count;
    }
    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
    public List<Type> getTypes() {
        return types;
    }
    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public JsonObjectBuilder toJSON(){
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<JsonObjectBuilder> listOfTypes = this.getTypes()
            .stream()
            .map(t -> t.toJSON())
            .toList();

        for(JsonObjectBuilder x: listOfTypes)
            arrBuilder.add(x);

        return Json.createObjectBuilder()
            .add("total_count", this.getTotal_count())
            .add("types", arrBuilder);
    }

    public static Pegs createJson(JsonObject o){
        Pegs pp = new Pegs();
        List<Type> result = new LinkedList<Type>();
        JsonNumber totalCnt = o.getJsonNumber("total_count");
        JsonArray types = o.getJsonArray("types");
        pp.setTotal_count(totalCnt.intValue());
        for(int i=0; i < types.size(); i++){
            JsonObject x = types.getJsonObject(i);
            Type t = Type.createJson(x);
            result.add(t);
        }
        pp.setTypes(result);
        return pp;
    }
    
}
