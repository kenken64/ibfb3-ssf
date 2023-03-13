package sg.edu.nus.iss.workshop16.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class DecodingBoard implements Serializable{
    private int total_count;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
    
    public JsonObjectBuilder toJSON(){
        return Json.createObjectBuilder()
            .add("total_count", this.getTotal_count());
    }

    public static DecodingBoard createJson(JsonObject o){
        DecodingBoard d = new DecodingBoard();
        JsonNumber totalCnt = o.getJsonNumber("total_count");
        d.setTotal_count(totalCnt.intValue());
        return d;
    }
}
