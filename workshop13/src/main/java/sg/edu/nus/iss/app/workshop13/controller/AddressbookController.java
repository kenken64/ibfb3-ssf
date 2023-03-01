package sg.edu.nus.iss.app.workshop13.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import sg.edu.nus.iss.app.workshop13.model.Contact;
import sg.edu.nus.iss.app.workshop13.util.Contacts;

@Controller
@RequestMapping(path="/contact")
public class AddressbookController {

    @Autowired
    private Contacts ctcs;

    @Autowired
    private ApplicationArguments appArgs;

    @Value("${workshop13.default.data.dir}")
    private String defaultDataDir;
    
    @GetMapping
    public String showAddressBookForm(Model model){
        model.addAttribute("contact", new Contact());
        return "addressbook";
    }

    @PostMapping
    public String saveContact(@Valid Contact contact, BindingResult binding , Model model){
        if(binding.hasErrors()){
            return "addressbook";
        }

        ctcs.saveContact(contact, model, appArgs, defaultDataDir);
        return "showContact";
    }

    @GetMapping(path="{contactId}")
    public String getContactId(Model model, @PathVariable String contactId){
        ctcs.getContactById(model, contactId, appArgs, defaultDataDir);
        return "showContact";
    }

    @GetMapping(path="/list")
    public String getAllContacts(Model model){
        ctcs.getAllContacts(model, appArgs, defaultDataDir);
        return "contacts";
    }
}
