package sg.edu.nus.iss.workshop16.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Pieces implements Serializable{
    private DecodingBoard decoding_board;
    private Pegs pegs;
    private Rulebook rulebook;

    public DecodingBoard getDecoding_board() {
        return decoding_board;
    }
    public void setDecoding_board(DecodingBoard decoding_board) {
        this.decoding_board = decoding_board;
    }
    public Pegs getPegs() {
        return pegs;
    }
    public void setPegs(Pegs pegs) {
        this.pegs = pegs;
    }
    public Rulebook getRulebook() {
        return rulebook;
    }
    public void setRulebook(Rulebook rulebook) {
        this.rulebook = rulebook;
    }

    public JsonObjectBuilder toJSON(){
        return Json.createObjectBuilder()
            .add("decoding_board", this.getDecoding_board()
                .toJSON())
            .add("pegs", this.getPegs().toJSON())
            .add("rulebook", this.getRulebook().toJSON());    
    }

    public static Pieces createJson(JsonObject o){
        Pieces p = new Pieces();
        JsonObject decodingBoard = o.getJsonObject("decoding_board");
        JsonObject pegs = o.getJsonObject("pegs");
        JsonObject rb = o.getJsonObject("rulebook");
        p.decoding_board = DecodingBoard.createJson(decodingBoard);
        p.pegs = Pegs.createJson(pegs);
        p.rulebook = Rulebook.createJson(rb);
        return p;
    }

}
