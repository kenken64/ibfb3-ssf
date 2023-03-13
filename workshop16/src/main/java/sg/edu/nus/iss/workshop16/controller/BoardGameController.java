package sg.edu.nus.iss.workshop16.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.workshop16.model.Mastermind;
import sg.edu.nus.iss.workshop16.service.BoardGameService;

@RestController
@RequestMapping(path="/api/boardgame", 
        consumes=MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardGameController {
    @Autowired
    private BoardGameService bgSvc;

    @PostMapping
    public ResponseEntity<String> createBoardGame(
            @RequestBody Mastermind ms){
        int insertCnt = bgSvc.saveGame(ms);
        Mastermind result= new Mastermind();
        result.setId(ms.getId());
        result.setInsertCount(insertCnt);
        // This is optional
        if(insertCnt == 0){
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJSONInsert().toString());
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJSONInsert().toString());
    }
}
