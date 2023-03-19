package sg.edu.nus.iss.revision2.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.revision2.model.Item;
import sg.edu.nus.iss.revision2.model.Quotation;
import sg.edu.nus.iss.revision2.service.QuotationService;

@RestController
@RequestMapping(path="/quotation", 
    produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class QuotationRestController {
    
    @Autowired
    private QuotationService qSvc;

    @PostMapping
    public ResponseEntity<String> createQuotationItem(
            @RequestBody Item i){
        qSvc.saveItem(i);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(i.toJSON().build().toString());
    }

    @GetMapping
    public ResponseEntity<String> getAllQuotation() throws IOException{
        Quotation q = qSvc.getQuotations();
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(q.toJSON().build().toString());
    }
}
