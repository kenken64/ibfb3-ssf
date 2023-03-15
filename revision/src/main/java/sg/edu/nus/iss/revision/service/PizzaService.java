package sg.edu.nus.iss.revision.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import sg.edu.nus.iss.revision.model.Delivery;
import sg.edu.nus.iss.revision.model.Order;
import sg.edu.nus.iss.revision.model.Pizza;
import sg.edu.nus.iss.revision.repository.PizzaRepository;

@Service
public class PizzaService {
    @Autowired
    private PizzaRepository pizzaRepo;

    public static final String[] PIZZA_NAMES = {
        "bella", "margherita", "marinara", "spianatacalabrese", "trioformaggio"
    };

    public static final String[] PIZZA_SIZES = { "sm", "md", "lg"};

    private final Set<String> pizzaNames;
    private final Set<String> pizzaSizes;
    

    @Value("${revision.pizza.api.url}")
    private String restPizzaUrl;

    public PizzaService(){
        pizzaNames = new HashSet<String>(Arrays.asList(PIZZA_NAMES));
        pizzaSizes = new HashSet<String>(Arrays.asList(PIZZA_SIZES));
    }

    public Optional<Order> getOrderByOrderId(String orderId){
        return pizzaRepo.get(orderId);
    }

    public Order savePizzaOrder(Pizza p , Delivery d){
        Order o = createPizzaOrder(p,d);
        calculateCost(o);
        pizzaRepo.save(o);
        return o;
    }

    public Order createPizzaOrder(Pizza p , Delivery d){
        String orderId = UUID.randomUUID().toString().substring(0,8);
        Order o = new Order(p, d);
        o.setOrderId(orderId);
        return o;
    }

    public float calculateCost(Order o){
        float total = 0f;
        switch(o.getPizzaName()){
            case "margherita":
                total+=22;
                break;

            case "trioformaggio":
                total+=25;
                break;

            case "bella", "" , "marinara", "spianatacalabrese":
                total+=30;
                break;
        }

        switch(o.getSize()) {
            case "md":
                total*=1.2;
                break;
            case "lg":
                total*=1.5;
                 break;
            case "sm":
            default:    
        }

        total *=o.getQuantity();
        if(o.getRush())
            total +=2;
        o.setTotalCost(total);
        return total;
    }

    public List<ObjectError> validatePizzaOrder(Pizza p){
        List<ObjectError> errors = new LinkedList<>();
        FieldError error;

        if(!pizzaNames.contains(p.getPizza().toLowerCase())){
            error = new FieldError("pizza", "pizza", 
                    "We do not have the %s pizza ".formatted(p.getPizza()));
            errors.add(error);
        }

        if(!pizzaSizes.contains(p.getSize().toLowerCase())){
            error = new FieldError("pizza", "size", 
                    "We do not have the %s pizza size".formatted(p.getSize()));
            errors.add(error);
        }


        return errors;
    }

    public Optional<Order> getOrderDetails(String orderId){
        String url = UriComponentsBuilder
                    .fromUriString(this.restPizzaUrl + orderId)
                    .toUriString();
        RequestEntity req = RequestEntity.get(url).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        Order o = Order.create(resp.getBody());
        if(null == o)
            return Optional.empty();
        
        return Optional.of(o);
    }
}
