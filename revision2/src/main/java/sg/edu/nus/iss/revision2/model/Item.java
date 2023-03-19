package sg.edu.nus.iss.revision2.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Instant;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;

public class Item extends Base{
    private String name;
    private BigDecimal price;
    private String id;
    private String username;
    private Long dt;

    public Item(){
        this.id = super.generateId(8);
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal inPrice) {
        this.price = inPrice;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDt() {
        return Instant.now().toEpochMilli();
    }

    public void setDt(Long dt) {
        this.dt = Instant.now().toEpochMilli();
    }
    
    public JsonObjectBuilder toJSON(){
        return Json.createObjectBuilder()
            .add("name", this.getName())
            .add("price", this.price)
            .add("username", this.username)
            .add("id", this.getId())
            .add("dt", this.getDt().toString());
    }

    public static Item createJson(JsonObject o){
        Item t = new Item();
        JsonNumber itemPrice  = o.getJsonNumber("price");
        String name = o.getString("name");
        String id = o.getString("id");
        String username = o.getString("username");
        Long dt = o.getJsonNumber("dt").longValue();
        
        t.setId(id);
        t.setName(name);
        t.setUsername(username);
        t.setPrice(itemPrice.bigDecimalValue());
        t.setDt(dt);
        return t;
    }    

    public static Item create(String json) throws IOException{
        Item m = new Item();
        if(json != null){
            try(InputStream is = new ByteArrayInputStream(json.getBytes())){
                JsonReader r = Json.createReader(is);
                JsonObject o = r.readObject();
                m.setName(o.getString("name"));
                m.setId(o.getString("id"));
                m.setUsername(o.getString("username"));
                m.setPrice(o.getJsonNumber("price").bigDecimalValue());
                //m.setDt(o.getJsonNumber("dt").longValue());
            }
        }
        return m;
    }
}
