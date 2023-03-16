package sg.edu.nus.iss.revision.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.revision.model.Order;

@Repository
public class PizzaRepository {
    @Autowired @Qualifier("pizza")
    private RedisTemplate<String, String> template;

    public void save(Order ord){
        
        this.template.opsForValue()
            .set(ord.getOrderId(), ord.toJSON().toString());
    }

    public Optional<Order> get(String orderId){
        String json = template.opsForValue().get(orderId);
        if((null == json || json.trim().length() <= 0)){
            return Optional.empty();
        }

        return Optional.of(Order.create(json));
    }
}
