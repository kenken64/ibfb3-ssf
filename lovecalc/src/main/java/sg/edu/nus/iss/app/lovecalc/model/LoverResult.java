package sg.edu.nus.iss.app.lovecalc.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Random;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class LoverResult implements Comparable <LoverResult> {
    private String fname;
    private String sname;
    private Integer percentage;
    private String result;
    private String id;

    public LoverResult() {
        this.id = generateId(8);
    }

    public LoverResult(String fname, String sname) {
        this.id = generateId(8);
        this.fname =fname;
        this.sname = sname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private synchronized String generateId(int numChars) {
        Random r = new Random();
        StringBuilder strBuilder = new StringBuilder();
        while (strBuilder.length() < numChars) {
            strBuilder.append(Integer.toHexString(r.nextInt()));
        }
        return strBuilder.toString().substring(0, numChars);
    }

    public static LoverResult create(String json) throws IOException {
        LoverResult w = new LoverResult();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            // remove encoding chars from API
            String person1Name = URLDecoder.decode(o.getString("fname"), "UTF-8");
            String person2Name = URLDecoder.decode(o.getString("sname"), "UTF-8");
            
            w.setFname(person1Name);
            w.setSname(person2Name);
            w.setPercentage(Integer.parseInt(o.getString("percentage")));
            w.setResult(o.getString("result"));
        }
        return w;
    }

    @Override
    public int compareTo(LoverResult o) {
        if(o.getPercentage() > 70){
            return 1;
        }
        return -1;
    }

}
