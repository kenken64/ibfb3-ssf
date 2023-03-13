package sg.edu.nus.iss.workshop16.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(path="{msId}")
    public ResponseEntity<String> getBoardGame(
        @PathVariable String msId) throws IOException{
        Mastermind ms = bgSvc.findById(msId);
        if(ms == null || ms.getName() == null){
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ms.toJSON().toString());
    }

    @PutMapping(path="{msId}")
    public ResponseEntity<String> updateBoardGame(
                @RequestBody Mastermind ms,
                @PathVariable String msId,
                @RequestParam(defaultValue = "false") boolean isUpSert

    )throws IOException{
        Mastermind result = null;
        ms.setUpSert(isUpSert);
        if(!isUpSert){
            result = bgSvc.findById(msId);
            if(result == null){
                return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("");
            }
        }
        ms.setId(msId);
        int updateCount = bgSvc.updateBoardGame(ms);
        ms.setUpdateCount(updateCount);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ms.toJSONUpdate().toString());
    }
}
