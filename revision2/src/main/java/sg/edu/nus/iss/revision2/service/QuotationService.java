package sg.edu.nus.iss.revision2.service;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.revision2.model.Item;
import sg.edu.nus.iss.revision2.model.Quotation;
import sg.edu.nus.iss.revision2.repository.QuotationRepository;

@Service
public class QuotationService {
    
    @Autowired
    private QuotationRepository qRepo;

    public void saveItem(final Item i){
        qRepo.saveGame(i);
    }

    public Quotation getQuotations() throws IOException {
        Quotation q = new Quotation();
        List<Item> l = new LinkedList<>();
        Item[] items = qRepo.getQuotations();
        for(Item x : items)
            l.add(x);
        q.setItems(l);
        return q;
    }
}
