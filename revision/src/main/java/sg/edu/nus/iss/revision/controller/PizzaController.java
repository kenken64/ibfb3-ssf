package sg.edu.nus.iss.revision.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.revision.model.Pizza;

@Controller
public class PizzaController {
    
    @GetMapping(path={"/", "/index.html"})
    public String getIndex(Model m , HttpSession sess){
        sess.invalidate();
        m.addAttribute("pizza", new Pizza());
        return "index";
    }
}
