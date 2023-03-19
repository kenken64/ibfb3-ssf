package sg.edu.nus.iss.revision2.model;

import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Quotation extends Base{
    private List<Item> items = new LinkedList<>();
    private String id;
    
    public Quotation(){
        this.id = super.generateId(8);
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JsonObjectBuilder toJSON(){
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        List<JsonObjectBuilder> listOfTypes = this.getItems()
            .stream()
            .map(t -> t.toJSON())
            .toList();

        for(JsonObjectBuilder x: listOfTypes)
            arrBuilder.add(x);

        return Json.createObjectBuilder()
            .add("quotation_id", this.getId())
            .add("items", arrBuilder);
    }

    public static Quotation createJson(JsonObject o){
        Quotation pp = new Quotation();
        List<Item> result = new LinkedList<Item>();
        String quotationId = o.getString("id");
        JsonArray qq = o.getJsonArray("quotations");
        pp.setId(quotationId);
        for(int i=0; i < qq.size(); i++){
            JsonObject x = qq.getJsonObject(i);
            Item t = Item.createJson(x);
            result.add(t);
        }
        pp.setItems(result);
        return pp;
    }

}
