package sg.edu.nus.iss.revision.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.revision.model.Order;
import sg.edu.nus.iss.revision.service.PizzaService;

@RestController
@RequestMapping("/order")
public class PizzaRestController {
    @Autowired
    private PizzaService pizzasvc;
    
    @GetMapping(path="{orderId}")
    public ResponseEntity<String> getOrder(@PathVariable String orderId){
        Optional<Order> op = pizzasvc.getOrderByOrderId(orderId);
        if(op.isEmpty()){
            JsonObject error = Json.createObjectBuilder()
                .add("message", "Order %s not found".formatted(orderId))
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error.toString());
        }
        return ResponseEntity.ok(op.get().toJSON().toString());
    }
}
