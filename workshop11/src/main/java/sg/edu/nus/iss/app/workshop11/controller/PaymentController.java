package sg.edu.nus.iss.app.workshop11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/payment")
public class PaymentController {
    @GetMapping(path={"/index.html", "/kenneth"}, produces = {"text/html"})
    public String paymentPage(){
        return "index3";
    }   
}
