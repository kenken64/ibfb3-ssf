package sg.edu.nus.iss.workshop17.model.openweather;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Weather implements Serializable{
    private String city;
    private String temperature;

    private List<Conditions> conditions = new LinkedList<>();
    private Long weatherTimeStamp;
    private Long sunsetTimeStamp;
    private Long sunriseTimeStamp;

    
    public Long getWeatherTimeStamp() {
        return weatherTimeStamp;
    }

    public void setWeatherTimeStamp(Long weatherTimeStamp) {
        this.weatherTimeStamp = weatherTimeStamp;
    }

    public Long getSunsetTimeStamp() {
        return sunsetTimeStamp;
    }

    public void setSunsetTimeStamp(Long sunsetTimeStamp) {
        this.sunsetTimeStamp = sunsetTimeStamp;
    }

    public Long getSunriseTimeStamp() {
        return sunriseTimeStamp;
    }

    public void setSunriseTimeStamp(Long sunriseTimeStamp) {
        this.sunriseTimeStamp = sunriseTimeStamp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public List<Conditions> getConditions() {
        return conditions;
    }

    public void setConditions(List<Conditions> conditions) {
        this.conditions = conditions;
    }

    public static Weather create(String json) throws IOException{
        Weather w = new Weather();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            w.setCity(o.getString("name"));
            JsonNumber wDt= o.getJsonNumber("dt");
            w.setWeatherTimeStamp(wDt.longValue());
            JsonObject mainObj = o.getJsonObject("main");
            w.setTemperature(mainObj.getJsonNumber("temp").toString());
            JsonObject sysObj = o.getJsonObject("sys");
            w.setSunriseTimeStamp(sysObj.getJsonNumber("sunrise").longValue());
            w.setSunsetTimeStamp(sysObj.getJsonNumber("sunset").longValue());
            w.conditions = o.getJsonArray("weather").stream()
                .map(v-> (JsonObject)v)
                .map(v-> Conditions.createJson(v))
                .toList();
        }

        return w;
    }

}
